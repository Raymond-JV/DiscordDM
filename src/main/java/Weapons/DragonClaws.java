package Weapons;

import java.util.Random;

public class DragonClaws extends WeaponComponent {


    private String quickCode = "dclaw";
    private double accuracy = 0.6;
    private int maxHit = 35;


    private int particle[] = new int[4];

    public DragonClaws() {
        AttackStats clawData = new AttackStats().setAccuracy(accuracy).setMax(maxHit);
        this.addAttack(quickCode,
                new Attack(clawData) {
                    @Override
                    public AttackResultContext attack() {

                        Random seed = new Random();
                        AttackResultContext result = new AttackResultContext();
                        resetAttack();

                        if (hitLanded()) {
                            particle[0] = seed.nextInt(maxHit);
                            particle[1] = particle[0] / 2;
                            particle[2] = particle[1] / 2;
                            particle[3] = particle[1] - particle[2];
                        } else if (hitLanded()) {
                            particle[1] = (int) (seed.nextInt(maxHit) * 0.75);
                            particle[2] = particle[1] / 2;
                            particle[3] = particle[1] - particle[2];
                        } else if (hitLanded()) {
                            particle[2] = (int) (seed.nextInt(maxHit) * 0.5);
                            particle[3] = particle[2];
                        } else if (hitLanded()) {
                            particle[3] = (int) (seed.nextInt(maxHit) * (1.5));
                        }

                        int sum = 0;
                        for (int i : particle)
                            sum += i;

                        String message = String.format("Dragon claws slashed for %d-%d-%d-%d!", particle[0], particle[1], particle[2], particle[3]);

                        return result.setDamage(sum).setMessage(message);
                    }
                });
    }

    private void resetAttack() {
        for (int i = 0; i < particle.length; i++)
            particle[i] = 0;
    }
}