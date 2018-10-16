package Weapons;


import Game.Item;

import java.util.Map;
import java.util.TreeMap;

public abstract class WeaponComponent extends Item {

    private TreeMap<String, Attack> variations = new TreeMap<>();

    public WeaponComponent(){}
    public WeaponComponent(String name, int value) {
        super(name, value);
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