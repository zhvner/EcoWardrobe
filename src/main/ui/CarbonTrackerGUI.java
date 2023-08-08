package ui;

import exceptions.DatabaseEmptyException;
import exceptions.InvalidInputException;
import model.Clothing;
import model.Country;
import model.Log;
import model.Material;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

// Represents GUI for CarbonTracker of outfits

public class CarbonTrackerGUI extends JFrame implements ListSelectionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    //Header pane
    private Log todayLog;

    private JLabel date;
    private JLabel totalWater;
    JPanel headerPanel;

    //left pane
    private JTabbedPane leftPane;
    private JPanel wardPanel;
    private DefaultListModel<Object> outfitModel;

    private JList<Clothing> wardList;
    private JList<Object> infoList;
    private JButton addClothing;
    private JButton newClothing;
    private JButton removeClothing;
    private JScrollPane wardScrollPane;


    // right pane
    private JTabbedPane rightPane;
    private JPanel infoPanel;
    private JLabel infoLabel;
    private JPanel insightPanel;
    private JLabel insightLabel;

    // Menu Bar
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveButton;
    private JMenuItem loadButton;

    //Data
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/saves/todayLog.json";
    private JsonWriter jsonWriter;

    private final ImageIcon imageIcon = new ImageIcon("./src/splashScreen.png");


    // EFFECTS: Creates and arranges the main app window.
    public CarbonTrackerGUI() throws DatabaseEmptyException {
        super("GreenOutfit - Sustainable Outfit Diary");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        todayLog = new Log();
        outfitModel = new DefaultListModel();

        initializeDataHandler();
        createHeaderPanel();
        addLeftPane();
        addRightPane();
        createMenuBar();

        splashScreen();
        loadDataBefore();
        saveDataAfter();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: initializes data fields
    private void initializeDataHandler() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        todayLog = new Log();
        outfitModel = new DefaultListModel<>();
    }

    // MODIFIES: this
    // EFFECTS: creates header panel for today's date and water footprint
    private void createHeaderPanel() throws DatabaseEmptyException {
        JPanel headerPane = new JPanel();
        headerPane.setLayout(new FlowLayout());

        date = new JLabel(todayLog.getFullDate());
        JPanel datePane = new JPanel();
        datePane.add(date);
        datePane.setPreferredSize(new Dimension(200, 30));
        datePane.setBorder(BorderFactory.createLineBorder(Color.black));

        totalWater = new JLabel("Total Water Used: " + todayLog.getTotalWaterFootprint() + " liters");
        JPanel waterPane = new JPanel();
        waterPane.add(totalWater);
        waterPane.setPreferredSize(new Dimension(320, 30));
        waterPane.setBorder(BorderFactory.createLineBorder(Color.black));


        headerPane.add(datePane);
        headerPane.add(Box.createHorizontalStrut(50));
        headerPane.add(waterPane);
        add(headerPane, BorderLayout.NORTH);

    }

    // MODIFIES: this
    // EFFECTS: creates left panel with buttons to add, remove, new and list of clothes created
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void addLeftPane() {
        leftPane = new JTabbedPane();
        leftPane.setForeground(Color.black);
        wardPanel = new JPanel();
        wardPanel.setLayout(new BoxLayout(wardPanel, BoxLayout.Y_AXIS));

        outfitModel = new DefaultListModel<>();
        wardList = new JList(outfitModel);
        wardList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        initializeButtonsLeftPane();

        // wardList.addListSelectionListener(e -> onWardSelectionChange(e));
        wardList.setFixedCellWidth(300);
        wardList.setFixedCellHeight(15);
        wardScrollPane = new JScrollPane(wardList);
        wardScrollPane.setPreferredSize(new Dimension(300, 350));
        wardScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //removeClothing.addActionListener(e -> removeClothing());

        this.wardPanel.add(addClothing);
        this.wardPanel.add(newClothing);
        this.wardPanel.add(removeClothing);
        this.wardPanel.add(wardScrollPane);
        // Add Tabs
        leftPane.add("Wardrobe", this.wardPanel);

        add(leftPane, BorderLayout.WEST);
    }

    // EFFECTS: initialises buttons on left panel
    private void initializeButtonsLeftPane() {
        newClothing = new JButton("New Clothing     ");
        newClothing.setActionCommand("new");
        newClothing.addActionListener(new ButtonListener());

        addClothing = new JButton("Add Clothing     ");
        addClothing.setActionCommand("add");
        addClothing.addActionListener(new ButtonListener());

        removeClothing = new JButton("Remove Clothing");
        removeClothing.setActionCommand("remove");
        removeClothing.addActionListener(new ButtonListener());

    }


    // EFFECTS: Prevents deselect and sets the rightPane to reflect the newly selected object.
    // MODIFIES: this
    private void onWardSelectionChange(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        preventWardDeselect();
        if (wardList.getSelectedIndex() != -1) {
            switchInfoPanel();
        }
    }

    // EFFECTS: Prevents wardlist from deselect by turning selected index to 0 if deselected.
    // MODIFIES: this
    private void preventWardDeselect() {
        if (wardList.getSelectedIndex() == -1) {
            wardList.setSelectedIndex(0);
        }
    }

    // EFFECTS: switches with info pane while maintaining selected tab.
    // MODIFIES: this
    private void switchInfoPanel() {
        setInfoLabel();
        int maintainTabIndex = rightPane.getSelectedIndex();
        rightPane.remove(0);
        rightPane.add(infoPanel, 0);
        rightPane.setTitleAt(0, "Basic Info");
        rightPane.setSelectedIndex(maintainTabIndex);
    }

    // EFFECTS: sets info label text
    // MODIFIES: this
    private void setInfoLabel() {
        String info = "<html><pre>Name: " + getSelectedClothing() + "\n\nCountry: "
                + getSelectedClothing().getCountry();

        info += "\n\nMaterial:" + getSelectedClothing().getMaterial();

        info += "</pre></html>";
        infoLabel.setText(info);
    }


    // MODIFIES: this
    // EFFECTS: creates right panel with info and insights pane
    private void addRightPane() {
        JPanel main = new JPanel(new GridLayout(1, 2));
        rightPane = new JTabbedPane();
        rightPane.setForeground(Color.black);
        // Info Panel
        infoPanel = new JPanel();
        infoLabel = new JLabel();
        infoLabel.setPreferredSize(new Dimension(300, 350));
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
        infoPanel.add(infoLabel);
        // Insight Panel
        insightPanel = new JPanel();
        insightLabel = new JLabel();
        insightLabel.setText("Coming soon!");
        insightPanel.add(insightLabel);

        // Add Tabs
        //rightPane.add("Basic Info", infoPanel);
        //setInfoLabel();
        rightPane.add("Insight", insightPanel);
        // Add Pane
        main.add(rightPane);
        add(rightPane, BorderLayout.CENTER);
    }

    // right pane handlers

    // EFFECTS: get selected clothing in Jlist
    private Clothing getSelectedClothing() {
        return null;
        //return outfitModel.get(wardList.getSelectedIndex());
    }


    // MODIFIES: this
    // EFFECTS: creates menu bar with save and load buttons
    private void createMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveButton = new JMenuItem("Save As...");
        //saveButton.addActionListener(e -> saveData());
        loadButton = new JMenuItem("Load Save...");
        // loadButton.addActionListener(e -> loadDataBefore());
        fileMenu.add(saveButton);
        fileMenu.add(loadButton);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }

    // menu bar handlers

    // CITATION: this method has been remodeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: initializes load prompt popup window on start
    private void loadDataBefore() {

        int loadOption = JOptionPane.showConfirmDialog(null,
                "Would you like to load your last log?", "Load File",
                JOptionPane.YES_NO_OPTION);
        if (loadOption == JOptionPane.YES_OPTION) {
            try {
                todayLog = jsonReader.read();
                updateClothes();
            } catch (IOException | InvalidInputException e) {
                System.out.println("Unable to read from file " + JSON_STORE);
            }
        }
    }

    // EFFECTS: updates clothing and water footprint on screen based on material on screen
    private void updateClothes() {
        outfitModel.clear();
        List<Clothing> clothingLog = todayLog.getTodayOutfit();
        for (Clothing clothing : clothingLog) {
            //String name = clothing.getName();
            outfitModel.addElement(clothing.getName());

        }
        updateWaterFootprint();
    }

    // EFFECTS: updates water footprint on screen
    private void updateWaterFootprint() {
        double waterFootprint = todayLog.getTotalWaterFootprint();
        String formattedWaterFootprint = String.format("%.2f", waterFootprint);
        totalWater.setText("Total Water used: " + formattedWaterFootprint + " liters");
    }


    // CITATION: this method has been remodeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: initializes save prompt popup window at the end
    private void saveDataAfter() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                int saveOption = JOptionPane.showConfirmDialog(null,
                        "Would you like to save your log before exiting?", "Save File Prompt",
                        JOptionPane.YES_NO_OPTION);
                if (saveOption == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(todayLog);
                        jsonWriter.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }
                    dispose();
                }
            }
        });
    }

    //splash screen

    // EFFECTS: displays a splash screen for 2 seconds.
    private void splashScreen() {
        JWindow splashScreen = new JWindow();
        splashScreen.setSize(500, 600);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.add(new JLabel(new ImageIcon("./src/splashScreen.png")));
        splashScreen.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            splashScreen.setVisible(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: monitors selected clothing index for change on mouse click and updates insights window
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            int index = wardList.getSelectedIndex();
            updateInfoWindow(index);
        }
    }

    private void updateInfoWindow(int index) {
    }


    // creates Action Listener for button presses
    class ButtonListener implements ActionListener {
        // EFFECTS: triggers button clicks and runs designated methods
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "add":
                    addClothingAction();
                    break;
                case "new":
                    newClothingAction();
                    break;
                case "remove":
                    removeClothingAction();
                    break;
            }
        }

        // MODIFIES: this, todayLog
        // EFFECTS: creates popup window and interface for adding clothes from database
        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        private void addClothingAction() {
            JPanel panel = new JPanel(new BorderLayout());
            List<Clothing> outfitDB = todayLog.getOutfitDB().getOutfit();
            String[] clothStringList = new String[outfitDB.size()];
            int pos = 0;
            for (Clothing clothing : outfitDB) {
                String name = clothing.getName();
                Country country = clothing.getCountry();
                Material material = clothing.getMaterial();
                clothStringList[pos] = String.format("%s  -  %d material", name);
                pos++;
            }

            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

            Object selectedClothing = JOptionPane.showInputDialog(panel,
                    "Select a clothing", "Add clothing from database",
                    JOptionPane.QUESTION_MESSAGE, scaledImageIcon,
                    clothStringList, 1);

            if (selectedClothing != null) {
                String selectedClothingValue = selectedClothing.toString();
                int selectedClothingIndex = Arrays.asList(clothStringList).indexOf(selectedClothingValue);
                Clothing clothing = outfitDB.get(selectedClothingIndex);
                todayLog.addClothingToLog(clothing);
                updateClothes();

            }
        }


        // MODIFIES: this, todayLog
        // EFFECTS: initialises a new window and interface for new clothing,
        // adds clothing to today's log and database if a clothing was created and updates screen
        private void newClothingAction() {
            NewClothingWindow newClothingWindow = new NewClothingWindow();
            JPanel panel = newClothingWindow.returnJPanel();

            int optionPaneValue = JOptionPane.showConfirmDialog(
                    null, panel,
                    "Create New Clothing",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (optionPaneValue == JOptionPane.OK_OPTION) {
                try {
                    String clothingName = newClothingWindow.getClothingName();
                    Material material = newClothingWindow.getMaterial();
                    Country country = newClothingWindow.getCountry();
                    Clothing newClothing = new Clothing(clothingName, country, material);

                    todayLog.addClothingToLog(newClothing);
                    updateClothes();
                    //int index = outfitModel.indexOf(newClothing);
                } catch (InvalidInputException e) {
                    System.out.println("Invalid input!");
                }
            }

        }

        // MODIFIES: this, todayLog
        // EFFECTS: removes a selected clothing from today's log and updates left pane
        private void removeClothingAction() {
            try {
                int index = wardList.getSelectedIndex();
                todayLog.removeClothingFromLog(index);
                updateClothes();

            } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("wrong");
            }
        }
    }


    public static void main(String[] args) throws DatabaseEmptyException {
        new CarbonTrackerGUI();
    }


}
