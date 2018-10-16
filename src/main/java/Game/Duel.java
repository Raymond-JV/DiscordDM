package Game;

import Weapons.AttackResultContext;
import Weapons.UnlockedAttacks;


public class Duel {

    private Player currentPlayer;
    private Player nextPlayer;
    private Player winner = null;
    private Player loser = null;

    private StringBuilder result = new StringBuilder();

    public Duel(Player currentPlayer, Player nextPlayer) {
        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;
        result = new StringBuilder();
        shuffleOrder();
    }

    private void shuffleOrder() {
        if (RandomHelper.range(0, 2) == 0) {
            swap();
        }
    }

    public void swap() {
        Player tmp = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = tmp;
    }

    //Only processes turn of current player
    public boolean processTurn(MessageContext message) {
        if (!message.verifySender(currentPlayer.getPlayerAccount()))
            return false;
        processCommand(message);
        return true;
    }

    private void processCommand(MessageContext message) {
        switch (message.getMessage()) {
            case "list":
                result.append("Available Attacks:");
                UnlockedAttacks availableAttacks = currentPlayer.getPlayerAccount().getUnlocks().getAvailableAttacks();
                for (String attackCode : availableAttacks.getAvailableAttacks()) {
                    result.append(attackCode);
                }
                break;
            case "help":
                result.append("Help Stub!");
                break;
            case "shop":
                result.append("Shop Stub!");
                break;
            default:
                processAttack(message);
                break;
        }
    }

    private void processAttack(MessageContext message) {
        Account user = currentPlayer.getPlayerAccount();
        UnlockedAttacks userAttacks = user.getUnlocks().getAvailableAttacks();
        String command = message.getMessage();

        if (userAttacks.hasAttack(command)) {
            AttackResultContext damage = userAttacks.attack(command);
            System.out.println(damage.getMessage());
            nextPlayer.applyDamage(damage.getDamage());
            result.append(String.format("(INFO)%n%s %d hp%n%s %dhp%n", currentPlayer.getUserName(), currentPlayer.getHealth(), nextPlayer.getUserName(), nextPlayer.getHealth()));
            if (finished()) {
                processHistory();
                result.append(String.format("%s has won the game!%n", winner.getUserName()));
            }
            swap();
        } else {
            result.append(String.format("Unknown command: \"%s\"%n", command));
            result.append("Type 'attack' to list available moves");
        }
    }

    public boolean finished() {
        //The player of the current turn is prioritized for winning, similar to the mechanics of dueling in Runescape.
        // i.e. ties are not allowed
        if (!nextPlayer.isAlive()) {
            winner = currentPlayer;
            loser = nextPlayer;
        } else if (!currentPlayer.isAlive()) {
            winner = nextPlayer;
            loser = currentPlayer;
        }
        return winner != null;
    }

    public String getResult() {
        return this.result.toString();
    }

    public String status() {
        return String.format("Current turn: %s%n", currentPlayer.getUserName());
    }

    private void processHistory() {
        DuelHistory winnerHistory = winner.getPlayerAccount().getRecordedGames();
        DuelHistory loserHistory = loser.getPlayerAccount().getRecordedGames();
        winnerHistory.addDuel(new DuelResult(winner.getUserName(), loser.getUserName(), DuelResult.Outcome.WIN));
        loserHistory.addDuel(new DuelResult(loser.getUserName(), winner.getUserName(), DuelResult.Outcome.LOSE));
    }
}
