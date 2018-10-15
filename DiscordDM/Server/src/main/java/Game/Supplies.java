package Game;



import Weapons.UnlockedAttacks;

import java.math.BigInteger;

public class Supplies {
    private BigInteger gold = BigInteger.valueOf(0);
    private UnlockedAttacks availableAttacks = new UnlockedAttacks();

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

    public BigInteger getGold()
    {
        return gold;
    }

    public UnlockedAttacks getAvailableAttacks() {
        return availableAttacks;
    }



}
