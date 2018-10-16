package Game;


import java.util.HashSet;
import java.util.Set;

public class Init {

    private static String itemDump = "drop_table.txt";
    public static void main(String[] args) throws InterruptedException {

       ItemDropValueReader itemParser = new ItemDropValueReader(itemDump);
       DropTable duelRewards = new DropTable(itemParser.getCommons(), 0.9);
       DropTable rareDrop = new DropTable(itemParser.getWeapons(), 1);
       duelRewards.addDropTable(rareDrop);



         Set<String> spawnedItems = new HashSet<>();
         int i = 0;
       while (true)
       {
           Item spawnedItem = duelRewards.spawn();
           System.out.println(spawnedItem.info() + " num: " + i++);
           spawnedItems.add(spawnedItem.getName());
           Thread.sleep(1);
           if (spawnedItems.size() == (itemParser.getCommons().size() + itemParser.getWeapons().size()))
               break;
       }
        System.out.println("all items spawned correctly");

    }
}