package ui;

import exceptions.DatabaseEmptyException;
import exceptions.InvalidInputException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Represents CarbonTrackerApp used to run console app.
public class CarbonTrackerApp {

    private static final String JSON_STORE = "./data/todayLog.json";
    private Log today;
    private List<Clothing> todayLogOutfit;
    private OutfitDatabase outfitDatabase;
    private List<Clothing> selectOutfit;
    private Scanner input;
    private Material material;
    private Country producer;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: begins the ui console
    public CarbonTrackerApp() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        today = new Log();
        todayLogOutfit = today.getTodayOutfit();
        outfitDatabase = today.getOutfitDB();
        selectOutfit = outfitDatabase.getOutfit();

        input = new Scanner(System.in);

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

            if (command.equals("q")) {
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
        System.out.println(""
                + "\u001B[7ml \u001B[0m Load File                      \u001B[7ms \u001B[0m Save File      "
                + "                   \u001B[7mq \u001B[0m Quit");
        System.out.println(""
                + "\u001B[7mv \u001B[0m View Outfit Log                \u001B[7mn \u001B[0m New Cloting    "
                +                "  "
                + "                 \u001B[7md \u001B[0m Delete Clothing ");
        System.out.println(""
                + "\u001B[7mx \u001B[0m High export impact clothing    \u001B[7mwf\u001B[0m High water footprint"
                + "              \u001B[7mw \u001B[0m View Total Water used per outfit");
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
        } else if (command.equals("v")) {
            doViewClothingLog();
        } else if (command.equals("w")) {
            doTotalWaterUsed();
        } else if (command.equals("x")) {
            doHighestClimateImpactByExport();
        } else if (command.equals("wf")) {
            doHighestClimateImpactByWater();
        } else if (command.equals("l")) {
            doLoadTodayLog();
        } else if (command.equals("s")) {
            doSaveTodayLog();
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

        Clothing clothing = null;
        try {
            clothing = new Clothing(name, producer, material);
        } catch (InvalidInputException e) {
            System.out.println("Invalid input");
        }

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
        System.out.println("Select a clothing to remove by typing its number:");
        Clothing clothing = selectClothingFromDB(todayLogOutfit);
        today.removeClothingFromLog(clothing);
        System.out.println(clothing.getName() + " has been removed from your log!");
    }

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
    // MODIFIES: this
    // EFFECTS: displays the clothing that has the highest rating in exporting
    private void doHighestClimateImpactByExport() {
        Clothing highestImpactClothing = null;
        try {
            highestImpactClothing = today.getOutfitDB().computeHighestImpactExport();
            System.out.println("Your " + highestImpactClothing.getName() + " has the highest impact "
                    + "on climate in terms of exporting clothes");
        } catch (DatabaseEmptyException e) {
            System.out.println("Outfit database is empty. "
                    + "Please create new clothes or add from wardrobe");
        }

    }

    // REQUIRES: outfit database must not be empty
    // MODIFIES: this
    // EFFECTS: displays the clothing that has the highest rating in exporting
    private void doHighestClimateImpactByWater() {
        Clothing highestImpactClothing = null;
        try {
            highestImpactClothing = today.getOutfitDB().computeHighestWaterFootprint();
            System.out.println("Your " + highestImpactClothing.getName() + " has the highest impact "
                    + "on climate in terms of water footprint");
        } catch (DatabaseEmptyException e) {
            System.out.println("Outfit database is empty. "
                    + "Please create new clothes or add from wardrobe");
        }

    }

    // REQUIRES: outfit database must not be empty
    // MODIFIES: this
    // EFFECTS: displays the total water used for today's log
    private void doTotalWaterUsed() {
        double waterFootprint = today.getTotalWaterFootprint();
        System.out.println("The total water footprint of today's outfit is " + waterFootprint + " liters");
    }

    // CITATION: this method has been modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the current log to file
    private void doSaveTodayLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(today);
            jsonWriter.close();
            System.out.println("Saved for today's date of" + today.getFullDate() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // CITATION: this method has been modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: loads the current log to file
    private void doLoadTodayLog() {
        try {
            today = jsonReader.read();
            todayLogOutfit = today.getTodayOutfit();
            outfitDatabase = today.getOutfitDB();
            selectOutfit = outfitDatabase.getOutfit();

            System.out.println("Log successfully loaded from file!");

        } catch (IOException | InvalidInputException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

}







