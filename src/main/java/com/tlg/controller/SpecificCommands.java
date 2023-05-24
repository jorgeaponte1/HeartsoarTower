package com.tlg.controller;

import com.tlg.model.Item;
import com.tlg.model.Player;
import com.tlg.model.Scene;


/**
 * Handles commands that need awareness of model state.
 */
class SpecificCommands {
    protected static void specificCommands(String[] instruct, Player player, Scene scene) {
//        Get item:
        if (instruct[0].equalsIgnoreCase("get")) {
//            Check to see if the item is in the room:
            for (Item item : scene.getSceneItems()) {
                if (item.getName().equalsIgnoreCase(instruct[1])) {
//                    Add to our inventory:
                    player.addItemToInventory(item);
                    System.out.println("You picked up the " + item.getName() + " and added to your inventory.");
//                    Remove from the scene:
                    scene.removeItem(item);
                    return;
                }
            }
            System.out.println("I cannot get that item.");
        } else if (instruct[0].equalsIgnoreCase("drop")) {
//            Check to see if the item is in the inventory:
            for (Item item : player.getInventory()) {
                if (item.getName().equalsIgnoreCase(instruct[1])) {
                    player.removeItemFromInventory(item);
                    System.out.println("You dropped the " + item.getName() + ".");
                    return;
                }
            }
        }
    }

}