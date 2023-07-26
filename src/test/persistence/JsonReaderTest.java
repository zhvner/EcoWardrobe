package persistence;

import exceptions.InvalidInputException;
import model.Clothing;
import model.Log;
import model.OutfitDatabase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static model.Country.*;
import static model.Material.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Log log = reader.read();
            fail("IOException expected");
        } catch (IOException | InvalidInputException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLog() {
        JsonReader reader = new JsonReader("./data/TestReaderEmptyLog.json");
        try {
            Log log = reader.read();
            OutfitDatabase wardrobe = log.getOutfitDB();
            assertEquals(0, wardrobe.getNumClothes());
            assertEquals(0, log.getTotalWaterFootprint());
            assertEquals(0, log.getTodayOutfit().size());
            assertTrue(log.isTodayLogEmpty());
        } catch (IOException | InvalidInputException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFullLog() {
        JsonReader reader = new JsonReader("./data/testReaderFullLog.json");
        try {
            Log log = reader.read();
            List<Clothing> todayOutfit = log.getTodayOutfit();
            List<Clothing>  wardrobe = log.getOutfitDB().getOutfit();
            assertEquals(6919.825, log.getTotalWaterFootprint());
            assertEquals(3, todayOutfit.size());
            assertFalse(log.isTodayLogEmpty());


            assertEquals(5, wardrobe.size());
            assertFalse(log.getOutfitDB().isDBEmpty());

            checkClothing("top", SOUTH_KOREA,NYLON, todayOutfit.get(0), wardrobe.get(0));
            checkClothing("trousers", VIETNAM,WOOL, todayOutfit.get(1), wardrobe.get(1));
            checkClothing("leggings", CHINA,POLYESTER, todayOutfit.get(2), wardrobe.get(2));
            checkClothing("dress", TAIPEI,SILK, wardrobe.get(3));
            checkClothing("skirt", BANGLADESH,COTTON, wardrobe.get(4));

        } catch (IOException | InvalidInputException e) {
            fail("Couldn't read from file");
        }
    }


}
