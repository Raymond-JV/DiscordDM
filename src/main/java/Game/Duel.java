package Game;

import Weapons.AttackResultContext;
import Weapons.UnlockedAttacks;

public class Duel {

    private Player currentPlayer;
    private Player nextPlayer;
    private Player winner = null;
    private Player loser = null;

    public Duel(Player currentPlayer, Player nextPlayer) {
        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;
        shuffleOrder();
    }

    private void shuffleOrder() {
        if (RandomHelper.range(0, 2) == 0) {
            Player tmp = currentPlayer;
            currentPlayer = nextPlayer;
            nextPlayer = tmp;
        }
    }

    public void swap() {
        Player tmp = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = tmp;
    }

    public void processTurn(MessageContext message) {
        if (!message.verifySender(currentPlayer.getPlayerAccount()))
            return;
        processCommand(message);
    }

    public void processCommand(MessageContext message) {
        switch (message.getMessage()) {
            case "help":
                System.out.println("Help stub!");
                break;
            case "shop":
                System.out.println("Shop stub!");
                break;
            default:
                processAttack(message);
                break;
        }
    }

    private void processAttack(MessageContext message) {
        Account user = currentPlayer.getPlayerAccount();
        UnlockedAttacks userAttacks = user.getAvailableAttacks();
        String command = message.getMessage();

        if (userAttacks.hasAttack(command)) {
            AttackResultContext damage = userAttacks.attack(command);
            System.out.println(damage.getMessage());
            nextPlayer.applyDamage(damage.getDamage());
            System.out.printf("(INFO)%n%s %d hp%n%s %dhp%n", currentPlayer.getUserName(), currentPlayer.getHealth(), nextPlayer.getUserName(), nextPlayer.getHealth());

            if (duelOver()) {
                processHistory();
                System.out.printf("%s has won the game!%n", winner.getUserName());
            }
            swap();
        } else {
            //ADD WEAPON CHECKING
            System.out.printf("Unknown command: [%s]", command);
        }

    }

    public boolean duelOver() {
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

    public void info() {
        System.out.printf("Current user: %s, Next User: %s%n", currentPlayer.getUserName(), nextPlayer.getUserName());
    }


    private void processHistory() {
        DuelHistory winnerHistory = winner.getPlayerAccount().getRecordedGames();
        DuelHistory loserHistory = loser.getPlayerAccount().getRecordedGames();
        winnerHistory.addDuel(new DuelResult(winner.getUserName(), loser.getUserName(), DuelResult.Outcome.WIN));
        loserHistory.addDuel(new DuelResult(loser.getUserName(), winner.getUserName(), DuelResult.Outcome.LOSE));
    }
}
