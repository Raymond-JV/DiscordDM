package Game;

import java.util.ArrayList;
import java.util.List;

public class DuelHistory {

    List<DuelResult> history = new ArrayList<>();
    int won;
    int lost;

    public DuelHistory() {
    }

    public void addDuel(DuelResult game) {
        if ((won == Integer.MAX_VALUE) || (lost == Integer.MAX_VALUE)) {
            throw new RuntimeException("Game history tally overflowed!");
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
        float winRatio = (float) won / lost;
        return String.format("(Total Games: %d) (Won: %d) (Lost: %d) (Win Rate: %.2f)", getTotalPlayed(), won, lost, winRatio);
    }
}
