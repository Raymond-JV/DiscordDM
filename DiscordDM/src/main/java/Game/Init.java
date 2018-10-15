package Game;

import java.io.*;



public class Init {

    public static void main(String[] args) {

        String itemDropFileName = "drop_table.txt";
        if (Init.class.getClassLoader().getResourceAsStream(itemDropFileName) == null)
            System.out.println("NULL");
    }
}