package Game.Combat.Formula;


import Game.Battle.Player;
import Game.RandomHelper;

public final class BasicFormula implements AttackFormula {

    @Override
    public AttackResult calculateAttack(Player user, Player other, int maxHit, double accuracy) {
        int damage;
        String particles;

        if (RandomHelper.chance(accuracy)) {
            damage = RandomHelper.range(maxHit);
        }
        else
            damage = 0;

        return new AttackResult(damage, String.valueOf(damage));
    }
}
