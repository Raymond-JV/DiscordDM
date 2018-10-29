package Game.Battle;

import Game.MessageContext;

import java.util.ArrayList;
import java.util.List;

public class DuelArena {

    private PlayerHandleUpdater effectiveNames;
    private Duel duel;
    private List<Event> eventSequence = new ArrayList<>();
    boolean lastMoveValid = false;

    public DuelArena(PlayerHandleUpdater effectiveNames) {
        this.effectiveNames = effectiveNames;
    }

    public void startDuel(List<Player> participant) {
        duel = new Duel(participant, effectiveNames);
    }

    private void inputMove(String code) {
        lastMoveValid = duel.inputMove(code);
        this.eventSequence = duel.getSequence();
    }

    public boolean duelFinished() {
        return this.duel.finished();
    }

    public List<Event> processCommand(MessageContext message) {
        eventSequence.clear();
        lastMoveValid = false;

                if (!message.verifySender(this.duel.getCurrentPlayer())) {
                    eventSequence.add(new Event("Not your turn."));
                    return eventSequence;
                }
                inputMove(message.getMessage());
                return eventSequence;
    }

    public Player getCurrentPlayer() {
        return this.duel.getCurrentPlayer();
    }

    public boolean lastMoveValid()
    {
        return this.lastMoveValid;
    }

    public List<DuelStatus> getStatus()
    {
        return duel.getStatus();
    }
}
