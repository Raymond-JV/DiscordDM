package Game.Battle;

import Game.DuelResult;

import java.util.ArrayList;
import java.util.List;

public class DuelHistory {

    List<DuelResult> history = new ArrayList<>();
    private int won;
    private int lost;

    public DuelHistory() {
    }

    public void addDuel(DuelResult game) {
        if ((won == Integer.MAX_VALUE) || (lost == Integer.MAX_VALUE)) {
            return;
        }
        history.add(game);

        if (game.score == DuelResult.Outcome.WIN)
            won++;
        else
            lost++;
    }

    public int getTotalPlayed() {
        return history.size();
    }

    public int getTotalWon() {
        return won;
    }

    public int getTotalLost() {
        return lost;
    }

    public String toString() {
        String winRate;
        if (lost == 0)
            winRate = "undefeated!";
        else
            winRate = String.format("%.0f%%", 100 * (float) won / (won + lost));

        return String.format("(Total Games: %d) (Won: %d) (Lost: %d) (Win Rate: %s)", getTotalPlayed(), won, lost, winRate);
    }
}
