package com.tlg.model;


import java.util.ArrayList;
import java.util.List;

public class Scene {
    private Room room;
    private String[] description = new String[3];
    private List<Item> sceneItems = new ArrayList<>();
    private List<Monster> sceneMonsters = new ArrayList<>();

    Scene(SceneBuilder sceneBuilder, List<Room> rooms, List<Item> items, List<Monster> monsters) {
//        Associate the room with the correct room:
        for (Room r : rooms){
            if (r.getName().equalsIgnoreCase(sceneBuilder.getRoom()))    this.room = r;
        }
        for (Item i : items) {
            for(String j : sceneBuilder.getItems()){
                if (i.getName().equalsIgnoreCase(j)) addItem(i);
            }
        }
        for (Monster i : monsters) {
            for(String j : sceneBuilder.getMonsters()){
                if (i.getName().equalsIgnoreCase(j)) addMonster(i);
            }
        }
        this.description = sceneBuilder.getDescription();
    }

    public Room getRoom(){
        return this.room;
    }
    public String[] getDescription() {
        return description;
    }
    public List<Item> getSceneItems() {
        return sceneItems;
    }
    public void removeItem(Item e){
        sceneItems.remove(e);
    }
    public void addItem(Item e){
        sceneItems.add(e);
    }
    public List<Monster> getSceneMonsters() {
        return sceneMonsters;
    }
    public  void addMonster(Monster e){
        sceneMonsters.add(e);
    }
}
