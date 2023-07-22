package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.Country.BANGLADESH;
import static model.Country.CAMBODIA;
import static model.Material.COTTON;
import static model.Material.DENIM;
import static org.junit.jupiter.api.Assertions.*;

public class LogTest {
    Log today;
    Clothing c1;
    Clothing c2;

    List<Clothing> outfitLogged;

    @BeforeEach
    public void runBefore() {
        today = new Log();
        c1 = new Clothing("top", BANGLADESH,COTTON);
        c2 = new Clothing("skirt", CAMBODIA,DENIM);

        outfitLogged = today.getTodayOutfit();
    }



    @Test
    public void testAddClothingToLogOneClothing() {
        today.addClothingToLog(c1);
        assertEquals(1, outfitLogged.size());

        assertEquals(6600, today.getTotalWaterFootprint());
    }

    @Test
    public void testAddClothingToLogTwoDifferentClothing() {
        today.addClothingToLog(c1);
        today.addClothingToLog(c2);

        assertEquals(2, outfitLogged.size());
        assertEquals(9930, today.getTotalWaterFootprint());
    }

    @Test
    public void testAddClothingToLogTwoSameClothes() {
        today.addClothingToLog(c1);
        today.addClothingToLog(c1);

        assertEquals(2, outfitLogged.size());
        assertEquals(13200, today.getTotalWaterFootprint());
    }

    @Test
    public void testAddClothingToLogOneTwoOne() {
        today.addClothingToLog(c1);
        today.addClothingToLog(c2);
        today.addClothingToLog(c1);

        assertEquals(3, outfitLogged.size());
        assertEquals(16530, today.getTotalWaterFootprint());
    }

    @Test
    public void testRemoveClothingFromLogEmpty() {
        assertEquals(0, outfitLogged.size());
        assertFalse(today.removeClothingFromLog(c1));
        assertEquals(0, today.getTotalWaterFootprint());
    }

    @Test
    public void testRemoveClothingFromLogOneClothing() {
        today.addClothingToLog(c1);
        assertTrue(today.removeClothingFromLog(c1));
        assertEquals(0, outfitLogged.size());
        assertEquals(0, today.getTotalWaterFootprint());
    }

    @Test
    public void testRemoveClothingFromLogTwoDifferent() {
        today.addClothingToLog(c1);
        today.addClothingToLog(c2);
        assertEquals(2, outfitLogged.size());
        assertEquals(9930, today.getTotalWaterFootprint());

        assertTrue(today.removeClothingFromLog(c1));
        assertEquals(1, outfitLogged.size());
        assertEquals(3330, today.getTotalWaterFootprint());

        assertTrue(today.removeClothingFromLog(c2));
        assertEquals(0, outfitLogged.size());
    }

    @Test
    public void testRemoveClothingFromLogTwoSame() {
        today.addClothingToLog(c1);
        today.addClothingToLog(c1);
        assertEquals(2, outfitLogged.size());
        assertEquals(13200, today.getTotalWaterFootprint());

        assertTrue(today.removeClothingFromLog(c1));
        assertEquals(1, outfitLogged.size());
        assertEquals(6600, today.getTotalWaterFootprint());

        assertTrue(today.removeClothingFromLog(c1));
        assertEquals(0, outfitLogged.size());
        assertEquals(0, today.getTotalWaterFootprint());

    }

    @Test
    public void testGetFullDate() {
        String presentDate = today.getFullDate();
        String[] gapDate = presentDate.split(" ");

        String dayOfWeek = gapDate[0];
        String month = gapDate[1];
        String dayNum = gapDate[2];

        assertTrue(isDayFormatCorrect(dayOfWeek));
        assertTrue(isMonthFormatCorrect(month));
        assertTrue(isDayNumberFormatCorrect(dayNum));
    }

    // REQUIRES: value passed must be a day of the week
    // EFFECTS: helper function to check if value is a valid day of the week
    public Boolean isDayFormatCorrect(String day) {
        String lcDate = day.toLowerCase();

        ArrayList<String> daysOfWeek = new ArrayList<>(Arrays.asList("monday", "tuesday", "wednesday", "thursday",
                "friday", "saturday", "sunday"));

        return (daysOfWeek.contains(lcDate));
    }

    // REQUIRES: value passed must be a month
    // EFFECTS: helper function to check if value is valid month
    public Boolean isMonthFormatCorrect(String month) {
        String lcMonth= month.toLowerCase();

        ArrayList<String> months = new ArrayList<>(Arrays.asList("january", "february", "march", "april", "may",
                "june", "july", "august", "september", "october", "november", "december"));

        return (months.contains(lcMonth));

    }

    // REQUIRES: value passed must be a day number
    // EFFECTS: helper function to check if value is inclusively between 1 and 31
    public Boolean isDayNumberFormatCorrect(String dayNum) {
        ArrayList<String> dayNumbers = new ArrayList<>();

        for (int i = 1; i <=31; i++) {
            dayNumbers.add(Integer.toString(i));
        }

        return (dayNumbers.contains(dayNum));
    }

}
