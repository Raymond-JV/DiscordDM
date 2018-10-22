package Game.Combat.Formula;

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
                return new BasicFormula();
        }
    }
}
