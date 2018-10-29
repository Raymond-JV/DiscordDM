package Game;

public class DuelResult {

    public final String winner;
    public final String loser;
    public final Outcome score;

    public DuelResult(String winner, String loser, Outcome score) {
        this.winner = winner;
        this.loser = loser;
        this.score = score;
    }

    public String toString() {
        return String.format("(%s), Winner: %s, Loser: %s", score.name(), winner, loser);
    }

    public enum Outcome {WIN, LOSE}
}
