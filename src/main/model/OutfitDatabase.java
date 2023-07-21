package model;

import exceptions.DatabaseEmptyException;

import java.util.ArrayList;
import java.util.List;

// Represents OutfitDatabase as in my wardrobe.
// In the wardrobe, there are clothes that eventually construct outfit
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
    public boolean storeEntry(Clothing c) {
        if (!this.outfit.contains(c)) {
            this.outfit.add(c);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns number of clothes in the database
    public int getNumClothes() {
        return this.outfit.size();
    }

    // EFFECTS: returns clothing from the outfit database by its index
    public Clothing getOutfitByIndex(int i) {
        return outfit.get(i);
    }

    // EFFECTS: returns true if database is empty, false otherwise
    public boolean isDatabaseEmpty() {
        return outfit.isEmpty();
    }

    // REQUIRES: outfit database must not be empty
    // EFFECTS: returns the highest climate impact clothing in wardrobe,
    //          based on fast fashion exporting country's rating
    //          (in case of tie, returns the first of the tied sources)
    public Clothing computeHighestImpactExport() {
        int highestIndex = 0;
        for (int i = 0; i <= this.getNumClothes(); i++) {
            int rating = getOutfitByIndex(i).getCountry().getRating();
            int highestRating = getOutfitByIndex(highestIndex).getCountry().getRating();
            if (rating < highestRating) {
                highestIndex = i;
                return getOutfitByIndex(highestIndex);
            }
        }
        return getOutfitByIndex(highestIndex);
    }

    // REQUIRES: outfit database must not be empty
    // EFFECTS: returns the highest total climate impact clothing in wardrobe,
    //          based on water footprint
    //          (in case of tie, returns the first of the tied sources)
    public Clothing computeHighestWaterFootprint() {
        int highestIndex = 0;
        for (int i = 0; i < this.getNumClothes(); i++) {
            double water = getOutfitByIndex(i).getMaterial().getWaterFootprint();
            double highestWater = getOutfitByIndex(highestIndex).getMaterial().getWaterFootprint();
            if (water > highestWater) {
                highestIndex = i;
                return getOutfitByIndex(highestIndex);
            }
        }
        return getOutfitByIndex(highestIndex);
    }

}

