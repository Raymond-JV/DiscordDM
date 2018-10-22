package Game.Battle;


import Game.Combat.WeaponComponent;
import Game.MessageContext;
import java.util.List;



public class DuelArena {


    private PlayerHandleUpdater effectiveNames;
    private Duel duel;


    public DuelArena(PlayerHandleUpdater effectiveNames) {
        this.effectiveNames = effectiveNames;
    }

    public void startDuel(List<Player> participant)
    {
        duel = new Duel(participant, effectiveNames);
    }

    private String inputMove(String code)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.duel.inputMove(code));
        sb.append(this.duel.getStatus());
        return sb.toString();
    }

    public boolean duelFinished()
    {
        return this.duel.finished();
    }

    public String processCommand(MessageContext message) {
        if (!message.verifySender(this.duel.getCurrentPlayer())) {
            return "not your turn";
        }

        StringBuilder prompt = new StringBuilder();
        switch (message.getMessage()) {
            case "list":
                prompt.append("Available Attacks:");
                List<WeaponComponent> ownedWeapons = this.duel.getCurrentPlayer().getSupplies().getWeapons();
                for (WeaponComponent weapon : ownedWeapons) {
                    prompt.append("\n").append(weapon.getName()).append(" codes ");
                    for (String code : weapon.getAttackList().keySet()) {
                        prompt.append(code).append(" ");
                    }
                    prompt.append("\n");
                }
                return prompt.toString();
            case "help":
                prompt.append("Help Stub");
                return prompt.toString();
            case "shop":
                prompt.append("Shop stub");
                return prompt.toString();
            default:
                prompt.append(inputMove(message.getMessage()));
                return prompt.toString();
        }
    }

    public Player getCurrentPlayer()
    {
        return this.duel.getCurrentPlayer();
    }
}
