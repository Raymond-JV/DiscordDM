package Game;



import Game.Battle.DuelArena;
import Game.Battle.Player;
import Game.Battle.PlayerHandleUpdater;
import Game.Combat.WeaponComponent;
import Data.JsonDataParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleDuelSimulator {

    private static String itemDump = "ItemStats.json";
    private static String name[] = {"bob", "trollermond"};

    public static void main(String[] args) {

        JsonDataParser parser = new JsonDataParser(itemDump);
        List<WeaponComponent> weapons = parser.readWeapons();
        PlayerSpawner spawner = new PlayerSpawner(weapons);
        PlayerHandleUpdater updatedNames = new PlayerHandleUpdater();

        //The list is shuffled, so order of player one and two is lost
        Player one = spawner.createFullyUnlocked();
        Player two = spawner.createFullyUnlocked();

        List<Player> duelists = new ArrayList<>();
        duelists.add(one);
        duelists.add(two);
        updatedNames.updateHandle(one, name[0]);
        updatedNames.updateHandle(two, name[1]);

        Scanner in = new Scanner(System.in);
        DuelArena arena = new DuelArena(updatedNames);
        while (true)
        {
            System.out.printf("%n=== Console DuelArena Simulator ===%n\td: begin duel%n\tx: quit console%n");
            String input = in.nextLine();
            switch (input.toLowerCase()) {

                case "d":

                    arena.startDuel(duelists);
                    while (!arena.duelFinished())
                    {
                        System.out.printf("Current turn: %s%n", updatedNames.getHandle(arena.getCurrentPlayer()));
                        String command[] = in.nextLine().split(" ");
                        if (command.length != 2)
                        {
                            System.out.println("error: format 'player' 'attack'");
                            continue;
                        }
                        String inputName = command[0];
                        String message = command[1];

                        String output = null;
                        if (inputName.equalsIgnoreCase(name[0]))
                            output = arena.processCommand(new MessageContext(one, message));
                            else if (inputName.equalsIgnoreCase(name[1]))
                            output = arena.processCommand(new MessageContext(two, message));

                            if (output !=null)
                                System.out.println(output);
                    }
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
