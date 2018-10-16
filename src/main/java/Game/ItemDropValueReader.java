package Game;

import Weapons.WeaponComponent;
import Weapons.WeaponComponentFactory;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ItemDropValueReader {

    private enum JsonData {
        ItemList("items"),
        Commons("commons"),
        Weapons("weapons");
        public final String field;

        JsonData(String field) {
            this.field = field;
        }
    }

    private final List<Item> commons = new ArrayList<>();
    private final List<WeaponComponent> weapons = new ArrayList<>();

    public ItemDropValueReader(String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (InputStream resourceAsStream = ItemDropValueReader.class.getClassLoader().getResourceAsStream(fileName);
             JsonReader reader = gson.newJsonReader(new InputStreamReader(resourceAsStream))) {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(reader);
            this.read(element.getAsJsonObject());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void read(JsonObject data) {
        JsonObject itemList = data.getAsJsonObject(JsonData.ItemList.field);
        this.readCommons(itemList);
        this.readWeapons(itemList);
    }

    private void readCommons(JsonObject data) {
        JsonArray commonsList = data.getAsJsonArray(JsonData.Commons.field);
        Gson gson = new Gson();
        for (JsonElement currentItem : commonsList) {
            JsonObject itemInfo = currentItem.getAsJsonObject();
            commons.add(gson.fromJson(itemInfo, Item.class));
        }
    }

    //#TODO parse all data from weapons THIS IS A STUB
    private void readWeapons(JsonObject data) {
        for (WeaponComponentFactory w : WeaponComponentFactory.values()) {
            weapons.add(w.create());
        }
    }

    public List<Item> getCommons() {
        return commons;
    }

    public List<WeaponComponent> getWeapons() {
        return weapons;
    }

}


