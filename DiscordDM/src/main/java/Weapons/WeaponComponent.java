package Weapons;


import java.util.Map;
import java.util.TreeMap;

public abstract class WeaponComponent {

    private TreeMap<String, Attack> variations = new TreeMap<>();

    public WeaponComponent() {
    }

    public void addAttack(String quickCode, Attack addAttack) {
        this.variations.put(quickCode, addAttack);
    }

    public Map<String, Attack> getAttackList() {
        return this.variations;
    }

    public AttackResultContext attack(String quickCode) {
        return this.getAttackList().get(quickCode).attack();
    }
}