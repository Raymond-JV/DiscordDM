package Weapons;


import Game.Item;

import java.util.HashMap;
import java.util.Map;

public abstract class WeaponComponent extends Item {

    private Map<String, Attack> variations = new HashMap<>();
    private WeaponAttributes attributes;
    public WeaponComponent(){}

    public WeaponComponent(String name, int value) {
        super(name, value);
    }

    public void setAttributes(WeaponAttributes attributes)
    {
        this.attributes = attributes;
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