package Game;

import java.util.List;

public class DropTable {


    private List<? extends Item> itemList;
    private final double odds;
    private DropTable next;


    public DropTable(List<? extends Item> itemList, double odds)
    {
        this.itemList = itemList;
        this.odds = odds;
    }

    public DropTable(List< ? extends Item> itemList, double odds, DropTable next)
    {
        this(itemList, odds);
        this.next = next;
    }


    public void addDropTable(DropTable next)
    {
        this.next = next;
    }

    public Item spawn()
    {
        if ((itemList == null) || (itemList.size() == 0))
            return spawnHelper(next);

        if (RandomHelper.range(0.00, 1.00) <= odds)
        {
            return new Item(itemList.get(RandomHelper.range(0, itemList.size())));
        }
        else {
            return spawnHelper(next);
        }
    }

    private Item spawnHelper(DropTable table)
    {
        if (table == null)
            return null;
        else
            return table.spawn();
    }



}
