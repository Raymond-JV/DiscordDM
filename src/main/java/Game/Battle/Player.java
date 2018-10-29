package Game.Battle;

import Game.PlayerStatus;
import Game.Supplies;

public class Player {

    private Supplies supplies = new Supplies();
    private PlayerStatus status = new PlayerStatus();

    public Player() {
    }

    public Supplies getSupplies() {
        return supplies;
    }

    public void setSupplies(Supplies equipment) {
        this.supplies = equipment;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
}
