package persistence;

import model.Clothing;
import model.Country;
import model.Material;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkClothing(String name, Country country, Material material, Clothing... clothes) {
        for(Clothing c: clothes){
            assertEquals(name, c.getName());
            assertEquals(country, c.getCountry());
            assertEquals(material, c.getMaterial());
        }
    }
}
