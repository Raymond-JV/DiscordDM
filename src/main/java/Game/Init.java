package Game;

import Data.JsonDataParser;
import Data.JsonWeaponData;
import Weapons.WeaponAttributes;
import Weapons.WeaponAttributesBuilder;
import Weapons.WeaponComponentFactory;

import java.util.List;


public class Init {

    private static String itemDump = "drop_table.txt";
    public static void main(String[] args) throws InterruptedException {



//        JsonDataParser parser = new JsonDataParser(itemDump);
//        List<JsonWeaponData> weapons = parser.getWeapons();
//        JsonWeaponData w = weapons.get(0);
//        System.out.println(w);
//        System.out.println(w.getAccuracy());
//         WeaponAttributes attributes = new WeaponAttributesBuilder()
//                                            .accuracy(w.getAccuracy())
//                                            .maxHit(w.getMaxHit())
//                                            .spec(w.getSpec())
//                                            .quickCode(w.getQuickCodes())
//                                            .build();
//        System.out.println(attributes);


//       JsonDataParser itemParser = new JsonDataParser(itemDump);
//       DropTable duelRewards = new DropTable(itemParser.getCommons(), 1);
      // DropTable rareDrop = new DropTable(itemParser.getWeapons(), 1);
     //  duelRewards.addDropTable(rareDrop);



//         Set<String> spawnedItems = new HashSet<>();
//         int i = 0;
//       while (true)
//       {
//           Item spawnedItem = duelRewards.spawn();
//           System.out.println(spawnedItem.info() + " num: " + i++);
//           spawnedItems.add(spawnedItem.getName());
//           Thread.sleep(1);
//           if (spawnedItems.size() == (itemParser.getCommons().size() + itemParser.getWeapons().size()))
//               break;
//       }
//        System.out.println("all items spawned correctly");

    }
}