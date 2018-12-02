package Game.Combat.Formula;

import Game.Battle.Player;

public class DhFormula implements AttackFormula {

    @Override
    public AttackResult calculateAttack(Player user, Player other, int maxHit, double accuracy) {
        double multiplier = (user.getStatus().getHealth() > 10) ? 0.45 : 1;
        BasicFormula basic = new BasicFormula();
        return basic.calculateAttack(user, other, (int)(maxHit * multiplier), accuracy);
    }
}
