package Game.Combat.Formula;

import Game.Battle.Player;

public interface AttackFormula {

   AttackResult calculateAttack(Player user, Player other, int maxHit, double accuracy);

}
