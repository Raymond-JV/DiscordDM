package Game.Combat.Formula;

import Game.Combat.Formula.AttackFormula;
import Game.Combat.Formula.BasicFormula;
import Game.Combat.Formula.DragonClawFormula;

public class AttackFormulaFactory {

    public AttackFormulaFactory()
    {}

    public AttackFormula create(String code)
    {
        switch (code)
        {

            case "dclaw":
                return new DragonClawFormula();

            default:
                return new BasicFormula();
        }
    }
}
