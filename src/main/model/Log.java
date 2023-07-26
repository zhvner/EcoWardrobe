package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Presents a log to the diary with today's inserted date, today's outfit (list of clothes) and
// adding the outfit to the database
public class Log implements Writable {
    private String name;

    private final String fullDate;
    private final List<Clothing> todayOutfit;
    private final OutfitDatabase outfitDB;

    private double totalWaterFootprint;
    private Clothing highestImpactClothing;

    // EFFECTS: constructs Log with today's outfit log with present date,
    //          instantiates the outfit database and displays clothing with the most
    //          impact
    public Log() {
        LocalDateTime date = LocalDateTime.now();
        String month = date.getMonth().name();
        String dayOfWeek = date.getDayOfWeek().name();
        int dayNumber = date.getDayOfMonth();
        this.fullDate = dayOfWeek + " " + month + " " + dayNumber;

        this.todayOutfit = new ArrayList<>();
        this.outfitDB = new OutfitDatabase();
        this.totalWaterFootprint = 0;
    }

    // MODIFIES: this, OutfitDatabase
    // EFFECTS: adds clothing to today's log and to clothes database if new
    //          updates total calories
    public void addClothingToLog(Clothing c) {
        this.todayOutfit.add(c);
        outfitDB.storeEntry(c);
        totalWaterFootprint += c.getMaterial().getWaterFootprint();
    }

    // MODIFIES: this, OutfitDataBase
    // EFFECTS: adds clothing to database only
    public void storeClothingInDatabase(Clothing c) {
        outfitDB.storeEntry(c);
    }


    // MODIFIES: this
    // EFFECTS: if clothing included in log, removes it, returns true
    //          returns false if clothing not in log
    public boolean removeClothingFromLog(Clothing c) {
        if (todayOutfit.contains(c)) {
            this.todayOutfit.remove(c);
            totalWaterFootprint -= c.getMaterial().getWaterFootprint();
            return true;
        } else {
            return false;
        }
    }

////     MODIFIES: this
////     EFFECTS: if clothing index is in range, removes clothing and deducts water used per material, returns true
////              returns false if index not range
//    public Boolean removeClothingFromLog(int index) {
//        if (!(index >= todayOutfit.size())) {
//            double water = todayOutfit.get(index).getMaterial().getWaterFootprint();
//            this.todayOutfit.remove(index);
//            totalWaterFootprint -= water;
//            return true;
//        } else {
//            return false;
//        }
//    }

    //getters
    public String getFullDate() {
        return fullDate;
    }

    public List<Clothing> getTodayOutfit() {
        return todayOutfit;
    }

    public OutfitDatabase getOutfitDB() {
        return outfitDB;
    }

    public double getTotalWaterFootprint() {
        return totalWaterFootprint;
    }

    // CITATION: remodelled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: converts outfit data in log and database to JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Today's date", getFullDate());
        json.put("Today's outfit", clothesToJson(todayOutfit));
        json.put("Wardrobe clothes", clothesToJson(outfitDB.getOutfit()));
        return json;
    }

    // CITATION: remodeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns clothes in today's log as a JSON array
    private JSONArray clothesToJson(List<Clothing> clothingList) {
        JSONArray jsonArray = new JSONArray();

        for (Clothing c : clothingList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    public boolean isTodayLogEmpty() {
        return todayOutfit.isEmpty();
    }

}
