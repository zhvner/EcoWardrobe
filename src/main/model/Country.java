package model;

// Represent importing Country that produced the clothing in the wardrobe
//   rating the exporting countries of fast fashion clothings 1 - the most, 10 - the least
//     The rating for the country will help display the clothing that has the most impact
//     and the least impact on climate
public enum Country {
    CHINA(1),
    VIETNAM(2),
    BANGLADESH(3),
    INDIA(4),
    TURKEY(5),
    PAKISTAN(6),
    CAMBODIA(7),
    INDONESIA(8),
    SOUTH_KOREA(9),
    TAIPEI(10);

    private int rating;

    // EFFECTS: Constructs to set the rating for each country
    Country(int rating) {
        this.rating = rating;
    }

    // getter
    public int getRating() {
        return rating;
    }


}


