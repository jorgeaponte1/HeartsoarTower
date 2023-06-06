package com.tlg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private String name = "Harmony";
    private Room location;
    private Room prevLocation;
    private List<Item> inventory = new ArrayList<>();
    private Map map;


    public Player(List<Room> rooms,List<Item> items) {
//        Set the location with the name Entrance
        for (Room room : rooms){
            if (room.getName().equals("Entrance")){
                setLocation(room);
                break;
            }
        }
        String handkerchief = "Handkerchief";
        String amulet = "Amulet";
        String sword = "Sword";
        for (Item item : items){
            if (item.getName().equals(handkerchief)){
                addItemToInventory(item);
            }
            if (item.getName().equals(amulet)){
                addItemToInventory(item);
            }
            if (item.getName().equals(sword)){
                addItemToInventory(item);
            }
        }
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room currentLocation) {
        this.location = currentLocation;
    }

    public Room getPrevLocation() {
        return prevLocation;
    }

    public void setPrevLocation(Room prevLocation) {
        this.prevLocation = prevLocation;
    }

    public List<Item> getInventory() {
        return inventory;
    }
    public void roomItemFromInventory(Item e){
        inventory.remove(e);
    }
    public void addItemToInventory(Item e){
        inventory.add(e);
    }
    public void removeItemFromInventory(Item e){
        inventory.remove(e);
    }
    public void removeItemFromInventory(String e){
        for (Item item : inventory){
            if (item.getName().equalsIgnoreCase(e)){
                inventory.remove(item);
                break;
            }
        }
    }

    public Boolean hasItem(String itemName){
        for (Item item : inventory){
            if (item.getName().equalsIgnoreCase(itemName)){
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void useAmulet() {
        if (prevLocation != null) {
            location = prevLocation;
        }
    }
}
