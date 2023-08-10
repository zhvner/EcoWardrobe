package model;

import exceptions.InvalidInputException;
import org.json.JSONObject;
import persistence.Writable;

// Represents Clothing in my wardrobe with importing name, country and material used to make it.
public class Clothing implements Writable {

    private String name;
    private Country country; // exporting country -- impact rating
    private Material material; // material using chemicals -- display chemicals used

    // EFFECTS: constructs clothing with name, exported/producing country and material
    public Clothing(String name, Country countries, Material materials) throws InvalidInputException {
        if (name.isEmpty() || materials == null || countries == null) {
            throw new InvalidInputException();
        }
        this.name = name;
        this.country = countries;
        this.material = materials;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public Material getMaterial() {
        return material;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country countries) {
        this.country = countries;
    }

    public void setMaterial(Material materials) {
        this.material = materials;
    }


    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: transforms a clothing object into a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("country", country);
        json.put("material", material);
        return json;
    }
}


