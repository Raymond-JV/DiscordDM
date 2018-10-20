package Game.Battle;

import Game.Combat.Attack;
import Game.Combat.Effect;
import Game.Combat.Formula.AttackResult;
import Game.Combat.PlayerCondition;
import Game.Combat.WeaponCondition;
import Utility.CircularLinkedList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Duel {

    private CircularLinkedList<Player> participants = new CircularLinkedList<>();
    private Player currentPlayer;
    private Player nextPlayer;
    private Iterator<Player> turnMarker;

    private PlayerHandleUpdater currentNames;


    private StringBuilder event = new StringBuilder();

    public Duel(List<Player> participants, PlayerHandleUpdater currentNames)
    {
        this.currentNames = currentNames;
        Collections.shuffle(participants);
        for (Player p : participants)
            this.participants.addFront(p);
        this.turnMarker = this.participants.iterator();
        nextPlayer = turnMarker.next();
        currentPlayer = turnMarker.next();
    }

    public String inputMove(String move)
    {
        event = new StringBuilder();
        return validateMove(move);
    }


    public boolean finished()
    {
        return participants.size() == 1;
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }


    private String validateMove(String code)
    {
        Attack selectedMove = currentPlayer.getSupplies().getAttack(code);
        if (selectedMove == null) {
            event.append("Error: unknown command.\n");
            event.append("Type 'list' to show available moves.").append("\n");
            return event.toString();
        }
        return verifyAttack(selectedMove);
    }
    private String verifyAttack(Attack move)
    {
        PlayerCondition frozen = getCondition(currentPlayer, Effect.FREEZE);
        if ((frozen != null) && (move.getType() == CombatStyle.MELEE))
        {
            event.append("You cannot use melee style attacks while frozen.");
            event.append(String.format("The freeze will last for %d more turns.", frozen.getDuration()));
            return event.toString();
        }
        return verifySpec(move);
    }

    private String verifySpec(Attack move) {
        int requiredSpec = move.getSpec();
        int currentSpec = currentPlayer.getStatus().getSpec();
        if (requiredSpec > currentSpec)
        {
            event.append(String.format("Insufficient special. %s requires %d spec.", move.getCode()[0], move.getSpec()));
            return event.toString();
        }
        else{
            currentPlayer.getStatus().removeSpec(requiredSpec);
            return calculateDamage(move);
        }
    }

    private String calculateDamage(Attack move)
    {
        AttackResult damage = move.calculateAttack(currentPlayer, nextPlayer);
        boolean conditionLanded = move.conditionLanded();
        return checkForPlayerHeal(move, damage, conditionLanded);
    }

    private String checkForPlayerHeal(Attack move, AttackResult result, boolean condition)
    {
        if (condition && (move.getCondition().getType() == Effect.HEAL))
        {
            int heal = (int) ((double)result.getRawDamage() * move.getCondition().getPercentage());
            currentPlayer.getStatus().applyHeal(heal);
            event.append(String.format("%s healed for %d", currentNames.getHandle(currentPlayer), heal));
        }
        return applyDamage(move, result, condition);
    }

    private String applyDamage(Attack move, AttackResult result, boolean condition) {
        int damage = result.getRawDamage();
        //#TODO generate random message code
        event.append(String.format(move.getMessage()[0], currentNames.getHandle(currentPlayer) , result.getDamageParticles())).append("\n");
        nextPlayer.getStatus().applyDamage(damage);

        if (verifyDeath(nextPlayer)) {
            turnMarker.remove();
            return event.toString();
        }
        else {
            return checkForVenge(move, result);
        }
    }

    private String checkForVenge(Attack move, AttackResult result) {
        PlayerCondition venge = getCondition(nextPlayer, Effect.VENGEANCE);
        if (venge != null)
        {
            int reflect = (int)((double)result.getRawDamage() * venge.getPercentage());
            currentPlayer.getStatus().applyDamage(reflect);
            event.append(String.format("%s was struck by venge and took %d damage.%n", "stub", currentNames.getHandle(currentPlayer), reflect));
            venge.tick();
            if (verifyDeath(currentPlayer))
            {
                turnMarker.remove();
                return event.toString();
            }
        }
        return checkForGenericConditions(move);
    }

    private String checkForGenericConditions(Attack move) {
        String otherName = currentNames.getHandle(nextPlayer);
        for (PlayerCondition condition : nextPlayer.getStatus().getPlayerPlayerConditions()) {
            switch (condition.getType()) {
                case POISON:
                    int poisonDamage = 6;
                    nextPlayer.getStatus().applyDamage(poisonDamage);
                    //#TODO implement class for handling conditions/reading values
                    event.append(String.format("%s is poisoned and took %d damage.%n", otherName, poisonDamage));
                    break;

                case VENOM:
                    int venomDamage = 12;
                    nextPlayer.getStatus().applyDamage(venomDamage);
                    event.append(String.format("Player took %d damage from venom.%n", otherName, venomDamage));
                    break;
            }
            condition.tick();
        }
        if (verifyDeath(nextPlayer))
        {
            turnMarker.remove();
            return event.toString();
        }
        else {
            return applyCondition(move);
        }
    }

    private String applyCondition(Attack move) {
        WeaponCondition condition = move.getCondition();

        if (move.conditionLanded() && (getCondition(nextPlayer, condition.getType()) == null))
        {
                PlayerCondition newCondition = new PlayerCondition(condition.getType(), condition.getDuration(),
                        condition.getPercentage(), condition.getStrength());
                String nextName = currentNames.getHandle(nextPlayer);
                switch (condition.getType()) {
                    case VENOM:
                        event.append(nextName).append(" was venomed!\n");
                        break;
                    case POISON:
                        event.append(nextName).append(" was poisoned!\n");
                }
                nextPlayer.getStatus().getPlayerPlayerConditions().add(newCondition);
            }
        removedExpiredConditions();
        cycleTurn();
        return event.toString();
    }

    private void removedExpiredConditions() {

        for (int i = 0; i < participants.size(); i++)
        {
            turnMarker.next().getStatus().getPlayerPlayerConditions().removeIf(PlayerCondition::expired);
        }
    }

    //#TODO optimize
    private boolean verifyDeath(Player player)
    {
        if (player.getStatus().getHealth() == 0)
        {
            event.append(currentNames.getHandle(player)).append(" died!\n");
            return true;
        }
        else {
            return false;
        }
    }

    private void cycleTurn()
    {
        nextPlayer = currentPlayer;
        currentPlayer = turnMarker.next();
    }

    public String getStatus()
    {
        return String.format("%s%n%s", getPlayerStatus(currentPlayer), getPlayerStatus(nextPlayer));
    }

    private String getPlayerStatus(Player player)
    {
        //        for (PlayerCondition condition: player.getStatus().getPlayerPlayerConditions())
//        {
//           // sb.append(String.format("Condition: %s Duration: %d", condition.getType().name(), condition.getDuration()));
//        }
        String status = String.format("%s, health: %d, spec: %d", currentNames.
                getHandle(player), player.getStatus().getHealth(), player.getStatus().getSpec());
        return status;
    }

    private PlayerCondition getCondition(Player selected, Effect type)
    {
        for (PlayerCondition condition : selected.getStatus().getPlayerPlayerConditions())
        {
            if (condition.getType() == type)
                return condition;
        }
        return null;
    }
}
