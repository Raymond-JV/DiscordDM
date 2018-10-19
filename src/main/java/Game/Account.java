package Game;


import Game.Battle.Player;

public class Account {


    private String snowFlakeId = "coffee"; //#TODO TEMPORARY PLACEHOLDER
    private Player player;


    public Account(String snowFlakeId) {
        this.snowFlakeId = snowFlakeId;
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
