package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CountryTest {

    Country countryTest;

    @BeforeEach
    public void setUp(){
        countryTest = Country.CAMBODIA;
    }


    @Test
    public void testGetRating(){
        assertEquals(7,countryTest.getRating());
    }
}
