package Game.Battle;

import Game.Combat.Attack;
import Game.Combat.Effect;
import Game.Combat.PlayerCondition;
import Game.Combat.WeaponComponent;
import Game.DuelOutcome;
import Game.MessageContext;
import Game.PlayerStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class Duel {

    //#TODO use circly linked list with list to allow for mpre than 2 players per battle
    //Possibly add boss battles later

    private List<PlayerNamePairing> participants;
    private List<DuelOutcome> results = new ArrayList<>();
    private PlayerNamePairing currentPlayer;
    private String previousDialogue = null;


    public Duel(List<PlayerNamePairing> players) {
        this.participants = players;
        for (PlayerNamePairing player : players)
            player.getPlayer().getStatus().refresh();
        Collections.shuffle(participants);
        currentPlayer = participants.get(0);
        currentPlayer.getPlayer().getStatus().setTurn(true);
    }

    private void switchTurns() {
        for (PlayerNamePairing p : participants) {
            if (!p.getPlayer().getStatus().isTurn())
                currentPlayer = p;
            p.getPlayer().getStatus().cycleTurn();
        }
    }

    //#TODO use circular linked list and call getnext
    private PlayerNamePairing getDefender() {
        for (PlayerNamePairing p : participants)
            if (!p.getPlayer().getStatus().isTurn())
                return p;
        return null;
    }

    private boolean playerDeath()
    {
        for (PlayerNamePairing p : participants)
        {
            if (p.getPlayer().getStatus().isDead())
                return true;
        }
        return false;
    }

    //Stub
    private void processOutcomes()
    {

    }



    public List<DuelOutcome> getResults() {
        return results;
    }


    private void processCommand(MessageContext message) {
        switch (message.getMessage()) {
            case "list":
                System.out.println("Available Attacks:");
                List<WeaponComponent> ownedWeapons = currentPlayer.getPlayer().getSupplies().getWeapons();
                for (WeaponComponent weapon : ownedWeapons) {
                    System.out.println(weapon.getName());
                    for (String code : weapon.getAttackList().keySet()) {
                        System.out.println(code + " ");
                    }
                }
                break;
            case "help":
                System.out.println("Help Stub");
                break;
            case "shop":
                System.out.println("Shop stub");
                break;
            default:
                processAttack(message);
                break;
        }
    }

    //Dirty code, need to test weapons ASAP
    private void processAttack(MessageContext message) {

        if (!message.verifySender(currentPlayer.getPlayer()))
            return;

        String code = message.getMessage();
        Attack selectedMove = currentPlayer.getPlayer().getSupplies().getAttack(code);
        if (selectedMove == null) {
            previousDialogue = String.format("Error: unknown command%nType 'list' to show available moves.%n");
            return;
        }

        previousDialogue = "";

        PlayerNamePairing otherPlayer = this.getDefender();

        assert otherPlayer != null;
        int damage = selectedMove.calculateAttack(currentPlayer.getPlayer(), otherPlayer.getPlayer());
        boolean conditionLanded = selectedMove.conditionLanded();
        if (selectedMove.getCondition().getType() == Effect.HEAL)
        {
            if (conditionLanded) {
                int healAmount = (int) Math.round((double) damage * selectedMove.getCondition().getPercentage());
                previousDialogue += String.format("%s healed for %d", currentPlayer.getCurrentName(), healAmount);
            }
        }

        otherPlayer.getPlayer().getStatus().applyDamage(damage);
        previousDialogue += String.format(selectedMove.getMessage()[0], currentPlayer.getCurrentName(), String.valueOf(damage));
        if (playerDeath()) {
            PlayerNamePairing winner = this.getWinner();
                    previousDialogue += String.format("%s has won!", winner.getCurrentName());
                    return;
        }
        Stack<PlayerCondition> expired = new Stack<>();

        for (PlayerCondition condition : otherPlayer.getPlayer().getStatus().getPlayerPlayerConditions())
        {
            if (condition.getType() == Effect.VENGEANCE)
            {
                int reflect = (int)((double)damage * condition.getPercentage());
                currentPlayer.getPlayer().getStatus().applyDamage(reflect);
                previousDialogue += String.format("%s was struck by venge and took %d damage.", currentPlayer.getCurrentName(), reflect);
                expired.add(condition);
                if (playerDeath()) {
                    PlayerNamePairing winner = this.getWinner();
                    previousDialogue += String.format("%s has won!", winner.getCurrentName());
                    return;
                }
            }
        }

        for (PlayerCondition condition : otherPlayer.getPlayer().getStatus().getPlayerPlayerConditions())
        {
            if (condition.getType() == Effect.POISON)
            {
                previousDialogue += String.format("%s was damaged by poison and took %d damage.", currentPlayer.getCurrentName(), condition.getStrength());
                condition.tick();
                if (condition.expired())
                    expired.add(condition);
            }

        }

        List<PlayerCondition> conditions = otherPlayer.getPlayer().getStatus().getPlayerPlayerConditions();

        for (PlayerCondition toRemove : expired)
            conditions.remove(toRemove);

        switchTurns();
    }

    private PlayerNamePairing getWinner() {
        for (PlayerNamePairing p : participants)
        {
            if (!p.getPlayer().getStatus().isDead())
                return p;
        }
        throw new RuntimeException("Winner was expected and not found!");
    }

    public boolean finished() {
        return results.size() != 0;
    }

    public String getStatus() {
        String duelStatus = "";
        for (PlayerNamePairing player : participants) {
            PlayerStatus currentStatus = player.getPlayer().getStatus();
            duelStatus += String.format("Player: %s, Health: %d, Spec: %d%n", player.getCurrentName(),
                    currentStatus.getHealth(), currentStatus.getSpec());
        }
        return duelStatus;
    }

    public void updatePlayerName(Player toUpdate, String newName) {
        for (PlayerNamePairing pairing : participants) {
            if (pairing.getPlayer() == toUpdate)
                pairing.setCurrentName(newName);
        }
    }


//    private void processHistory() {
//        DuelHistory winnerHistory = winner.getPlayerAccount().getRecordedGames();
//        DuelHistory loserHistory = loser.getPlayerAccount().getRecordedGames();
//        winnerHistory.addDuel(new DuelResult(winner.getUserName(), loser.getUserName(), DuelResult.Outcome.WIN));
//        loserHistory.addDuel(new DuelResult(loser.getUserName(), winner.getUserName(), DuelResult.Outcome.LOSE));
//    }
}
