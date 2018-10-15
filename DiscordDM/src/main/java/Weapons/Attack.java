package Weapons;

import java.util.Random;

public abstract class Attack {

    private AttackAttributes attackStats;

    public Attack(AttackAttributes attackStats) {
        this.attackStats = attackStats;
    }

    public abstract AttackResultContext attack();

    public boolean hitLanded() {
        Random seed = new Random();
        return seed.nextDouble() <= attackStats.getAccuracy();
    }
}
