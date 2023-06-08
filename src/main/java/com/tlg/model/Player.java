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
    private int amuletCharges;
    private boolean gameOver;
    private boolean wonGame;


    public Player(List<Room> rooms, List<Item> items, int amuletCharges, boolean gameOver, boolean wonGame) {
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
        this.amuletCharges = amuletCharges;
        this.gameOver = gameOver;
        this.wonGame = wonGame;
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

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isWonGame() {
        return wonGame;
    }

    public void setWonGame(boolean wonGame) {
        this.wonGame = wonGame;
    }

    public int getAmuletCharges() {
        return amuletCharges;
    }

    public void setAmuletCharges(int amuletCharges) {
        this.amuletCharges = amuletCharges;
    }

    public void useAmulet() {
        if (prevLocation != null && getAmuletCharges() > 0) {
            location = prevLocation;
        }
    }
}
