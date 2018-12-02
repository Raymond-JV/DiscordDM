package Game.Combat.Formula;

import Game.Battle.Player;
import Utility.RandomHelper;

public final class BasicFormula implements AttackFormula {

    private final int numHits;
    public BasicFormula(int numHits)
    {
        this.numHits = numHits;
    }
    public BasicFormula()
    {
        this(1);
    }

    public AttackResult calculateAttack(Player user, Player other, int minHit, int maxHit, double accuracy) {

        String result = "";
        int damage[] = new int[numHits];
        int sum = 0;
        for (int i = 0; i < numHits; i++) {
            damage[i] = (RandomHelper.chance(accuracy)) ? RandomHelper.range(minHit, maxHit) : 0;
            sum += damage[i];
        }

        for (int i = 0; i < damage.length - 1; i++)
            result += String.valueOf(damage[i]) + "-";
        result += damage[damage.length - 1];

        return new AttackResult(sum, result);
    }
    @Override
    public AttackResult calculateAttack(Player user, Player other, int maxHit, double accuracy) {

       return this.calculateAttack(user, other, 0, maxHit, accuracy);
    }
}
