package model;

import java.util.ArrayList;
import java.util.List;

public class OutfitDatabase {
    private List<Clothing> outfit;

    // EFFECTS:  Constructs an empty OutfitDatabase
    public OutfitDatabase() {
        this.outfit = new ArrayList<>();
    }

    public List<Clothing> getOutfit() {
        return outfit;
    }

    // MODIFIES: this
    // EFFECTS:  Add a clothing to the OutfitDatabase
    public void addClothing(Clothing clothing) {
        this.outfit.add(clothing);
    }

    // REQUIRES: 0 <= index < outfit.size()
    // MODIFIES: this
    // EFFECTS:  Remove a clothing from the outfit

    public void removeClothing(int index) {
        this.outfit.remove(index);
    }

    // MODIFIES: this
    // EFFECTS: if new clothing, stores in database
    //          if not new, nothing happens
    public Boolean storeEntry(Clothing c) {
        if (!this.outfit.contains(c)) {
            this.outfit.add(c);
            return true;
        } else {
            return false;
        }
    }
}
