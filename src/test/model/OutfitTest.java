package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Country.BANGLADESH;
import static model.Country.CAMBODIA;
import static model.Material.COTTON;
import static model.Material.DENIM;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutfitTest {

    Log today;
    OutfitDatabase outfitDatabase;
    Clothing c1;
    Clothing c2;

    @BeforeEach
    public void runBefore() {
        today = new Log();
        outfitDatabase = today.getOutfitDB();
        c1 = new Clothing("top", BANGLADESH, COTTON);
        c2 = new Clothing("skirt", CAMBODIA, DENIM);
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



}
