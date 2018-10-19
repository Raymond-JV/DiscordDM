package Game;

import Game.Battle.InsufficientFundsException;
import Game.Battle.Player;
import Game.Combat.WeaponComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {

    Map<String, WeaponComponent> sale = new HashMap<>();
    private StringBuilder message;

    public Shop(List<WeaponComponent> sale)
    {
        for (WeaponComponent w : sale)
            this.sale.put(w.getName(), w);
    }

    public boolean buy(Player player, String item)
    {
        WeaponComponent wanted = sale.get(item);
        int cost = wanted.getValue();

        if (player.getSupplies().hasGold(cost)){
            processTransaction(player, wanted);
            return true;
        }
        else
            return false;
    }

    //#TODO Database Transaction
    private void processTransaction(Player player, WeaponComponent wanted) {
        try {
            player.getSupplies().removeGold(wanted.getValue());
            List<WeaponComponent> ownedWeapons = player.getSupplies().getWeapons();
            ownedWeapons.add(wanted);
        } catch (InsufficientFundsException e) {
            System.out.printf("Error, possible duplication.");
            e.printStackTrace();
        }
    }

    public String getItemsForSale()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("General Store");
        for (WeaponComponent item : sale.values())
        {
            sb.append(item.getName());
            sb.append(String.format(" : %10d gp", item.getValue()));
        }
        return sb.toString();
    }
}
