package model;

// Presents materials used to make clothing.
// The following are the most common materials used in everyday clothes.
// Assumption is that I will use the app. My size is S/M.
// POLYESTER= 305g, DENIM =370g, SILK=200g, COTTON=340g,
// NYLON = 400g, WOOL = 300g, TWEED = 500g

// https://www.jongstit.com/en/blog/resources-consumption
// water used
// POLYESTER = 65 liters / kg
// DENIM = 9000 liters / kg
// SILK= 10000 litres/kg
// COTTON = 22000 liters / kg
// WOOL = 15,000 liters/ kg
// TWEED = 22,000 liters/ kg
// NYLON = 6000 liters / kg

// water footprint:
// POLYESTER= 65 * 0.305 = 19.825 liters
// DENIM = 9000 * 0.370 = 3330.00 liters
// SILK= 10000  * 0.2 = 2000.00 liters
// COTTON = 22000 * 0.300 = 6600.00 l
// NYLON = 6000 * 0.400 = 2400.00 l
// WOOL = 15000 * 0.3 = 4500.00 l
// TWEED = 22000 * 0.5 = 11000.00 l

public enum Material {
    POLYESTER(19.825),
    DENIM(3330),
    SILK(2000),
    COTTON(6600),
    NYLON(2400),
    WOOL(4500),
    TWEED(11000);

    double waterFootprint;

    //EFFECTS: Constructs Material with fixed precalculated double value
    //         water footprint assigned to the average clothing
    Material(double waterFootprint) {
        this.waterFootprint = waterFootprint;
    }

    public double getWaterFootprint() {
        return waterFootprint;
    }
}
