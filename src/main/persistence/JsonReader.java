package persistence;

import exceptions.InvalidInputException;
import model.Clothing;
import model.Country;
import model.Log;
import model.Material;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.stream.Stream;

public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads today's Log from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Log read() throws IOException, InvalidInputException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses today's log from JSON object and returns it
    private Log parseLog(JSONObject jsonObject) throws InvalidInputException {
        Log log = new Log();
        // Adding today's outfit
        JSONArray jsonArrayOutfit = jsonObject.getJSONArray("Today's outfit");
        for (Object json : jsonArrayOutfit) {
            JSONObject nextClothing = (JSONObject) json;
            addClothing(log, nextClothing);
        }

        // Adding wardrobe clothes
        JSONArray jsonArrayWardrobe = jsonObject.getJSONArray("Wardrobe clothes");
        for (Object json : jsonArrayWardrobe) {
            JSONObject nextClothing = (JSONObject) json;
            Clothing clothing = returnJsonClothing(nextClothing);
            log.storeClothingInDatabase(clothing);
        }
        return log;
    }


    // MODIFIES: log
    // EFFECTS: parses clothing from JSON object and adds it to today's log
    private void addClothing(Log todayLog, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Country country = Country.valueOf(jsonObject.getString("country"));
        Material material = Material.valueOf(jsonObject.getString("material"));

        Clothing clothing = null;
        try {
            clothing = new Clothing(name, country, material);
        } catch (InvalidInputException e) {
            System.out.println("Invalid input");
        }
        todayLog.addClothingToLog(clothing);
    }

    // EFFECTS: parses clothing from JSON object and returns it
    private Clothing returnJsonClothing(JSONObject jsonObject) throws InvalidInputException {
        String name = jsonObject.getString("name");
        Country country = Country.valueOf(jsonObject.getString("country"));
        Material material = Material.valueOf(jsonObject.getString("material"));

        return new Clothing(name, country, material);
    }


}
