package Game.Battle;

import Game.Combat.Attack;
import Game.Combat.Effect;
import Game.Combat.Formula.AttackResult;
import Game.Combat.PlayerCondition;
import Game.Combat.WeaponCondition;
import Game.PlayerStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Duel {

        private TurnKeeper marker;
        private PlayerHandleUpdater currentNames;
        private List<Event> eventSequence = new ArrayList<>();
        private static Logger logger = LogManager.getLogger(Duel.class);


    public Duel(List<Player> participants, PlayerHandleUpdater currentNames) {
        marker = new TurnKeeper(participants);
        this.currentNames = currentNames;
    }

    public boolean inputMove(String move) {
            eventSequence.clear();
            return validateMove(move);
    }

    public boolean finished() {
        return marker.finished();
    }

    public void finish()
    {
        for (int i = 0; i < marker.remaining(); i++)
        {
            marker.cycle().getStatus().refresh();
        }
    }

    public List<Event> getSequence(){
        return eventSequence;
    }


    public Player getCurrentPlayer() {
        return marker.current();
    }

    private boolean validateMove(String code) {
        Attack selectedMove = marker.current().getSupplies().getAttack(code);
        if (selectedMove == null) {
            eventSequence.add(new Event("Error: you do not own a weapon with that code."));
            eventSequence.add( new Event("Type '!list' to show available moves."));
            return false;
        }
        return verifyAttack(selectedMove);
    }

    private boolean verifyAttack(Attack move) {
        PlayerCondition frozen = getCondition(marker.current(), Effect.FREEZE);
        if ((frozen != null) && (move.getType() == CombatStyle.MELEE)) {
            eventSequence.add(new Event("You cannot use melee style attacks while frozen."));
            eventSequence.add(new Event(String.format("The freeze will last for %d more turn(s).", frozen.getDuration())));
            return false;
        }
        return verifySpec(move);
    }

    private boolean verifySpec(Attack move) {
        int requiredSpec = move.getSpec();
        int currentSpec = marker.current().getStatus().getSpec();
        if (requiredSpec > currentSpec) {
            eventSequence.add(new Event(String.format("Insufficient special. %s requires %d spec.", move.getCode()[0], move.getSpec())));
            return false;
        } else {
           marker.current().getStatus().removeSpec(requiredSpec);
            return calculateDamage(move);
        }
    }

    private boolean calculateDamage(Attack move) {
        AttackResult damage = move.calculateAttack(marker.current(), marker.peek());
        boolean conditionLanded = move.conditionLanded();
        return checkForPlayerHeal(move, damage, conditionLanded);
    }

    private boolean checkForPlayerHeal(Attack move, AttackResult result, boolean condition) {
        if (condition && (move.getCondition().getType() == Effect.HEAL)) {
            int heal = (int) ( result.getRawDamage() * move.getCondition().getPercentage());
            int before = marker.current().getStatus().getHealth();
            marker.current().getStatus().applyHeal(heal);
            int after = marker.current().getStatus().getHealth();
            eventSequence.add(new Event("%s healed for %s.", currentNames.getHandle(marker.current()), String.valueOf(after - before)));
        }
        return applyDamage(move, result);
    }

    private boolean applyDamage(Attack move, AttackResult result) {
        int damage = result.getRawDamage();

        //#TODO generate random message code
        eventSequence.add( new Event(move.getMessage()[0], currentNames.getHandle(marker.current()), result.getDamageParticles()));
        marker.peek().getStatus().applyDamage(damage);

        if (verifyDeath(marker.peek())) {
            marker.remove(marker.peek());
            return true;
        } else {
            return checkForVenge(move, result);
        }
    }

    private boolean checkForVenge(Attack move, AttackResult result) {
        PlayerCondition venge = getCondition(marker.peek(), Effect.VENGEANCE);
        if (venge != null) {
            int reflect = (int) ((double) result.getRawDamage() * venge.getPercentage());
            marker.current().getStatus().applyDamage(reflect);
            eventSequence.add( new Event("%s was struck by venge and took %s damage.", currentNames.getHandle(marker.current()), String.valueOf(reflect)));
            venge.tick();
            if (verifyDeath(marker.current())) {
                marker.remove(marker.current());
                return true;
            }
        }
        return checkForGenericConditions(move);
    }

    private boolean checkForGenericConditions(Attack move) {
        String otherName = currentNames.getHandle(marker.peek());

        for (PlayerCondition condition : marker.peek().getStatus().getPlayerPlayerConditions()) {
            switch (condition.getType()) {
                case POISON:
                    int poisonDamage = 6;
                    marker.peek().getStatus().applyDamage(poisonDamage);
                    eventSequence.add(new Event("%s is poisoned and took %s damage.", otherName, String.valueOf(poisonDamage)));
                    break;

                case VENOM:
                    int venomDamage = 12;
                    marker.peek().getStatus().applyDamage(venomDamage);
                    eventSequence.add(new Event("Player took %s damage from venom.", otherName, String.valueOf(venomDamage)));
                    break;
                case HEAL:
                    marker.current().getStatus().getPlayerPlayerConditions().remove(condition);
                    continue;
            }
            condition.tick();
        }
        if (verifyDeath(marker.peek())) {
            marker.remove(marker.peek());
            return true;
        } else {
            return applyCondition(move);
        }
    }

    private boolean applyCondition(Attack move) {
        WeaponCondition condition = move.getCondition();

        if (move.conditionLanded() && (getCondition(marker.peek(), condition.getType()) == null)) {
            PlayerCondition newCondition = new PlayerCondition(condition.getType(), condition.getDuration(),
                    condition.getPercentage(), condition.getStrength());
            String nextName = currentNames.getHandle(marker.peek());
            switch (condition.getType()) {
                case VENOM:
                    eventSequence.add(new Event("%s was venomed!%n", nextName));
                    break;
                case POISON:
                    eventSequence.add(new Event("%s was poisoned!%n", nextName));
                    break;
                case FREEZE:
                    eventSequence.add(new Event("%s was frozen for %s turn(s)!%n", nextName, String.valueOf(condition.getDuration())));
            }
            marker.peek().getStatus().getPlayerPlayerConditions().add(newCondition);
        }
        removedExpiredConditions();
        marker.cycle();
        return true;
    }

    private void removedExpiredConditions() {
        for (int i = 0; i < marker.remaining(); i++)
        {
            Iterator<PlayerCondition> bookmark = marker.cycle().getStatus().getPlayerPlayerConditions().iterator();
            String nextName = currentNames.getHandle(marker.current());
            while (bookmark.hasNext())
            {
                PlayerCondition effect = bookmark.next();
                if (effect.expired()) {

                    switch (effect.getType()) {
                        case VENOM:
                            eventSequence.add(new Event("%s is no longer venomed.%n", nextName));
                            break;
                        case POISON:
                            eventSequence.add(new Event("%s is no longer poisoned.%n", nextName));
                            break;
                        case FREEZE:
                            eventSequence.add(new Event("%s is now unfrozen.%n", nextName));
                    }
                    bookmark.remove();
                }
            }
        }
    }

    private boolean verifyDeath(Player player) {
        if (player.getStatus().getHealth() == 0) {
            eventSequence.add(new Event("%s died!\n", currentNames.getHandle(player)));
            marker.current().getStatus().refresh();
            marker.peek().getStatus().refresh();
            return true;
        } else {
            return false;
        }
    }
    public List<DuelStatus> getStatus() {
        List<DuelStatus> status = new ArrayList<>();
        Player current = marker.current();

        for (int i = 0; i < marker.remaining(); i++) {
            String effectiveName = currentNames.getHandle(current);
            PlayerStatus currentStatus = current.getStatus();
            status.add(new DuelStatus(effectiveName, currentStatus.getHealth(), currentStatus.getSpec()));
            current = marker.cycle();
        }
        return status;
    }

    private PlayerCondition getCondition(Player selected, Effect type) {
        for (PlayerCondition condition : selected.getStatus().getPlayerPlayerConditions()) {
            if (condition.getType() == type)
                return condition;
        }
        return null;
    }
}
