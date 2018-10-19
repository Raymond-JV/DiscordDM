package Game.Combat.Formula;

public class AttackResult
{
    private final int rawDamage;
    private final String damageParticles;

    public AttackResult(int rawDamage, String damageParticles) {
        this.rawDamage = rawDamage;
        this.damageParticles = damageParticles;
    }

    public int getRawDamage() {
        return rawDamage;
    }

    public String getDamageParticles() {
        return damageParticles;
    }
}

