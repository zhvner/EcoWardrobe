package ui;

import exceptions.DatabaseEmptyException;
import model.*;

import java.util.List;
import java.util.Scanner;

// Represents CarbonTrackerApp used to run console app.
public class CarbonTrackerApp {

    private Log today;
    private List<Clothing> todayLogOutfit;
    private OutfitDatabase outfitDatabase;
    private List<Clothing> selectOutfit;
    private Scanner input;
    private Material material;
    private Country producer;

    /*
     * EFFECTS: begins the ui console
     */
    public CarbonTrackerApp() {
        runApp();
    }

    public void runApp() {
        boolean appRunning = true;
        String command = null;

        initLog();

        while (appRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                appRunning = false;
            } else {
                processCommand(command);
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes main objects
    public void initLog() {
        today = new Log();
        todayLogOutfit = today.getTodayOutfit();
        outfitDatabase = today.getOutfitDB();
        selectOutfit = outfitDatabase.getOutfit();

        input = new Scanner(System.in);
    }


    // MODIFIES: this
    // EFFECTS: displays main menu
    private void displayMenu() {
        System.out.println("\nWelcome to GreenFit! Please select:");
        System.out.println("\tn -> create new clothing");
        System.out.println("\ta -> add existing clothing from your wardrobe (database)");
        System.out.println("\td -> delete a clothing entry");
        //System.out.println("\tc -> view rating per supplying country ");
        System.out.println("\tl -> view today's clothing log");
        System.out.println("\tv -> view a clothing");
        System.out.println("\tw -> view today's water footprint of your outfit");
        System.out.println("\tx -> view the highest impact clothing in your outfit by export");
        System.out.println("\tr -> view the highest impact clothing in your outfit by water footprint");
        System.out.println("\tquit -> quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user inputs in main menu
    private void processCommand(String command) {
        if (command.equals("n")) {
            doNewClothing();
        } else if (command.equals("a")) {
            doAddClothingFromDatabase();
        } else if (command.equals("d")) {
            doDeleteClothing();
        } else if (command.equals("l")) {
            doViewClothingLog();
        } else if (command.equals("v")) {
            doViewClothingInDatabase();
        } else if (command.equals("w")) {
            doTotalWaterUsed();
        } else if (command.equals("x")) {
            doHighestClimateImpactByExport();
        } else if (command.equals("r")) {
            doHighestClimateImpactByWater();
        } else {
            System.out.println("Invalid Selection. Please try again.");
        }
    }


    // MODIFIES: this
    // EFFECTS: creates a new clothing and adds it to today's clothing log
    public void doNewClothing() {
        System.out.println("Enter Clothing Name:");
        String name = input.next();

        Material material = getMaterial();
        Country producer = getProducer();

        Clothing clothing = new Clothing(name, producer, material);

        today.addClothingToLog(clothing);

        System.out.println(clothing.getName() + " has been created and added to your log!");
    }

    // MODIFIES: this
    // EFFECTS: gets a material from user's input selected from the provided list
    private Material getMaterial() {
        System.out.println("What's the material of your clothing?");
        System.out.println("\tp -> polyester");
        System.out.println("\td -> denim");
        System.out.println("\ts -> silk");
        System.out.println("\tc -> cotton");
        System.out.println("\tn -> nylon");
        System.out.println("\tw -> wool");
        System.out.println("\tt -> tweed");

        String chosenMaterial = input.next();

        return chooseMaterial(chosenMaterial);
    }

    // MODIFIES: this
    // EFFECTS: returns a material selected from the provided list
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private Material chooseMaterial(String chosenMaterial) {
        switch (chosenMaterial) {
            case "p":
                material = Material.POLYESTER;
                break;
            case "d":
                material = Material.DENIM;
                break;
            case "s":
                material = Material.SILK;
                break;
            case "c":
                material = Material.COTTON;
                break;
            case "n":
                material = Material.NYLON;
                break;
            case "w":
                material = Material.WOOL;
                break;
            case "t":
                material = Material.TWEED;
                break;
            default:
                System.out.println("Selection is not valid...");
                break;
        }
        return material;
    }


    // MODIFIES: this
    // EFFECTS: gets a producer country from user's input selected from the provided
    public Country getProducer() {
        System.out.println("Which country produced  your clothing?");
        System.out.println("\tc -> China");
        System.out.println("\tv -> Vietnam");
        System.out.println("\tb -> Bangladesh");
        System.out.println("\ti -> India");
        System.out.println("\tt -> Turkey");
        System.out.println("\tp -> Pakistan");
        System.out.println("\tcd -> Cambodia");
        System.out.println("\tid -> Indonesia");
        System.out.println("\tk -> South Korea");
        System.out.println("\ttp -> Taipei");

        String countryMade = input.next();

        return chooseCountry(countryMade);
    }

    // MODIFIES: this
    // EFFECTS: returns a producing country from user's input selected from the displayed list
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private Country chooseCountry(String countryMade) {
        switch (countryMade) {
            case "c":
                producer = Country.CHINA;
                break;
            case "v":
                producer = Country.VIETNAM;
                break;
            case "b":
                producer = Country.BANGLADESH;
                break;
            case "i":
                producer = Country.INDIA;
                break;
            case "t":
                producer = Country.TURKEY;
                break;
            case "p":
                producer = Country.PAKISTAN;
                break;
            case "cd":
                producer = Country.CAMBODIA;
                break;
            case "id":
                producer = Country.INDONESIA;
                break;
            case "k":
                producer = Country.SOUTH_KOREA;
                break;
            case "tp":
                producer = Country.TAIPEI;
                break;
        }
        return producer;
    }

    // MODIFIES: this
    // EFFECTS: adds a clothing from log
    private void doAddClothingFromDatabase() {
        System.out.println("Select a clothing to add from your wardrobe by typing its number:");
        Clothing clothing = selectClothingFromDB(selectOutfit);
        today.addClothingToLog(clothing);
        System.out.println(clothing.getName() + " has been added to your log!");
    }

    // MODIFIES: this
    // EFFECTS: selects a clothing from log
    private Clothing selectClothingFromDB(List<Clothing> selectOutfit) {
        for (Clothing clothing : selectOutfit) {
            System.out.println("For " + clothing.getName() + " press -> " + selectOutfit.indexOf(clothing));
        }
        int index = input.nextInt();
        return selectOutfit.get(index);
    }

    // MODIFIES: this
    // EFFECTS: remove a clothing from log
    private void doDeleteClothing() {
        System.out.println("Select a meal to remove by typing its number:");
        Clothing clothing = selectClothingFromDB(todayLogOutfit);
        today.removeClothingFromLog(clothing);
        System.out.println(clothing.getName() + " has been removed from your log!");
    }

//    // MODIFIES: this
//    // EFFECTS: get an export rating for each country
//    private void doViewRatingByCountry() {
//        Country producer = getProducer();
//        int rating = producer.getRating();
//        System.out.println(producer + " rating in terms of exporting fast fashion is " + rating + " ");
//    }

    // EFFECTS: displays today's outfit log
    private void doViewClothingLog() {

        int clothingNum = 0;

        System.out.println("Outfit for " + today.getFullDate());
        System.out.println("-----------------------------");

        for (Clothing c : todayLogOutfit) {
            clothingNum++;
            System.out.println("Clothing #" + clothingNum);
            printClothingInfo(c);
            System.out.println("-----------------");

        }
    }

    // EFFECTS: prints today's outfit log
    private void printClothingInfo(Clothing c) {
        String name = c.getName();
        String producingCountry = String.valueOf(c.getCountry());
        String material = String.valueOf(c.getMaterial());

        System.out.println("Name: " + name);
        System.out.println("Producing Country: " + producingCountry);
        System.out.println("Material: " + material);
    }

    // REQUIRES: outfit database must not be empty
    // EFFECTS: searches database and displays a user selected clothing's information
    private void doViewClothingInDatabase() {
        System.out.println("Select a clothing from the database:");
        Clothing c = selectClothingFromDB(selectOutfit);
        System.out.println("Outfit Details:");
        printClothingInfo(c);
    }

    // REQUIRES: outfit database must not be empty
    // MODIFIES: this
    // EFFECTS: displays the clothing that has the highest rating in exporting
    private void doHighestClimateImpactByExport() {
        Clothing highestImpactClothing = today.getOutfitDB().computeHighestImpactExport();
        System.out.println("Your " + highestImpactClothing.getName() + " has the highest impact "
                + "on climate in terms of exporting clothes");
    }

    // REQUIRES: outfit database must not be empty
    // MODIFIES: this
    // EFFECTS: displays the clothing that has the highest rating in exporting
    private void doHighestClimateImpactByWater() {
        Clothing highestImpactClothing = today.getOutfitDB().computeHighestWaterFootprint();
        System.out.println("Your " + highestImpactClothing.getName() + " has the highest impact "
                + "on climate in terms of water footprint");
    }


    // REQUIRES: outfit database must not be empty
    // MODIFIES: this
    // EFFECTS: displays the total water used for today's log
    private void doTotalWaterUsed() {
        double waterFootprint = today.getTotalWaterFootprint();
        System.out.println("The total water footprint of today's outfit is " + waterFootprint + " liters");
    }

}







