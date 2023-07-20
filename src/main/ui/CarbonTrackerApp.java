package ui;

import model.*;

import java.util.List;
import java.util.Scanner;

public class CarbonTrackerApp {

    private Log today;
    private List<Clothing> todayLogOutfit;
    private OutfitDatabase outfitDatabase;
    private List<Clothing> selectOutfit; // select
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
    // EFFECTS: processes user inputs in main menu
    private void processCommand(String command) {
        if (command.equals("new")) {
            doNewClothing();
        } else if (command.equals("add")) {
            doAddClothingFromDatabase();
        } else if (command.equals("del")) {
            doDeleteClothing();
        } else if (command.equals("c")) {
            doViewRatingByCountry();
        } else if (command.equals("log")) {
            doViewClothingLog();
        } else if (command.equals("view")) {
            doViewClothingInDatabase();
        } else {
            System.out.println("Invalid Selection. Please try again.");
        }
    }



    // EFFECTS: displays main menu
    private void displayMenu() {
        System.out.println("\nWelcome to GreenFit! Please select:");
        System.out.println("\tnew -> create new clothing from your wardrobe");
        System.out.println("\tadd -> add clothing from your wardrobe");
        System.out.println("\tdel -> delete a clothing entry");
        System.out.println("\tc -> view rating per supplying country ");
        System.out.println("\tlog -> view today's clothing log");
        System.out.println("\tview -> view a clothing");
        System.out.println("\tquit -> quit");
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

    private Material getMaterial() {
        System.out.println("What's the material of your clothing?");
        System.out.println("\tp -> polyester");
        System.out.println("\td -> denim");
        System.out.println("\ts -> silk");
        System.out.println("\tc -> cotton");
        System.out.println("\tn -> nylon");
        System.out.println("\tw -> wool");
        System.out.println("\tt -> tweed");
        System.out.println("\tsf -> synthetic fibre");

        String type = input.next();

        switch (type) {
            case "p" -> material = Material.POLYESTER;
            case "d" -> material = Material.DENIM;
            case "s" -> material = Material.SILK;
            case "c" -> material = Material.COTTON;
            case "n" -> material = Material.NYLON;
            case "w" -> material = Material.WOOL;
            case "t" -> material = Material.TWEED;
            case "sf" -> material = Material.SYNTHETIC_FIBRE;
            default -> System.out.println("Selection is not valid...");
        }
        return material;
    }


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
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

        switch (countryMade) {
            case "c" -> producer = Country.CHINA;
            case "v" -> producer = Country.VIETNAM;
            case "b" -> producer = Country.BANGLADESH;
            case "i" -> producer = Country.INDIA;
            case "t" -> producer = Country.TURKEY;
            case "p" -> producer = Country.PAKISTAN;
            case "cd" -> producer = Country.CAMBODIA;
            case "id" -> producer = Country.INDONESIA;
            case "k" -> producer = Country.SOUTH_KOREA;
            case "tp" -> producer = Country.TAIPEI;
        }
        //initProducer();
        return producer;
    }




    private void doAddClothingFromDatabase() {
        System.out.println("Select a clothing to add from your wardrobe by typing its number:");
        Clothing clothing = selectClothingFromDB(selectOutfit);
        today.addClothingToLog(clothing);
        System.out.println(clothing.getName() + " has been added to your log!");
    }

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

    private void doViewRatingByCountry() {
        Country producer = getProducer();
        int rating = producer.getRating();
        System.out.println(producer + " rating in terms of exporting fast fashion is " + rating + " ");
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
    // EFFECTS: searches database and displays a user selected clothing's information
    private void doViewClothingInDatabase() {
        System.out.println("Select a clothing from the database:");
        Clothing c = selectClothingFromDB(selectOutfit);
        System.out.println("Outfit Details:");
        printClothingInfo(c);
    }


}






