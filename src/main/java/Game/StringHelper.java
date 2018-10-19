package Game;

public final class StringHelper {

    private StringHelper(){}

    public static String removeSpace(String s)
    {
        return s.replaceAll(" ", "");
    }
}
