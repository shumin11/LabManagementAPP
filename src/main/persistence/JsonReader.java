package persistence;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Item;
import model.Type;
import model.TypeList;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads lab inventory information from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TypeList read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseTypeList(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses types from JSON object and returns it
    private TypeList parseTypeList(JSONArray jsonArray) {
        TypeList typeList = new TypeList();
        for (Object json : jsonArray) {
            JSONObject nextType = (JSONObject) json;
            addType(typeList, nextType);
        }
        return typeList;
    }

    // MODIFIES: typeList
    // EFFECTS: parses type from JSON object and add them to typeList
    private void addType(TypeList typeList, JSONObject jsonObject) {
        String typeName = jsonObject.getString("type name");
        Type type = new Type(typeName);
        addItems(type, jsonObject);
        typeList.getTypes().add(type);
    }

    // MODIFIES: type
    // EFFECTS: parses items from JSON object and adds them to type
    private void addItems(Type type, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(type, nextItem);
        }
    }

    // MODIFIES: type
    // EFFECTS: parses item from JSON object and adds it to type
    private void addItem(Type type, JSONObject jsonObject) {
        String itemName = jsonObject.getString("item name");
        int amount = jsonObject.getInt("amount");
        String location = jsonObject.getString("location");
        String vendor = jsonObject.getString("vendor");
        String updated = jsonObject.getString("updated");
        int cutoff = jsonObject.getInt("cutoff");
        Boolean toOrder = jsonObject.getBoolean("toOrder");
        Item item = new Item(itemName, amount, location, vendor, updated, cutoff);
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (int i = 0; i < jsonArray.length(); i++) {
            item.addNote(jsonArray.getString(i));
        }
        String typeName = type.getTypeName();
        type.addItemToType(typeName, item);
    }
}
