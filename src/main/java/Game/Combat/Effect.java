package Game.Combat;

public enum Effect {
    FREEZE,
    POISON,
    VENOM,
    VENGEANCE,
    HEAL;

    @Override
    public String toString() {
        return this.name();
    }
}
