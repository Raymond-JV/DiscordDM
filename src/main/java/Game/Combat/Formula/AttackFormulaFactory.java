package Game.Combat.Formula;

import Game.Combat.Formula.AttackFormula;
import Game.Combat.Formula.BasicFormula;
import Game.Combat.Formula.DragonClawFormula;

public class AttackFormulaFactory {

    public AttackFormulaFactory()
    {}

    public AttackFormula create(String code)
    {
        switch (code.toLowerCase())
        {
            case "dclaw":
            case "dclaws":
            case "dragon claws":
                return new DragonClawFormula();
            case "gmaul":
            case "maul":
            case "granite maul":
                return new GraniteMaulFormula();

            default:
                System.out.println(code);
                return new BasicFormula();
        }
    }
}
