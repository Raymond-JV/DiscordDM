package Game.Combat;


import Game.Item;

import java.util.HashMap;
import java.util.Map;

public class WeaponComponent extends Item {

    private final Map<String, Attack> variations;

    public WeaponComponent() {
        this.variations = new HashMap<>();
    }

    public WeaponComponent(String name, int value, Map<String, Attack> variations) {

        super(name, value);
        this.variations = variations;
    }

    public void addAttack(String quickCode, Attack addAttack) {
        this.variations.put(quickCode, addAttack);
    }

    public Map<String, Attack> getAttackList() {
        return this.variations;
    }

    public Attack attack(String quickCode) {
        return this.getAttackList().get(quickCode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        for (Attack move : variations.values()) {
            sb.append(move.toString());
        }
        return sb.toString();
    }
}