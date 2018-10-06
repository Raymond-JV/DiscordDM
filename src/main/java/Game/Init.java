package Game;

import Weapons.AttackResultContext;
import Weapons.DragonClaws;

public class Init {

    public static void main(String[] args) {

        DragonClaws d = new DragonClaws();
        AttackResultContext dclawAttack = d.attack("dclaw");
        System.out.println(dclawAttack.getDamage());
        System.out.println(dclawAttack.getMessage());


    }
}