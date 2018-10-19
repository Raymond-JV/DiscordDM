package Game;

import Game.Battle.Player;

public class DuelOutcome {

    private final Player player;
    private final DuelResult outcome;

    public DuelOutcome(Player player, DuelResult outcome) {
        this.player = player;
        this.outcome = outcome;
    }

    public Player getPlayer() {
        return player;
    }

    public DuelResult getOutcome() {
        return outcome;
    }
}
