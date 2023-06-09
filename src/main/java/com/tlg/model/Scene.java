package com.tlg.model;


import com.tlg.view.DisplayArt;
import com.tlg.view.DisplayInput;
import com.tlg.view.DisplayText;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private Room room;
    private String[] description = new String[3];
    private List<Item> sceneItems = new ArrayList<>();
    private List<Monster> sceneMonsters = new ArrayList<>();
    private DisplayArt displayArt = new DisplayArt();

    public Scene(SceneBuilder sceneBuilder, List<Room> rooms, List<Item> items, List<Monster> monsters) {
//        Associate the room with the correct room:
        for (Room r : rooms){
            if (r.getName().equalsIgnoreCase(sceneBuilder.getRoom())) {
                this.room = r;
            }
        }
        for (Item i : items) {
            for(String j : sceneBuilder.getItems()){
                if (i.getName().equalsIgnoreCase(j)) {
                    addItem(i);
                }
            }
        }
        for (Monster i : monsters) {
            for(String j : sceneBuilder.getMonsters()){
                if (i.getName().equalsIgnoreCase(j)) {
                    addMonster(i);
                }
            }
        }
        this.description = sceneBuilder.getDescription();
    }

    public Room getRoom(){
        return this.room;
    }
    public String getDescription(int i) {
        return description[i];
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
    public Monster getSceneMonsters(int i) {
        return sceneMonsters.get(i);
    }
    public List<Monster> getAllSceneMonsters() {

        return sceneMonsters;
    }
    public  void addMonster(Monster e){
        sceneMonsters.add(e);
    }
    public void defeatMonster(Monster e, DisplayText text, DisplayInput inputter, List<Room> rooms, Player player){
        displayArt.defeatMonster(e, text, inputter, rooms);
        if(e.getName().equalsIgnoreCase("Prince")) {
            //player.setGameOver(true);
            player.setWonGame(true);
            winGame();
        }
        sceneMonsters.remove(e);
    }

    private void winGame() {
//        Clear screen:
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(" ____      ____                   _                         \n" +
                    "|_  _|    |_  _|                 | |                        \n" +
                    "  \\ \\  /\\  / / .--.   _   _   __ | |                        \n" +
                    "   \\ \\/  \\/ // .'`\\ \\[ \\ [ \\ [  ]| |                        \n" +
                    "    \\  /\\  / | \\__. | \\ \\/\\ \\/ / |_|                        \n" +
                    " ____\\/__\\/   '.__.'   \\__/\\__/  (_)                        \n" +
                    "|_  _||_  _|                                                \n" +
                    "  \\ \\  / / .--.   __   _    _   _   __   .--.   _ .--.      \n" +
                    "   \\ \\/ // .'`\\ \\[  | | |  [ \\ [ \\ [  ]/ .'`\\ \\[ `.-. |     \n" +
                    "   _|  |_| \\__. | | \\_/ |,  \\ \\/\\ \\/ / | \\__. | | | | |  _  \n" +
                    "  |______|'.__.'  '.__.'_/   \\__/\\__/   '.__.' [___||__](_) \n");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.exit(0);
        }
}
