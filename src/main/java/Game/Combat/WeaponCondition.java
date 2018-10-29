package Game.Combat;

public class WeaponCondition {

    private Effect type;
    private double chance;
    private int duration;
    private double percentage;
    private int strength;

    public WeaponCondition(Effect type, double chance, int duration, double percentage, int strength) {
        this.type = type;
        this.chance = chance;
        this.duration = duration;
        this.percentage = percentage;
        this.strength = strength;
    }

    public WeaponCondition(WeaponCondition other) {
        this.type = other.type;
        this.chance = other.chance;
        this.duration = other.duration;
        this.percentage = other.percentage;
        this.strength = other.strength;
    }

    public Effect getType() {
        return type;
    }

    public double getChance() {
        return chance;
    }

    public int getDuration() {
        return duration;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getStrength() {
        return strength;
    }

    public String toString() {
        return String.format("Effect: %s, Chance: %2.2f, Duration: %d%n, Percentage: %.0f%%, Strength: %d",
                type, chance, duration, percentage * 100, strength);
    }
}
