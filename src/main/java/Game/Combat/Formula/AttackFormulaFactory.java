package Game.Combat.Formula;

import Game.Combat.Attack;

public class AttackFormulaFactory {

    public AttackFormulaFactory() {
    }

    public AttackFormula create(Attack attack) {
        switch (attack.getCode()[0]) {
            case "dclaw":
            case "dclaws":
            case "dragon claws":
                return new DragonClawFormula();
            default:
                return new BasicFormula(Math.max(1, attack.getNumHits()));
        }
    }
}
