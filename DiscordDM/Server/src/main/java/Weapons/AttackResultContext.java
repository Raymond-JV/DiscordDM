package Weapons;

public class AttackResultContext {

    private int damage;
    private String message;

    public AttackResultContext() {
    }

    public AttackResultContext(int damage, String message) {
        this.damage = damage;
        this.message = message;
    }

    public int getDamage() {
        return this.damage;
    }

    //Builder Methods
    public AttackResultContext setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public AttackResultContext setMessage(String message) {
        this.message = message;
        return this;
    }


}
