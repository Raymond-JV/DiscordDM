package Game;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ItemDropValueReader {

    private String fileName;
    private JsonObject data;
    private Map<String, Integer> commons = new HashMap<>();
    private Map<String, Integer> weapons = new HashMap<>();

    public ItemDropValueReader(String fileName)
    {
        this.fileName = fileName;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (InputStream resourceAsStream = ItemDropValueReader.class.getResourceAsStream(fileName);
             JsonReader reader = new JsonReader(new InputStreamReader(resourceAsStream))) {
            JsonParser parser= new JsonParser();
            JsonElement element = parser.parse(reader);
            JsonObject data = element.getAsJsonObject();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public void read()
    {
        data.get("items");
    }

    public Map<String, Integer> getCommons()
    {
        return commons;
    }

    public Map<String, Integer> getWeapons()
    {
        return weapons;
    }

}

