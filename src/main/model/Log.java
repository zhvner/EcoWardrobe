package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Log {

    private final String fullDate;
    private final List<Clothing> todayOutfit;
    private final OutfitDatabase outfitDB;



    public Log() {
        LocalDateTime date = LocalDateTime.now();
        String month = date.getMonth().name();
        String dayOfWeek = date.getDayOfWeek().name();
        int dayNumber = date.getDayOfMonth();
        this.fullDate = dayOfWeek + " " + month + " " + dayNumber;

        this.todayOutfit = new ArrayList<>();
        this.outfitDB = new OutfitDatabase();
    }

    // MODIFIES: this, OutfitDatabase
    // EFFECTS: adds clothing to today's log and to meal database if new
    //          updates total calories
    public void addClothingToLog(Clothing c) {
        this.todayOutfit.add(c);
        outfitDB.storeEntry(c);
    }


    // MODIFIES: this
    // EFFECTS: if clothing included in log, removes it, returns true
    //          returns false if clothing not in log
    public boolean removeClothingFromLog(Clothing c) {
        if (todayOutfit.contains(c)) {
            this.todayOutfit.remove(c);
            return true;
        } else {
            return false;
        }
    }

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
}
