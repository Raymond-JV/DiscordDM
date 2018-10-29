package Game.Combat.Formula;

import Game.Battle.Player;
import Utility.RandomHelper;

public final class DragonClawFormula implements AttackFormula {

    @Override
    public AttackResult calculateAttack(Player user, Player other, int maxHit, double accuracy) {

        int particle[] = new int[4];

        if (RandomHelper.chance(accuracy)) {
            particle[0] = RandomHelper.range(maxHit);
            particle[1] = particle[0] / 2;
            particle[2] = particle[1] / 2;
            particle[3] = particle[1] - particle[2];
        } else if (RandomHelper.chance(accuracy)) {
            particle[1] = (int) (RandomHelper.range(maxHit) * 0.75);
            particle[2] = particle[1] / 2;
            particle[3] = particle[1] - particle[2];
        } else if (RandomHelper.chance(accuracy)) {
            particle[2] = (int) (RandomHelper.range(maxHit) * 0.5);
            particle[3] = particle[2];
        } else if (RandomHelper.chance(accuracy)) {
            particle[3] = (int) (RandomHelper.range(maxHit) * (1.5));
        }

        int sum = 0;

        StringBuilder sumParticles = new StringBuilder();
        for (int i = 0; i < particle.length - 1; i++) {
            sum += particle[i];
            sumParticles.append(String.valueOf(particle[i])).append("-");
        }
        int lastParticle = particle[particle.length - 1];
        sum += lastParticle;
        sumParticles.append(String.valueOf(lastParticle));

        return new AttackResult(sum, sumParticles.toString());
    }
}

