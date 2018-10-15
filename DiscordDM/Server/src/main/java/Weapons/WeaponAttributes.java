package Weapons;

public class WeaponAttributes {

    private String quickCode;
    private double accuracy;
    private int maxHit;
    private int spec;

    public WeaponAttributes() {
    }

    public String getQuickCode() {
        return quickCode;
    }

    public WeaponAttributes setQuickCode(String quickCode) {
        this.quickCode = quickCode;
        return this;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public WeaponAttributes setAccuracy(double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public WeaponAttributes setMaxHit(int maxHit) {
        this.maxHit = maxHit;
        return this;
    }

    public int getSpec() {
        return spec;
    }

    public WeaponAttributes setSpec(int spec) {
        this.spec = spec;
        return this;
    }

    public String toString() {
        return String.format("Code:[%s], Accuracy:[%f], Max Hit:[%d]%n", quickCode, accuracy, maxHit);
    }
}