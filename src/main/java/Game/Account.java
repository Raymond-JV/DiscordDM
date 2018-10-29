package Game;

import Game.Battle.Player;

public class Account {

    private String snowFlakeId;
    private Player player;

    public Account(String snowFlakeId, Player player) {

        this.snowFlakeId = snowFlakeId;
        this.player = player;
    }

    public String getSnowFlakeId() {
        return this.snowFlakeId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
