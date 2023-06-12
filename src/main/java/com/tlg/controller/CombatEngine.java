package com.tlg.controller;

import com.tlg.model.*;
import com.tlg.view.DisplayArt;
import com.tlg.view.DisplayEngine;
import com.tlg.view.DisplayInput;
import com.tlg.view.DisplayText;

import java.util.List;

import static com.tlg.controller.AlwaysCommands.gameOver;

class CombatEngine {

    private HeartsoarTower heartsoarTower;

    public CombatEngine(HeartsoarTower heartsoarTower) {
        this.heartsoarTower = heartsoarTower;
    }

    public boolean combatCommands(String[] instruct, Player player, Scene scene, DisplayArt art, DisplayText text, DisplayInput inputter, DisplayEngine displayEngine, List<Room> rooms, List<Item> items) {
        boolean actionTaken = false;
        if (instruct[0] == null && instruct[1] == null) {
            if (scene.isHasFirstSuccess()) {
                text.setDisplay("Invalid Command.\n" + scene.getDescription(1));
            }
            if (scene.isHasSecondSuccess()) {
                text.setDisplay("Invalid Command.\n" + scene.getDescription(2));
            }
            DisplayEngine.printScreen(art, text, inputter, rooms);
            return true;
        }
        //String[] input = instruct;
//        Verify there's even a monster in the room:
        if (scene.getSceneMonsters(0) == null) {
            //noinspection ConstantConditions
            return actionTaken;
        }
//        Every monster has some acceptable commands for defeating:
        Monster monster = scene.getSceneMonsters(0);
        List<String[]> successes = monster.getSuccesses();
//        Every monster has some commands for losing:
        List<String> failures = monster.getFailures();
//        To defeat the monster we have to do every command IN ORDER:
        String successVerb = successes.get(0)[0];
        String successNoun = successes.get(0)[1];

        if (successVerb.equalsIgnoreCase(instruct[0]) && successNoun.equalsIgnoreCase(instruct[1])){
//            First determine if the player has the item:
            if (instruct[0].equalsIgnoreCase("use") && !instruct[1].equalsIgnoreCase("amulet")){
                boolean hasItem = player.hasItem(instruct[1]);
                if (hasItem){
                    player.removeItemFromInventory(instruct[1]);
                } else {
                    text.setDisplay("You don't have that item.");
                    DisplayEngine.printScreen(art, text, inputter, rooms);
                    return true;
                }
            }
            successes.remove(0);
            scene.setHasFirstSuccess(true);
            if (successes.size() == 1) {
                text.setDisplay(scene.getDescription(1));
            }
            else if (successes.size() == 0) {
                scene.setHasSecondSuccess(true);
                String addItem = monster.deleteItem();
                if (addItem != null) {
                    for (Item item : items) {
                        if (item.getName().equalsIgnoreCase(addItem)) {
                            scene.addItem(item);
                        }
                    }
                }
                text.setDisplay(scene.getDescription(2));
                scene.defeatMonster(scene.getSceneMonsters(0), text, inputter, rooms, player);
                player.getLocation().setMonsterDefeated(true);
//                Kill the monster by adding an extra white line 30 times until the monster has dissapeared:
            }
            actionTaken = true;
            if (instruct[0].equalsIgnoreCase("get") && instruct[1].equalsIgnoreCase("key")) {
                for (Item item : scene.getSceneItems()) {
                    if (item.getName().equalsIgnoreCase(instruct[1])) {
                        player.addItemToInventory(item);
                        scene.removeItem(item);
                        break;
                    }
                }
            }
        }
        else if (instruct[0].equalsIgnoreCase("use") && instruct[1].equalsIgnoreCase("amulet")) {
            return false;
        }
        else if(!instruct[0].equalsIgnoreCase("look")){
            if (failures.contains(instruct[0]) || failures.contains(instruct[1])){
                actionTaken = true;
                if (player.getPrevLocation() == null) {
                    int amuletCharges = player.getAmuletCharges();
                    if (amuletCharges > 0) {
                        amuletCharges--;
                        player.setAmuletCharges(amuletCharges);
                        player.setPrevLocation(player.getLocation());
                        player.setLocation(player.getLocation());
                        text.setDisplay(monster.getSceneFailed() + "\nYou use the amulet to redo the current room. \n" + player.getLocation().getDesc()[0]);
                        if (amuletCharges == 0) {
                            player.removeItemFromInventory("amulet");
                        }
                    }
                    else {
                        gameOver();
                        player.setGameOver(true);
                    }
                }
                else {
                    for (Room room : rooms) {
                        if (room.getName().equals(player.getPrevLocation().getName())) {
                            int amuletCharges = player.getAmuletCharges();
                            if (amuletCharges > 0) {
                                amuletCharges--;
                                player.setAmuletCharges(amuletCharges);
                                player.useAmulet();
                                if (amuletCharges == 0) {
                                    player.removeItemFromInventory("amulet");
                                }
                                if (heartsoarTower.getPreviousScene() != null) {
                                    scene = heartsoarTower.getPreviousScene();
                                }
                                heartsoarTower.grabScene();
                                heartsoarTower.text.setDisplay(monster.getSceneFailed() + "\nYou use the amulet to return to the previous room. \n" + player.getLocation().getDesc()[0]);
                                if (scene.getAllSceneMonsters() != null && scene.getAllSceneMonsters().size() != 0) {
                                    String monsterPicture = scene.getAllSceneMonsters().get(0).getArt();
                                    art.setDisplay(monsterPicture);
                                }
                                else if (scene.getAllSceneMonsters() != null && scene.getSceneItems().size() != 0) {
                                    art.setDisplay(scene.getSceneItems().get(0).getArt());
                                }
                                else {
                                    art.setDisplay("");
                                }
                            }
                            else {
                                gameOver();
                                player.setGameOver(true);
                            }
                        }
                    }
                }
            }
        }
        if (actionTaken) {
            DisplayEngine.printScreen(art, text, inputter, rooms);
        }
        return actionTaken;
    }
}