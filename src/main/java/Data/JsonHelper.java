package Data;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonHelper {

    public static JsonObject getJsonObject(String fileName) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject data = null;

        try (InputStream resourceAsStream = JsonHelper.class.getClassLoader().getResourceAsStream(fileName);

             JsonReader reader = gson.newJsonReader(new InputStreamReader(resourceAsStream))) {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(reader);
            data = element.getAsJsonObject();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return data;
    }
}
