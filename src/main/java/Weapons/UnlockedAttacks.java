package Weapons;

import java.util.HashMap;
import java.util.Map;

public class UnlockedAttacks {

    private Map<String, Attack> availableAttacks = new HashMap<>();

    public UnlockedAttacks() {
    }

    public boolean hasAttack(String quickCode) {
        return availableAttacks.containsKey(quickCode);
    }

    public AttackResultContext attack(String quickCode) {
        return availableAttacks.get(quickCode).attack();
    }

    public void addWeaponComponent(WeaponComponent unlockedWeapon) {
        availableAttacks.putAll(unlockedWeapon.getAttackList());
    }

    public void removeWeaponComponent(String quickCode) {
        availableAttacks.remove(quickCode);
    }
}
