package Game;


import Game.Combat.Effect;
import Game.Combat.WeaponComponent;
import Game.Combat.WeaponCondition;
import JsonData.JsonDataParser;

import java.util.List;

public class Init {

    private static String itemDump = "ItemStats.json";

    public static void main(String[] args) {


        JsonDataParser parser = new JsonDataParser(itemDump);
        List<WeaponComponent> weaponComponents = parser.readWeapons();
        List<Item> commons = parser.readCommons();


        for (WeaponComponent weapon : weaponComponents)
        {
            for (Item item: commons)
            {
                if (StringHelper.removeSpace(item.getName()).equalsIgnoreCase(StringHelper.removeSpace(weapon.getName())))
                    System.out.printf("Collision Detected: %s%n", weapon.getName());
            }
        }

    }
}

