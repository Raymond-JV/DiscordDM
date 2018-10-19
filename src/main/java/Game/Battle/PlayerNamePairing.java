package Game.Battle;

public class PlayerNamePairing {

    private final Player player;
    private String currentName;

    public PlayerNamePairing(Player player, String currentName) {
        this.player = player;
        this.currentName = currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCurrentName() {
        return currentName;
    }


}
