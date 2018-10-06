package Weapons;

public class AttackStats {

    private int maxHit;
    private double accuracy;

    public AttackStats() {
    }


    public AttackStats setMax(int maxHit) {
        this.maxHit = maxHit;
        return this;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public AttackStats setAccuracy(double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

}
