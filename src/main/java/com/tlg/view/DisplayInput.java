package com.tlg.view;

import com.tlg.controller.HeartsoarTower;
import com.tlg.model.Item;
import com.tlg.model.Player;

public class DisplayInput extends Display{
    private final static int MAX_LINES = 1;
    private String display;
    private String prevDisplay;
    Player player;

    public DisplayInput(Player player) {
        super(MAX_LINES);
        this.player = player;
    }

    public String getDisplay() {
        setDisplay();
        return display;
    }

    public void setDisplay() {
//        We will be displaying PlayerName: Current Location.  List of items.
        String location = player.getLocation().getName();
        String inventory = player.getInventory().stream().map(Item::getName).reduce("", (a, b) -> a + " " + b);
        this.display = "Command  >                    " + player.getName() + ".\tLocation: " + location + ".\tInventory: " + inventory + ".\t" +  getAmuletCharges();
    }

    public String getInventory() {
        String inventory = player.getInventory().stream().map(Item::getName).reduce("", (a, b) -> a + " " + b);
        //String amulet = "\nAmulet Charges: " + player.getAmuletCharges();
        return "Inventory: " + inventory;
    }

    public String getAmuletCharges() {
        if (player.getAmuletCharges() > 0) {
            return "Amulet Charges Left: " + player.getAmuletCharges();
        }
        return "";
    }

//    public String getPrevDisplay() {
//        setPrevDisplay();
//        return prevDisplay;
//    }
//
//    public void setPrevDisplay() {
////        We will be displaying PlayerName: Current Location.  List of items.
//        String location = player.getLocation().getName();
//        String inventory = player.getInventory().stream().map(e -> e.getName()).reduce("", (a, b) -> a + " " + b);
//        this.prevDisplay = "Command  >                    " + player.getName() + ".\tLocation: " + location + ".\tInventory: " + inventory;
//    }
}