package ui;

import model.Country;
import model.Material;

import javax.swing.*;
import java.awt.*;

public class NewClothingWindow {

    private JPanel wardrobePanel;
    private JLabel clothingNameTag;
    private JTextField clothingName;
    private JLabel countryLabel;
    private JComboBox country;
    private JLabel materialLabel;
    private JComboBox material;

    private JFrame frame;


    public NewClothingWindow() {
        initializeFields();



    }

    private void initializeFields() {
        frame = new JFrame("New Clothing");
        wardrobePanel = new JPanel();

        clothingNameTag = new JLabel("Enter Clothing Name");
        clothingName = new JTextField();
        clothingName.setMaximumSize(new Dimension(300, 50));

        countryLabel = new JLabel("Select Country");
        country = new JComboBox(Country.values());
        country.setMaximumSize(new Dimension(300, 25));

        materialLabel = new JLabel("Select Material");
        material = new JComboBox(Material.values());
        material.setMaximumSize(new Dimension(300, 25));


        wardrobePanel.add(clothingNameTag);
        wardrobePanel.add(clothingName);
        wardrobePanel.add(countryLabel);
        wardrobePanel.add(country);
        wardrobePanel.add(materialLabel);
        wardrobePanel.add(material);

        wardrobePanel.setLayout(new BoxLayout(wardrobePanel, BoxLayout.Y_AXIS));
        wardrobePanel.setMinimumSize(new Dimension(250,100));
        wardrobePanel.setPreferredSize(new Dimension(250,150));



    }

    public JPanel returnJPanel() {
        return wardrobePanel;
    }

    public String getClothingName() {
        return clothingName.getText();
    }

    public Material getMaterial() {
        return (Material) material.getSelectedItem();
    }

    public Country getCountry() {
        return (Country) country.getSelectedItem();
    }
}
