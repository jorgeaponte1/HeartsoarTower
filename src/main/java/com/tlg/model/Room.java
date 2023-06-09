package com.tlg.model;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Room {
    private final String name;
    private String[] desc = new String[3];
//    desc[0] = Monster Present Item Present
//    desc[1] = Monster not Present Item Present
//    desc[2] = Complete
    private List<String> nouns;
    private String monster;
    private final HashMap<String, String> neighborRooms;
    private boolean isDiscovered;
    private String graphic;

    public Room(String name, String[] desc, List<String> nouns, String monster,
                 String graphic, HashMap<String,String> neighborRooms) {
        this.name = name;
        this.desc = desc;
        this.nouns = nouns;
        this.monster = monster;
        this.neighborRooms = neighborRooms;
        this.graphic = graphic;
        this.isDiscovered = false;
    }

    public Room() {
        this.name = "Empty Room";
        this.desc = new String[]{"Empty Room", "Empty Room", "Empty Room"};
        this.nouns = null;
        this.monster = null;
        this.neighborRooms = null;
        this.graphic = null;
        this.isDiscovered = false;
    }

    public String getName() {
        return name;
    }

    public String[] getDesc() {
        return desc;
    }

    public List<String> getNouns() {
        return nouns;
    }

    public String getMonster() {
        return monster;
    }

    public HashMap<String, String> getNeighborRooms() {
        return neighborRooms;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public String getGraphic() {
        return graphic;
    }

    public void setNouns(List<String> nouns) {
        this.nouns = nouns;
    }

    public void setMonster(String monster) {
        this.monster = monster;
    }

    public void setDiscovered(boolean discovered) {
        isDiscovered = discovered;
    }

    public Item[] getItems() {
        return null;
    }

    public Icon getMapImage() {
        String imagePath = "/Images/";
        switch (getName().toLowerCase()) {
            case "entrance":
                imagePath += "map_entrance.png";
                break;
            case "foyer":
                imagePath += "map_foyer.png";
                break;
            case "kitchen":
                imagePath += "map_kitchen.png";
                break;
            case "plaisure":
                imagePath += "map_plaisure.png";
                break;
            case "den":
                imagePath += "map_den.png";
                break;
            case "ballroom":
                imagePath += "map_ballroom.png";
                break;
            case "conservatory":
                imagePath += "map_conservatory.png";
                break;
            case "bedroom":
                imagePath += "map_bedroom.png";
                break;
            default:
                imagePath += "default_map.png"; // Default image if the room name doesn't match any of the above cases
                break;
        }
        URL imageUrl = getClass().getResource(imagePath);

        // Scale the image to the original scaled size
        //noinspection ConstantConditions
        ImageIcon originalIcon = new ImageIcon(imageUrl);
        int newWidth = originalIcon.getIconWidth() / 2; // Adjust the divisor to match the scaling factor used for the original image
        int newHeight = originalIcon.getIconHeight() / 2; // Adjust the divisor to match the scaling factor used for the original image
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }
}