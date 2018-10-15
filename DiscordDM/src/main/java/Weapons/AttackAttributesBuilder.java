package Weapons;


public class AttackAttributesBuilder {

    private AttackAttributes toBuild = new AttackAttributes();

    public AttackAttributesBuilder() {
    }

    public AttackAttributesBuilder quickCode(String quickCode) {
        this.toBuild.setQuickCode(quickCode);
        return this;
    }

    public AttackAttributesBuilder accuracy(double accuracy) {
        this.toBuild.setAccuracy(accuracy);
        return this;
    }

    public AttackAttributesBuilder maxHit(int maxHit) {
        this.toBuild.setMaxHit(maxHit);
        return this;
    }

    public AttackAttributesBuilder spec(int spec) {
        this.toBuild.setSpec(spec);
        return this;
    }

    public AttackAttributes build() {
        return this.toBuild;
    }
}



