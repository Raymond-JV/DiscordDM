//package Game;
//
//import Data.JsonDataParser;
//import Game.Battle.Duel;
//import Game.Battle.Player;
//import Game.Battle.PlayerHandleUpdater;
//import Game.Combat.WeaponComponent;
//import Utility.CircularLinkedList;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Init {
//
//    private static String itemDump = "ItemStats.json";
//
//    private static void testList() {
//        List<Player> players = new ArrayList<>();
//        CircularLinkedList<Player> test = new CircularLinkedList<>();
//        test.addFront(new Player());
//        test.addFront(new Player());
//        for (Player p : players) {
//            test.addRear(p);
//        }
//        test.display();
//    }
//
//    private static void testArena() {
//        JsonDataParser parser = new JsonDataParser(itemDump);
//        List<WeaponComponent> weapons = parser.readWeapons();
//        PlayerSpawner spawner = new PlayerSpawner(weapons);
//        PlayerHandleUpdater updatedNames = new PlayerHandleUpdater();
//
//        List<Player> duelists = new ArrayList<>();
//        duelists.add(0, spawner.createFullyUnlocked());
//        duelists.add(1, spawner.createFullyUnlocked());
//
//        updatedNames.updateHandle(duelists.get(0), "bob");
//        updatedNames.updateHandle(duelists.get(1), "trollermond");
//
//        Duel arena = new Duel(duelists, updatedNames);
//        Scanner in = new Scanner(System.in);
//        while (true) {
//            String input = in.nextLine();
//            String result = arena.inputMove(input);
//            System.out.println(result);
//            System.out.println(arena.getStatus());
//        }
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//        testArena();
//    }
//}
//
