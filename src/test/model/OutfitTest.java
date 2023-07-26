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
        assertTrue(outfitDatabase.isDBEmpty());
        today.addClothingToLog(c1);
        assertFalse(outfitDatabase.isDBEmpty());
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
    public void testHighestImpactExportThrown() {
        assertTrue(outfitDatabase.isDBEmpty());
        assertEquals(0, outfitDatabase.getOutfit().size());

        try {
            outfitDatabase.computeHighestImpactExport();
            fail("Database Empty Exception is not thrown");
        } catch (DatabaseEmptyException e) {
            // caught
        }

    }

    @Test
    public void testOneHighestImpactExportNotThrown() {
        outfitDatabase.addClothing(c2);
        assertEquals(1, outfitDatabase.getOutfit().size());
        assertEquals(7, c2.getCountry().getRating());

        Clothing highestImpactExport = null;
        try {
            highestImpactExport = outfitDatabase.computeHighestImpactExport();
        } catch (DatabaseEmptyException e) {
            fail();
        }
        assertEquals(c2, highestImpactExport);
    }


    @Test
    public void testMultipleHighestImpactExportNotThrown() {
        outfitDatabase.addClothing(c1);
        outfitDatabase.addClothing(c2);
        outfitDatabase.addClothing(c3);
        assertEquals(3, c1.getCountry().getRating());
        assertEquals(7, c2.getCountry().getRating());
        assertEquals(6, c3.getCountry().getRating());

        Clothing highestImpactExport = null;
        try {
            highestImpactExport = outfitDatabase.computeHighestImpactExport();
        } catch (DatabaseEmptyException e) {
            fail();
        }
        assertEquals(c1, highestImpactExport);
    }

    @Test
    public void testMultipleHighestImpactLastExportNotThrown() {
        outfitDatabase.addClothing(c2);
        outfitDatabase.addClothing(c1);

        assertEquals(7, c2.getCountry().getRating());
        assertEquals(3, c1.getCountry().getRating());


        Clothing highestImpactExport = null;
        try {
            highestImpactExport = outfitDatabase.computeHighestImpactExport();
            assertEquals(c1, highestImpactExport);
        } catch (DatabaseEmptyException e) {
            fail();
        }

    }

    @Test
    public void testComputeHighestWaterFootprintThrown() {
        assertTrue(outfitDatabase.isDBEmpty());
        try {
            outfitDatabase.computeHighestWaterFootprint();
            fail();
        } catch (DatabaseEmptyException e) {
            // caught
        }

    }

    @Test
    public void testOneComputeHighestWaterFootprintNotThrown() {
        outfitDatabase.addClothing(c1);
        assertEquals(6600, c1.getMaterial().getWaterFootprint());

        Clothing highestImpactByWater = null;
        try {
            highestImpactByWater = outfitDatabase.computeHighestWaterFootprint();
        } catch (DatabaseEmptyException e) {
            fail();
        }
        assertEquals(c1, highestImpactByWater);
    }

    @Test
    public void testMultipleComputeHighestWaterFootprintNotThrown() {
        outfitDatabase.addClothing(c1);
        outfitDatabase.addClothing(c2);
        outfitDatabase.addClothing(c3);
        assertEquals(6600, c1.getMaterial().getWaterFootprint());
        assertEquals(3330, c2.getMaterial().getWaterFootprint());
        assertEquals(11000, c3.getMaterial().getWaterFootprint());

        Clothing highestImpactByWater = null;
        try {
            highestImpactByWater = outfitDatabase.computeHighestWaterFootprint();
        } catch (DatabaseEmptyException e) {
            fail();
        }
        assertEquals(c3, highestImpactByWater);
    }

}
