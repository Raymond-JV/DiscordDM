package Game;



import Game.Battle.InsufficientFundsException;
import Game.Combat.Attack;
import Game.Combat.WeaponComponent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Supplies {
    private BigInteger gold = BigInteger.valueOf(0);
    private List<WeaponComponent> weapons = new ArrayList<>();
    //private UnlockedAttacks availableAttacks = new UnlockedAttacks();

    public Supplies()
    {}

    public void addGold(int value)
    {
        gold = gold.add(BigInteger.valueOf(value));
    }

    public void removeGold(int value) throws InsufficientFundsException {
        BigInteger difference = gold.subtract(BigInteger.valueOf(value));
        if (difference.compareTo(BigInteger.valueOf(0)) == -1)
            throw new InsufficientFundsException("Error, attempted to remove more gold than available in Supplies.");
        else {
            gold = difference;
        }
    }

    public void setWeapons(List<WeaponComponent> weapons)
    {
        this.weapons = weapons;
    }

    public void addWeapon(WeaponComponent weapon)
    {
        this.weapons.add(weapon);
    }

    public void removeWeapon(WeaponComponent weapon)
    {
        this.weapons.remove(weapon);
    }


    public boolean hasGold(int value)
    {
        return gold.compareTo(BigInteger.valueOf(value)) >= 0;
    }
    public BigInteger getGold()
    {
        return gold;
    }


    public Attack getAttack(String code)
    {
        for (WeaponComponent weapon : weapons)
        {
            Map<String, Attack> attackList = weapon.getAttackList();
            if (attackList.containsKey(code))
                return attackList.get(code);
        }
        return null;
    }

    public List<WeaponComponent> getWeapons() {
        return weapons;
    }



}
