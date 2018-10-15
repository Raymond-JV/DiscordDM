package Game;

import Weapons.WeaponComponentFactory;

import java.util.Scanner;

public class ConsoleDuelSimulator {
    public static void main(String[] args) {

        Account user[] = {new Account("Trollermond"), new Account("Bob")};
        for (Account a : user) {
            for (WeaponComponentFactory w : WeaponComponentFactory.values()) {
                a.getUnlocks().getAvailableAttacks().addWeaponComponent(w.create());
            }
        }

        Scanner in = new Scanner(System.in);
        while (true) {

            System.out.printf("%n=== Console Duel Simulator ===%n\td: begin duel%n\tx: quit console%n");
            String input = in.nextLine();
            switch (input.toLowerCase()) {

                case "d":
                    Player dummies[] = {new Player(user[0]), new Player(user[1])};
                    Duel consoleDuel = new Duel(dummies[0], dummies[1]);


                    while (!consoleDuel.finished()) {
                        System.out.println(consoleDuel.status());

                        while (consoleDuel.processTurn(new MessageContext(dummies[0].getPlayerAccount(), in.nextLine()))) {
                            System.out.println(consoleDuel.getResult());
                            break;
                        }
                }
                    for (Account a : user)
                        System.out.println(a.getRecordedGames() + a.getUserName());
                    break;

                case "x":
                    System.out.println("Terminating...");
                    System.exit(0);
                default:
                    break;
            }
        }
    }

}
