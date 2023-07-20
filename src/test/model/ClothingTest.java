package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Country.*;
import static model.Material.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClothingTest {

    private Clothing clothingTest1;
    private Clothing clothingTest2;
    private Clothing clothingTest3;
    private Clothing clothingTest4;
    private Clothing clothingTest5;
    private Clothing clothingTest6;
    private Clothing clothingTest7;
    private Clothing clothingTest8;
    private Clothing clothingTest9;
    private Clothing clothingTest10;

    @BeforeEach
    public void runBefore() {
        clothingTest1 = new Clothing("top", BANGLADESH, COTTON);
        clothingTest2 = new Clothing("skirt", CAMBODIA, DENIM);
        clothingTest3 = new Clothing("jacket", PAKISTAN, POLYESTER);
        clothingTest4 = new Clothing("blazer", CHINA, SYNTHETIC_FIBRE);
        clothingTest5 = new Clothing("pants", TURKEY, NYLON);
    }

    @Test
    public void testConstructor1(){
        assertEquals("top", clothingTest1.getName());
        assertEquals(BANGLADESH, clothingTest1.getCountry());
        assertEquals(COTTON, clothingTest1.getMaterial());
    }

    @Test
    public void testConstructor2(){
        assertEquals("skirt", clothingTest2.getName());
        assertEquals(CAMBODIA, clothingTest2.getCountry());
        assertEquals(DENIM, clothingTest2.getMaterial());
    }



}
