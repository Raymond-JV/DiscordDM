package Game.Battle;

public class DuelStatus {

    private final String name;
    private final int health;
    private final int spec;

    public DuelStatus(String name, int health, int spec) {
        this.name = name;
        this.health = health;
        this.spec = spec;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getSpec() {
        return spec;
    }
}
