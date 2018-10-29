package Game.Combat;

import Game.Item;

import java.util.HashMap;
import java.util.Map;

public class WeaponComponent extends Item {

    private final Map<String, Attack> variations;

    public WeaponComponent(String name, int value, Map<String, Attack> variations) {

        super(name, value);
        this.variations = variations;
    }

    public WeaponComponent(WeaponComponent other) {
        super(other.getName(), other.getValue());
        this.variations = new HashMap<>();
        variations.putAll(other.getAttackList());
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
        for (Map.Entry<String, Attack> move : variations.entrySet()) {
            sb.append(String.format("Code: %s, %s", move.getKey(), move.getValue()));
        }
        return sb.toString();
    }
}