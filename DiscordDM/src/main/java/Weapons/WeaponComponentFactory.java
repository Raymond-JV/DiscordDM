package Weapons;

import java.lang.reflect.InvocationTargetException;

public enum WeaponComponentFactory {

    DRAGON_CLAWS(DragonClaws.class),
    ABBYSAL_WHIP(AbbysalWhip.class);


    private Class<? extends WeaponComponent> clazz;
    private static String blacklist[] = {"\\s", "_"};


    WeaponComponentFactory(Class<? extends WeaponComponent> clazz) {
        this.clazz = clazz;
    }

    public WeaponComponent create() {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Created failed to spawn object in weapon component factory.");
    }

    public static WeaponComponent create(String weaponName) {
        for (WeaponComponentFactory weapon : WeaponComponentFactory.values()) {
            if (classCompare(weapon.name(), weaponName)) {
                return weapon.create();
            }
        }
        throw new RuntimeException("Tried to spawn a non existent object in weapon component factory.");
    }

    private static boolean classCompare(String class1, String class2) {
        for (String value : blacklist) {
            class1 = class1.replaceAll(value, "");
            class2 = class2.replaceAll(value, "");
        }
        System.out.println(class1);
        return class1.equalsIgnoreCase(class2);
    }

    public static boolean isWeapon(String itemName) {
        for (WeaponComponentFactory weapon : WeaponComponentFactory.values()) {
            if (classCompare(weapon.name(), itemName)) {
                return true;
            }
        }
        return false;
    }
}
