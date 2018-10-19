package Game.Combat;

import Game.Battle.CombatStyle;
import Game.Battle.Player;
import Game.Combat.Formula.AttackFormula;
import Game.RandomHelper;

import java.util.Arrays;

public class Attack {

    private CombatStyle type;
    private WeaponCondition condition;
    private int maxHit;
    private int spec;
    private double accuracy;
    private String[] message;
    private String[] code;
    private AttackFormula damageStrategy;

    public Attack() {}

    //parsed automatically using gson
    public Attack(CombatStyle type, WeaponCondition condition, int maxHit, int spec, double accuracy, String[] message, String[] code) {
        this.type = type;
        this.condition = condition;
        this.maxHit = maxHit;
        this.spec = spec;
        this.accuracy = accuracy;
        this.message = message;
        this.code = code;
    }

    public Attack(Attack other)
    {
        this.type = other.type;
        this.condition = new WeaponCondition(other.condition);
        this.maxHit = other.maxHit;
        this.spec = other.spec;
        this.accuracy = other.accuracy;
        this.message = Arrays.copyOf(other.message, other.message.length);
        this.code = Arrays.copyOf(other.code, other.code.length);
    }


    public int calculateAttack(Player user, Player other)
    {
        return damageStrategy.calculateAttack(user, other, maxHit, accuracy);
    }

    public void setFormula(AttackFormula damageStrategy)
    {
        this.damageStrategy = damageStrategy;
    }
    public CombatStyle getType() {
        return type;
    }

    public WeaponCondition getCondition() {
        return condition;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public int getSpec() {
        return spec;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public boolean conditionLanded()
    {
        if (condition == null)
            return false;
       return RandomHelper.chance(condition.getChance());
    }

    public String[] getMessage() {
        return message;
    }

    public String[] getCode() {
        return code;
    }
    public String toString()
    {
        return String.format("Max Hit: %d, Spec: %d, Accuracy: %.0f%% , Style: %s%n, First Dialogue: '%s%n'",
                maxHit, spec, accuracy * 100, type.name(), this.message[0]);
    }


}
