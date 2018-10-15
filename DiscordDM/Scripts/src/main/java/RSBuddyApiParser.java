
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

//This script reads the RSBuddy api item dump and creates an appropriate json data set.
//The names and prices of interesting items are recorded to be used in Discord DM's drop system.

public class RSBuddyApiParser {

    private static String outputFile = "drop_table.txt";
    private static String apiFileName = "RSBuddyItemDump.json";
    private static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static Map<String, Integer> priceGuide = new HashMap<>();

    public static void main(String[] args) {
        parseApiDumpIntoMap();
        writeJson(createWeaponsJsonObject(), createCommonsJsonObject());

        System.out.printf("Parsing dump: '%s'...%n%n", apiFileName);
        BigInteger sum = BigInteger.valueOf(0);
        for (Integer i : priceGuide.values()) {
            sum = sum.add(BigInteger.valueOf(i));
        }


        System.out.printf("Common Items: %d, Average Price: %f%nWeapon Count: %d%n", priceGuide.size(),
                sum.doubleValue() / priceGuide.size(), WeaponItem.values().length);

        System.out.printf("%nDrop Table successfully written as '%s'", outputFile);

    }

    private static void parseApiDumpIntoMap() {
        JsonReader reader = gson.newJsonReader(new InputStreamReader(RSBuddyApiParser.class.getResourceAsStream(apiFileName)));
        JsonParser parser = new JsonParser();
        JsonElement data = parser.parse(reader);
        JsonObject itemMap = data.getAsJsonObject();

        List<String> garbageItems = Arrays.asList("steel", "iron", "bronze", "wizard", "ring");
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : itemMap.entrySet()) {

            JsonObject currentItem = stringJsonElementEntry.getValue().getAsJsonObject();
            String currentName = currentItem.get("name").getAsString().toLowerCase();

            int currentPrice = currentItem.get("buy_average").getAsInt();
            if (currentPrice == 0)
                currentPrice = currentItem.get("sell_average").getAsInt();
            if (currentPrice == 0)
                continue;
            if (currentPrice > 5000 && currentPrice < 10_000_000) {
                priceGuide.put(currentName, currentPrice);
            } else if (garbageItems.stream().anyMatch(currentName::contains) && currentPrice < 1000)
                priceGuide.put(currentName, currentPrice);
        }

        //Weapon drops are handled in the weapons drop table
        for (WeaponItem w : WeaponItem.values()) {
            priceGuide.remove(w.name.toLowerCase());
        }
    }

    private static JsonArray createWeaponsJsonObject() {
        JsonArray weaponList = new JsonArray();
        for (WeaponItem w : WeaponItem.values()) {
            JsonObject info = new JsonObject();
            info.addProperty("name", w.name.toLowerCase());
            info.addProperty("price", w.price);
            info.addProperty("maxHit", w.maxHit);
            info.addProperty("spec", w.spec);
            JsonArray codes = new JsonArray();
            for (String code : w.quickCodes) {
                codes.add(code);
            }
            info.add("quickCodes", codes);
            weaponList.add(info);
        }
        return weaponList;
    }

    private static JsonArray createCommonsJsonObject() {

        JsonArray commonList = new JsonArray();
        for (Map.Entry<String, Integer> currentItem : priceGuide.entrySet()) {
            JsonObject itemInfo = new JsonObject();
            itemInfo.addProperty("name", currentItem.getKey());
            itemInfo.addProperty("price", currentItem.getValue());
            commonList.add(itemInfo);
        }
        return commonList;
    }

    private static void writeJson(JsonArray weapons, JsonArray commons) {

        JsonObject itemLists = new JsonObject();
        itemLists.add("weapons", weapons);

        itemLists.add("commons", commons);
        JsonObject items = new JsonObject();
        items.add("items", itemLists);
        try (FileWriter out = new FileWriter(outputFile)) {
            gson.toJson(items, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

