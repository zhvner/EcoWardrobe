package model;

import exceptions.DatabaseEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Country.*;
import static model.Material.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutfitTest {

    Log today;
    OutfitDatabase outfitDatabase;
    Clothing c1;
    Clothing c2;
    Clothing c3;

    @BeforeEach
    public void runBefore() {
        today = new Log();
        outfitDatabase = today.getOutfitDB();
        c1 = new Clothing("top", BANGLADESH, COTTON);
        c2 = new Clothing("skirt", CAMBODIA, DENIM);
        c3= new Clothing("jacket", PAKISTAN, TWEED);
    }

    @Test
    public void testStoreEntry(){
        List<Clothing> todayLogged = outfitDatabase.getOutfit();

        Boolean storedOutfit = outfitDatabase.storeEntry(c1);
        assertEquals(1, todayLogged.size());
        assertTrue(storedOutfit);

        storedOutfit = outfitDatabase.storeEntry(c2);
        assertEquals(2, todayLogged.size());
        assertTrue(storedOutfit);

        storedOutfit = outfitDatabase.storeEntry(c1);
        assertEquals(2, todayLogged.size());
        assertFalse(storedOutfit);
    }

    @Test
    public void testGetClothingByIndex() {
        outfitDatabase.storeEntry(c1);
        outfitDatabase.storeEntry(c2);

        Clothing getClothing1 = outfitDatabase.getOutfitByIndex(0);
        Clothing getClothing2 = outfitDatabase.getOutfitByIndex(1);

        assertEquals(c1.getName(), getClothing1.getName());
        assertEquals(c2.getName(), getClothing2.getName());
    }

    @Test
    public void testIsDatabaseEmpty() {
        assertTrue(outfitDatabase.isDatabaseEmpty());
        today.addClothingToLog(c1);
        assertFalse(outfitDatabase.isDatabaseEmpty());
    }

    @Test
    public void testAddClothing(){
        outfitDatabase.addClothing(c1);
        outfitDatabase.addClothing(c2);
        assertEquals(c1, outfitDatabase.getOutfitByIndex(0));
        assertEquals(c2, outfitDatabase.getOutfitByIndex(1));
        assertEquals(2, outfitDatabase.getNumClothes());
    }

    @Test
    public void testRemoveClothing(){
        outfitDatabase.addClothing(c1);
        outfitDatabase.addClothing(c2);
        assertEquals(c1, outfitDatabase.getOutfitByIndex(0));
        assertEquals(c2, outfitDatabase.getOutfitByIndex(1));
        assertEquals(2, outfitDatabase.getNumClothes());

        outfitDatabase.removeClothing(0);
        assertEquals(1, outfitDatabase.getNumClothes());
        assertEquals(c2, outfitDatabase.getOutfitByIndex(0));



    }

    @Test
    public void testHighestImpactExport() {
        outfitDatabase.addClothing(c1);
        outfitDatabase.addClothing(c2);
        assertEquals(2, outfitDatabase.getOutfit().size());

        Clothing highestImpactExport = null;
        try {
            highestImpactExport = outfitDatabase.computeHighestImpactExport();
        } catch (DatabaseEmptyException e) {
            fail();
        }
        assertEquals(c1, highestImpactExport);
    }

    @Test
    public void testComputeHighestWaterFootprint() {
        outfitDatabase.addClothing(c1);
        outfitDatabase.addClothing(c2);
        outfitDatabase.addClothing(c3);

        Clothing highestImpactByWater = null;
        try {
            highestImpactByWater = outfitDatabase.computeHighestWaterFootprint();
        } catch (DatabaseEmptyException e) {
            fail();
        }
        assertEquals(c3, highestImpactByWater);
    }






}
