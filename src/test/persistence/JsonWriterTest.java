package persistence;

import exceptions.InvalidInputException;
import model.Clothing;
import model.Log;
import model.OutfitDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static model.Country.*;
import static model.Material.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest extends JsonTest{

    Clothing c1;
    Clothing c2;
    Clothing c3;

    @BeforeEach
    public void setup() throws InvalidInputException {

        c1 = new Clothing("skirt", BANGLADESH,COTTON);

        c2 = new Clothing("trainers", CHINA, DENIM);

        c3 = new Clothing("tshirt", PAKISTAN, TWEED);
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            Log log = new Log();
            JsonWriter writer = new JsonWriter("./data/invalid\0fileName.json");
            writer.open();
            fail("IOException expected but not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyLog() {
        try {
            Log log = new Log();
            JsonWriter writer = new JsonWriter("./data/TestWriterEmptyLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/TestWriterEmptyLog.json");
            log = reader.read();
            assertTrue(log.isTodayLogEmpty());
            assertEquals(0, log.getTotalWaterFootprint());
            assertEquals(0, log.getTodayOutfit().size());
            OutfitDatabase outfitDatabase = log.getOutfitDB();
            assertEquals(0, outfitDatabase.getOutfit().size());
        } catch (IOException | InvalidInputException e) {
            fail("IOException thrown, no exception expected");
        }
    }

    @Test
    public void testWriterFullLog() {
        try {
            Log log = new Log();
            log.addClothingToLog(c1);
            log.addClothingToLog(c2);
            log.addClothingToLog(c3);

            JsonWriter writer = new JsonWriter("./data/testWriterFullLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFullLog.json");
            log = reader.read();
            OutfitDatabase outfitDatabase = log.getOutfitDB();

            assertFalse(log.isTodayLogEmpty());
            assertEquals(20930.0, log.getTotalWaterFootprint());
            assertEquals(3, log.getTodayOutfit().size());
            assertEquals(6, outfitDatabase.getOutfit().size());

            List<Clothing> clothes = log.getTodayOutfit();
            List<Clothing> dbClothes = log.getOutfitDB().getOutfit();
            checkClothing("skirt", BANGLADESH, COTTON, clothes.get(0), dbClothes.get(0));
            checkClothing("trainers", CHINA, DENIM,  clothes.get(1), dbClothes.get(1));
            checkClothing("tshirt", PAKISTAN,TWEED, clothes.get(2), dbClothes.get(2));

        } catch (IOException | InvalidInputException e) {
            fail("IOException thrown, no exception expected");
        }
    }

}
