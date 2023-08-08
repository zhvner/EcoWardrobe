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

    @BeforeEach
    public void runBefore() {
        clothingTest1 = new Clothing("top", BANGLADESH, COTTON);
        clothingTest2 = new Clothing("skirt", CAMBODIA, DENIM);
        clothingTest3 = new Clothing("jacket", PAKISTAN, POLYESTER);
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

    @Test
    public void testSetCountry(){
        clothingTest1.setCountry(PAKISTAN);
        assertEquals(PAKISTAN, clothingTest1.getCountry());
    }

    @Test
    public void testSetMaterial(){
        clothingTest1.setMaterial(NYLON);
        assertEquals(NYLON, clothingTest1.getMaterial());
    }

    @Test
    public void testSetName(){
        clothingTest1.setName("shirt");
        assertEquals("shirt", clothingTest1.getName());
    }

//    @Test
//    void testToString() {
//        String expected = "Clothing{name='top', country=BANGLADESH, material=COTTON}";
//        assertEquals(expected, clothingTest1.toString());
//    }





}
