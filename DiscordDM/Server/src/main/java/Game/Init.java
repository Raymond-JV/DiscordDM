package Game;

import java.io.*;



public class Init {

    public static void main(String[] args) {

        String itemDropFileName = "drop_table.txt";
        ItemDropValueReader drops = new ItemDropValueReader(itemDropFileName);
    }
}