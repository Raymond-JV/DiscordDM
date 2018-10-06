package Weapons;

import java.util.Random;

public class AbbysalWhip extends WeaponComponent {

    private String attackDescription;
    private String quickCode = "whip";
    private double accuracy = 0.7;
    private int maxHit = 35;
    private int spec;

    public AbbysalWhip() {
        AttackStats whipSlash = new AttackStats().setAccuracy(accuracy).setMax(maxHit);
        this.addAttack(quickCode, new Attack(whipSlash) {
            @Override
            public AttackResultContext attack() {
                Random seed = new Random();
                AttackResultContext result = new AttackResultContext();

                if (this.hitLanded()) {
                    result.setDamage(seed.nextInt(maxHit));
                } else {
                    result.setDamage(0);
                }
                result.setMessage(String.format("Whip slashed for a %d", result.getDamage()));
                return result;
            }
        });

    }
}
