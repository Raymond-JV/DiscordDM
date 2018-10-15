package Weapons;


public class WeaponAttributesBuilder {

    private WeaponAttributes toBuild = new WeaponAttributes();

    public WeaponAttributesBuilder() {
    }

    public WeaponAttributesBuilder quickCode(String quickCode) {
        this.toBuild.setQuickCode(quickCode);
        return this;
    }

    public WeaponAttributesBuilder accuracy(double accuracy) {
        this.toBuild.setAccuracy(accuracy);
        return this;
    }

    public WeaponAttributesBuilder maxHit(int maxHit) {
        this.toBuild.setMaxHit(maxHit);
        return this;
    }

    public WeaponAttributesBuilder spec(int spec) {
        this.toBuild.setSpec(spec);
        return this;
    }

    public WeaponAttributes build() {
        return this.toBuild;
    }
}



