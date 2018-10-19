package Game.Combat;
public class PlayerCondition {

    private Effect type;
    private int duration;
    private double percentage;
    private int strength;

    public PlayerCondition(Effect type, int duration, double percentage, int strength) {
        this.type = type;
        this.duration = duration;
        this.percentage = percentage;
        this.strength = strength;
    }

    public void tick() {
            this.duration--;
    }

    public boolean expired()
    {
        return this.duration == 0;
    }

    public Effect getType() {
        return type;
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
}
