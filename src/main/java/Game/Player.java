package Game;

import java.util.List;

public class Player {

    private static int MAX_HEALTH = 99;
    private static int MAX_SPEC = 100;
    boolean alive = true;
    private List<Condition> playerConditions;
    private int health = MAX_HEALTH;
    private int spec = MAX_SPEC;
    private Account playerAccount;


    public Player(Account playerAccount) {
        this.playerAccount = playerAccount;
    }

    public void applyDamage(int damage) {
        health -= damage;
        if (health <= 0)
            alive = false;
    }

    public void removeSpec(int specUsed) throws InsufficientSpecialException {
        if (spec <= specUsed)
            throw new InsufficientSpecialException();
        this.spec -= specUsed;
    }

    public void applyHeal(int heal) {
        health = Math.min(MAX_HEALTH, heal);
    }

    public void applyCondition(Condition newCondition) {
        playerConditions.add(newCondition);
    }

    public int getHealth() {
        return health;
    }

    public int getSpec() {
        return spec;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public String getUserName() {
        return this.playerAccount.getUserName();
    }

    public Account getPlayerAccount() {
        return playerAccount;
    }

}
