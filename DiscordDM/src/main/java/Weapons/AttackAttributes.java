package Weapons;

public class AttackAttributes {

    private String quickCode;
    private double accuracy;
    private int maxHit;
    private int spec;

    public AttackAttributes() {
    }

    public String getQuickCode() {
        return quickCode;
    }

    public AttackAttributes setQuickCode(String quickCode) {
        this.quickCode = quickCode;
        return this;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public AttackAttributes setAccuracy(double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public AttackAttributes setMaxHit(int maxHit) {
        this.maxHit = maxHit;
        return this;
    }

    public int getSpec() {
        return spec;
    }

    public AttackAttributes setSpec(int spec) {
        this.spec = spec;
        return this;
    }

    public String toString() {
        return String.format("Code:[%s], Accuracy:[%f], Max Hit:[%d]%n", quickCode, accuracy, maxHit);
    }
}