package model;

import java.util.Arrays;

public class Clothing {

    private String name;
    private Country country; // exporting country -- impact rating
    private Material material; // material using chemicals -- display chemicals used

    public Clothing(String name, Country countries, Material materials) {
        this.name = name;
        this.country = countries;
        this.material = materials;
    }

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


    @Override
    public String toString() {
        return "Clothing{"
                + "name='" + name + '\''
                + ", country=" + country
                + ", material=" + material
                + '}';
    }


}


