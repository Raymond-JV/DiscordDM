package Weapons;


import Game.RandomHelper;

public class WeaponSpawner {

    private WeaponComponentFactory weaponList[];
    private int count;

    public WeaponSpawner(WeaponComponentFactory[] weaponList) {
        this.weaponList = weaponList;
        this.count = weaponList.length;
    }

    public WeaponComponent spawnRandomWeapon() {
        return weaponList[RandomHelper.range(0, count)].create();
    }
}
