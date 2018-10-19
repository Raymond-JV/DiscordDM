package Game;

import Game.Battle.InsufficientSpecialException;
import Game.Combat.PlayerCondition;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatus {

    private enum StatusLimits{
        MAX_HEALTH (99),
        MIN_HEALTH(0),
        MAX_SPEC(100),
        MIN_SPEC(0);

        private final int limit;

        StatusLimits(int limit)
        {
            this.limit = limit;
        }
        private int value()
        {
            return limit;
        }
    }
    private List<PlayerCondition> playerPlayerConditions = new ArrayList<>();
    private boolean turn;
    private int health;
    private int spec;

    public PlayerStatus()
    {
        this.refresh();
    }

    public void refresh()
    {
        this.turn = false;
        this.health = StatusLimits.MAX_HEALTH.value();
        this.spec = StatusLimits.MAX_SPEC.value();
    }

    public void cycleTurn()
    {
        this.turn = !this.turn;
    }

    public void setTurn(boolean newTurn)
    {
        this.turn = newTurn;
    }

    public void applyHeal(int heal) {
        health = Math.min(StatusLimits.MAX_HEALTH.value(), heal);
    }

    public void applyDamage(int damage) {
        health = Math.max(health - damage, StatusLimits.MIN_HEALTH.value());

    }

    public void removeSpec(int specUsed) throws InsufficientSpecialException {
        if ((spec - specUsed) < StatusLimits.MIN_SPEC.value())
            throw new InsufficientSpecialException();
        this.spec -= specUsed;
    }

    public void addCondition(PlayerCondition newPlayerCondition) {
        playerPlayerConditions.add(newPlayerCondition);
    }


    public int getHealth() {
        return health;
    }


    public List<PlayerCondition> getPlayerPlayerConditions()
    {
        return this.playerPlayerConditions;
    }

    public int getSpec() {
        return spec;
    }

    public boolean isDead() {

        return this.health <= StatusLimits.MIN_HEALTH.value();
    }

    public boolean isTurn(){
        return this.turn;
    }

}
