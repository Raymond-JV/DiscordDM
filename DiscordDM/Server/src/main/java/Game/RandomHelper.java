package Game;

import java.util.Random;

public class RandomHelper {

    private static Random seed = new Random();

    private RandomHelper() {
    }

    public static int range(int min, int max) {
        if (min > max) {
            throw new RuntimeException("Are you serious lol. Min > Max in RandomHelper.range()");
        } else {
            return seed.nextInt(max - min) + min;
        }
    }
}
