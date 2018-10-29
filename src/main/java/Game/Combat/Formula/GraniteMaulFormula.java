package Game.Combat.Formula;

import Game.Battle.Player;
import Utility.RandomHelper;

public class GraniteMaulFormula implements AttackFormula {

    @Override
    public AttackResult calculateAttack(Player user, Player other, int maxHit, double accuracy) {
        int particle[] = new int[3];
        for (int i = 0; i < particle.length - 1; i++) {
            if (RandomHelper.chance(accuracy))
                particle[i] = RandomHelper.range(maxHit);
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
