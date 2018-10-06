package Game;

import Weapons.WeaponComponentFactory;

import java.util.Scanner;

public class ConsoleDuelSimulator {
    public static void main(String[] args) {

        Account user[] = {new Account("Trollermond"), new Account("Bob")};
        WeaponComponentFactory spawner = new WeaponComponentFactory();

        for (Account a : user) {
            a.getAvailableAttacks().addWeaponComponent(spawner.create("DragonClaws"));
            a.getAvailableAttacks().addWeaponComponent(spawner.create("AbbysalWhip"));
        }


        while (true) {
            Player dummies[] = {new Player(user[0]), new Player(user[1])};
            Duel consoleDuel = new Duel(dummies[0], dummies[1]);

            Scanner in = new Scanner(System.in);
            while (!consoleDuel.duelOver()) {
                for (int i = 0; i < 2; i++) {
                    System.out.printf("Current Turn: %s%n", dummies[i].getUserName());
                    String input = in.nextLine();
                    consoleDuel.processCommand(new MessageContext(user[i], input));
                    consoleDuel.info();
                    if (consoleDuel.duelOver())
                        break;
                }
            }
            System.out.println(user[0].getRecordedGames() + user[0].getUserName());
            System.out.println(user[1].getRecordedGames() + user[1].getUserName());
        }


    }
}
