package Game;

import java.util.Random;

public class RandomHelper {

    private static Random seed = new Random();

    private RandomHelper() {
    }

    public static boolean chance(double accuracy)
    {
        return seed.nextDouble() <= accuracy;
    }

    public static int range(int min, int max) {
        if (min > max) {
            throw new RuntimeException("Are you serious lol. Min > Max in RandomHelper.range()");
        } else {
            return seed.nextInt(max - min) + min;
        }
    }

    public static int range(int max)
    {
        return range(0, max);
    }

    public static double range(double min, double max)
    {
        if (min > max) {
            throw new RuntimeException("Are you serious lol. Min > Max in RandomHelper.range()");
        } else {
            return seed.nextDouble() * max + min;
        }
    }

    public static double range(double max)
    {
        return range(0, max);
    }
}
