package Game;

import Weapons.WeaponComponent;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemDropValueReader {

    private String fileName;
    private List<Item> commons = new ArrayList<>();
    private List<WeaponComponent> weapons = new ArrayList<>();

    public ItemDropValueReader(String fileName) {
        this.fileName = fileName;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (InputStream resourceAsStream = ItemDropValueReader.class.getClassLoader().getResourceAsStream(fileName);
             JsonReader reader = new JsonReader(new InputStreamReader(resourceAsStream))) {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(reader);
            this.read(element.getAsJsonObject());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void read(JsonObject data) {
        JsonArray commonsList = data.getAsJsonObject("items").getAsJsonArray("commons");
        Gson gson = new Gson();
        for (JsonElement currentItem : commonsList) {
            JsonObject itemInfo = currentItem.getAsJsonObject();
            Item i = gson.fromJson(itemInfo, Item.class);
            System.out.println(i.info());
        }
    }

    public List<Item> getCommons() {
        return commons;
    }

    public List<WeaponComponent> getWeapons() {
        return weapons;
    }

}

