package Data;

import Data.JsonHelper;
import Game.Combat.*;
import Game.Combat.Formula.AttackFormula;
import Game.Combat.Formula.AttackFormulaFactory;
import Game.Item;
import com.google.gson.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDataParser {


    private final JsonObject data;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public JsonDataParser(String fileName) {
        this.data = JsonHelper.getJsonObject(fileName);
    }

    public List<WeaponComponent> readWeapons() {

        JsonArray weaponData = data.getAsJsonObject(JsonField.ITEMS.value()).getAsJsonArray(JsonField.WEAPONS.value());
        List<WeaponComponent> weaponList = new ArrayList<>();

        for (JsonElement element : weaponData)
        {
            JsonObject weapon = element.getAsJsonObject();
            String name = weapon.get(JsonField.NAME.value()).getAsString();
            int value = weapon.get(JsonField.VALUE.value()).getAsInt();

            JsonArray attacks = weapon.get(JsonField.ATTACK.value()).getAsJsonArray();

            Map<String, Attack> variations = new HashMap<>();
            AttackFormulaFactory formulaBuilder = new AttackFormulaFactory();
            for (JsonElement e : attacks)
            {
               JsonObject attackData = e.getAsJsonObject();
               Attack newAttack = gson.fromJson(attackData, Attack.class);
               AttackFormula damageStrategy = formulaBuilder.create(newAttack.getCode()[0]);
               newAttack.setFormula(damageStrategy);

               for (String key : newAttack.getCode())
                     variations.put(key, newAttack);
            }
            weaponList.add(new WeaponComponent(name, value, variations));
        }
        return weaponList;
    }

    public List<Item> readCommons()
    {
        JsonArray commonData = data.getAsJsonObject(JsonField.ITEMS.value()).getAsJsonArray(JsonField.COMMONS.value());

        List<Item> commons = new ArrayList<>();

        for (JsonElement item : commonData)
            commons.add(gson.fromJson(item, Item.class));

        return commons;
    }

    private enum JsonField {

        WEAPONS("weapons"),
        ITEMS("items"),
        NAME("name"),
        VALUE("value"),
        ATTACK("attack"),
        COMMONS("commons");

        private String field;

        JsonField(String field) {
            this.field = field;
        }

        public String value()
        {
            return field;
        }
    }


}
