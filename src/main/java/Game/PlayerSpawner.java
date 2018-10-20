package Game;

import Game.Battle.Player;
import Game.Combat.WeaponComponent;

import java.util.ArrayList;
import java.util.List;

public class PlayerSpawner {


    private final List<WeaponComponent> spawnableWeapons;

    private final List<WeaponComponent> freeWeapons = new ArrayList<>();


    public PlayerSpawner(List<WeaponComponent> spawnableWeapons)
    {
        this.spawnableWeapons = spawnableWeapons;
        for (WeaponComponent weapon : spawnableWeapons)
        {
            if (weapon.getValue() == 0)
            {
                freeWeapons.add(weapon);
            }
        }
    }

    private List<WeaponComponent> copyWeapons(List<WeaponComponent> desiredSet)
    {
        List<WeaponComponent> spawnedSet = new ArrayList<>();

        for (WeaponComponent w : desiredSet)
        {
            spawnedSet.add(new WeaponComponent(w));
        }
        return spawnedSet;
    }

    public Player createFullyUnlocked()
    {
        Player spawnedPlayer = new Player();
        spawnedPlayer.getSupplies().setWeapons(this.copyWeapons(spawnableWeapons));
        return spawnedPlayer;
    }

    public Player createDefaultCharacter()
    {
        Player spawnedPlayer = new Player();
        spawnedPlayer.getSupplies().setWeapons(this.copyWeapons(freeWeapons));
        return spawnedPlayer;
    }








}
