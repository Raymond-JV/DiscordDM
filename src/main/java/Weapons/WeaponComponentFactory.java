package Weapons;

import Data.JsonDataParser;
import Data.JsonWeaponData;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum WeaponComponentFactory {

    DRAGON_CLAWS(DragonClaws.class),
    ABBYSAL_WHIP(AbbysalWhip.class);


    private Class<? extends WeaponComponent> clazz;
    private static String blacklist[] = {"\\s", "_"};
    private static Map<String, JsonWeaponData> weaponData = new HashMap<>();

    static {
        JsonDataParser parser = new JsonDataParser("drop_table.txt");
        List<JsonWeaponData> weapons = parser.getWeapons();
        for (JsonWeaponData itemData : weapons)
            weaponData.put(WeaponComponentFactory.clean(itemData.getName()), itemData);
    }
    WeaponComponentFactory(Class<? extends WeaponComponent> clazz) {
        this.clazz = clazz;
    }

    public WeaponComponent create() {
        try {
            JsonWeaponData data = weaponData.get(WeaponComponentFactory.clean(this.name()));
            WeaponAttributesBuilder builder = new WeaponAttributesBuilder();

            WeaponAttributes attributes =   builder.accuracy(data.getAccuracy())
                                                    .maxHit(data.getMaxHit())
                                                    .spec(data.getSpec())
                                                    .quickCode(data.getQuickCodes()).build();
            WeaponComponent weapon = clazz.getConstructor(String.class, int.class).newInstance(data.getName(), data.getPrice());
            weapon.setAttributes(attributes);
            return weapon;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to spawn object in weapon component factory.");
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

            class1 = WeaponComponentFactory.clean(class1);
            class2 = WeaponComponentFactory.clean(class2);
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

    public static String clean(String dirty)
    {
        for (String value: blacklist)
            dirty = dirty.replaceAll(value, "");
        return dirty.toLowerCase();
    }
}
