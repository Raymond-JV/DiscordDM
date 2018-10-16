package Data;

import Game.Item;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;


public class JsonDataParser {


    private List<Item> commons;
    private List<JsonWeaponData> weapons;
    private JsonObject data;
    private Gson gson = new Gson();

    public JsonDataParser(String fileName) {

        this.data = JsonHelper.getJsonObject(fileName);
        this.data = this.data.getAsJsonObject(ItemFields.ItemList.field).getAsJsonObject();
    }

    private List<Item> readCommons() {
        commons = new ArrayList<>();
        JsonArray commonsList = data.getAsJsonArray(ItemFields.Commons.field);
        for (JsonElement currentItem : commonsList) {
            JsonObject itemInfo = currentItem.getAsJsonObject();
            commons.add(gson.fromJson(itemInfo, Item.class));
        }
        return commons;
    }
    private List<JsonWeaponData> readWeapons()
    {
        weapons = new ArrayList<>();
        JsonArray weaponList = data.getAsJsonArray(ItemFields.Weapons.field);
        for (JsonElement currentWeapon : weaponList) {
            {
                JsonObject weaponInfo = currentWeapon.getAsJsonObject();
                JsonWeaponData currentDump = gson.fromJson(weaponInfo, JsonWeaponData.class);
                weapons.add(currentDump);
            }
        }
        return weapons;
    }

    public List<Item> getCommons() {
    return (commons == null) ? readCommons() : commons;
    }

    public List<JsonWeaponData> getWeapons() {
    return (weapons == null) ? readWeapons() : weapons;
    }
}


