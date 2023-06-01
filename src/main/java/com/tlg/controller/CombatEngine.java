package com.tlg.controller;

import com.tlg.model.*;
import com.tlg.view.DisplayArt;
import com.tlg.view.DisplayEngine;
import com.tlg.view.DisplayInput;
import com.tlg.view.DisplayText;

import java.util.List;
import java.util.Scanner;

class CombatEngine {

    public static boolean combatCommands(String[] instruct, Player player, Scene scene, DisplayArt art, DisplayText text, DisplayInput inputter, DisplayEngine displayEngine, List<Room> rooms, List<Item> items) {
        boolean actionTaken = false;
        if (instruct[0] == null && instruct[1] == null) {
            text.setDisplay("Invalid Command.");
            displayEngine.printScreen(art, text, inputter, rooms);
            return true;
        }
//        Verify there's even a monster in the room:
        if (scene.getSceneMonsters(0) == null) return actionTaken;
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
            if (instruct[0].equalsIgnoreCase("use")){
                boolean hasItem = player.hasItem(instruct[1]);
                if (hasItem){
                    player.removeItemFromInventory(instruct[1]);
                } else {
                    text.setDisplay("You don't have that item.");
                    displayEngine.printScreen(art, text, inputter, rooms);
                    return true;
                }
            }
            successes.remove(0);
            actionTaken = true;
            if (successes.size() == 0) {
                String addItem = monster.deleteItem();
                if (addItem != null) {
                    for (Item item : items) {
                        if (item.getName().equalsIgnoreCase(addItem)) {
                            scene.addItem(item);
                        }
                    }
                }
                scene.defeatMonster(scene.getSceneMonsters(0), text, inputter, rooms);
//                Kill the monster by adding an extra white line 30 times until the monster has dissapeared:

            }
            if (instruct[0].equalsIgnoreCase("get") && instruct[1].equalsIgnoreCase("key")) {
                for (Item item : scene.getSceneItems()) {
                    if (item.getName().equalsIgnoreCase(instruct[1])) {
                        player.addItemToInventory(item);
                        scene.removeItem(item);
                        break;
                    }
                }
            }
            text.setDisplay(monster.progressDescription());
        }
        else if (instruct[0].equalsIgnoreCase("use") && instruct[1].equalsIgnoreCase("amulet")) {
            return false;
        }
        else if(!instruct[0].equalsIgnoreCase("look")){
            if (failures.contains(instruct[0]) || failures.contains(instruct[1])){
                actionTaken = true;
                text.setDisplay(monster.getSceneFailed());
                // Game Over Screen and a prompt if they would like to use their amulet.
//            TODO: RETURN TO SAVE POINT
                System.out.println("Press enter to continue...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                player.useAmulet();
            }
        }


        if (actionTaken) displayEngine.printScreen(art, text, inputter, rooms);
        return actionTaken;
    }
}