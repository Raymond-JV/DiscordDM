package Game;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Init {

    public static void main(String[] args) {

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(Init.class.getResourceAsStream("drop_table.txt")))) {

            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}