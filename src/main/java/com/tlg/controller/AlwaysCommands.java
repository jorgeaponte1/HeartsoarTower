package com.tlg.controller;

import com.tlg.model.Item;
import com.tlg.model.Player;
import com.tlg.model.Room;
import com.tlg.model.Scene;
import com.tlg.view.*;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/**
 * AlwaysCommands are commands that are available to the player regardless of the location
 */
class AlwaysCommands {
    protected static Boolean alwaysAvailableCommands(String[] instruct, Player player, Scene scene, List<Room> rooms, DisplayEngine displayEngine, DisplayArt art, DisplayText text, DisplayInput inputter, MusicPlayer musicPlayer) {
//            Functions that we need REGARDLESS of what room we are in or our inventory state:
        if (instruct[0] == null && instruct[1] == null) {
            text.setDisplay("Invalid Command.");
            displayEngine.printScreen(art, text, inputter, rooms);
            return true;
        }
//        If the player wants to use the magical amulet to go to the previous room:
        if (instruct[0].equalsIgnoreCase("use") && instruct[1].equalsIgnoreCase("amulet")){
            player.useAmulet();
            text.setDisplay("You use the amulet to return to the previous room. " + player.getLocation().getDesc()[0]);
            displayEngine.printScreen(art, text, inputter, rooms);
        }
        if (instruct[0] != null) {
            if (instruct[0].equalsIgnoreCase("quit")) {
                quitGame();
            } else if (instruct[0].equalsIgnoreCase("help")) {
                help();
                displayEngine.printScreen(art, text, inputter, rooms);
                return true;
            } else if (instruct[0].equalsIgnoreCase("look")) {
                if (instruct[1] == null || instruct[1].equalsIgnoreCase("around")) {
                    System.out.println("Looking around...");
                    lookAround(player);
                    return true;
                } else {

                lookAtItem(instruct[1], player, scene, displayEngine, art, text, inputter, rooms);
                return true;
                }
            } else if (instruct[0].equalsIgnoreCase("music")) {
                musicSettings(musicPlayer);
            }
        }
        if (instruct[1] != null) {
            if (instruct[1].equalsIgnoreCase("inventory")) {
                StringBuilder inventory = new StringBuilder();
                for (Item item : player.getInventory()) {
                    inventory.append(item.getName()).append(", ");
                }
                text.setDisplay("You have the following items in your inventory:" + inventory.toString());
                displayEngine.printScreen(art, text, inputter, rooms);
            }
        } else if (instruct[0].equalsIgnoreCase("look") && instruct[1].equalsIgnoreCase("sword")) {
//            lookAtSword();
        }


//        else {
//            System.out.println("Invalid Command. Please try another way.");
//        }
        return false;
    }


    private static void help() {
//      Clear the console screen:
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println(

                "    ██████████▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█▀▀█████████\n" +
                        "    █████████`Welcome to Heartsoar Tower █░░▒▐████████\n" +
                        "    █████████                           ▐░█░▒░████████\n" +
                        "    ████████▌ You can use the following: ▒▌▒▒▒████████\n" +
                        "    ████████▌                           █▒█▒▒▒████████\n" +
                        "    █████████   go <direction>          █▒▀░▒▐████████\n" +
                        "    █████████⌐    look <item>           ▐█████████████\n" +
                        "    █████████▌      look around          █████████████\n" +
                        "    ██████████       check inventory     ▐████████████\n" +
                        "    ███████████       talk <npc>          ████████████\n" +
                        "    ███████████µ        use <item>        ▐███████████\n" +
                        "    ████████████         drop <item>       ███████████\n" +
                        "    ████████████▌          music            ██████████\n" +
                        "    █████████████            quit           ▐█████████\n" +
                        "    █████████████▌                           █████████\n" +
                        "    ██████████████                           └████████\n" +
                        "    ██████████▀▀▀▀▀MMMMMMMMMMMMMMMMMMMMMMM█ß▄ ████████\n" +
                        "    ████████'                            ▐▌▒░▌▐███████\n" +
                        "    ███████▌                             █▒▒▒█▐███████\n" +
                        "    ███████▌  Press enter to continue    █▒▒▒█▐███████\n" +
                        "    ████████                             ▐▄▄▄▀▐███████\n" +
                        "    ████████▄                             █   ████████\n" +
                        "    █████████▀∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞4▀4█████████\n");
//        Press enter to continue
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private static void lookAtItem(String itemName, Player player, Scene scene, DisplayEngine displayEngine, DisplayArt art, DisplayText text, DisplayInput inputter, List<Room> rooms) {
        Item foundItem = null;
        for (Item item: scene.getSceneItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                foundItem = item;
                break;
            }
        }
        if (foundItem == null) {
            for (Item inventoryItem : player.getInventory()) {
                if (inventoryItem.getName().equalsIgnoreCase(itemName)) {
                    foundItem = inventoryItem;
                    break;
                }
            }
        }
        if (foundItem != null) {
            text.setDisplay(foundItem.getDescription());
            art.setDisplay(foundItem.getArt());
            displayEngine.printScreen(art, text, inputter, rooms);
        } else {
            System.out.println("You don't see that item here.");
        }
    }


    private static void lookAround(Player player) {
        Room currentRoom = player.getLocation();
        String[] roomDescriptionArray = currentRoom.getDesc();
        String roomDescription = String.join(" ", roomDescriptionArray);
        System.out.println(roomDescription);
    }

    private static void musicSettings(MusicPlayer musicPlayer) {
        System.out.println("=== Music Settings ===");
        System.out.println("1. Play music");
        System.out.println("2. Stop music");
        System.out.println("3. Adjust volume");
        System.out.println("Enter the number of your choice:");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                musicPlayer.play();
                break;
            case 2:
                musicPlayer.stop();
                break;
            case 3:
                System.out.println("Enter the volume (1-100):");
                int volumeInput = scanner.nextInt();
                if (volumeInput >= 1 && volumeInput <= 100) {
                    float volume = volumeInput / 100f;
                    musicPlayer.setVolume(volume);
                    System.out.println("Volume adjusted to " + volumeInput);
                    System.out.println("Type in a command:");
                } else {
                    System.out.println("Invalid volume input. Please enter a value between 1 and 100.");
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static boolean quitGame() {
        System.out.println("Would you like to quit the game? Y/N");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        while (!"Y".equalsIgnoreCase(userInput) && !"Yes".equalsIgnoreCase(userInput) &&
                !"N".equalsIgnoreCase(userInput) && !"No".equalsIgnoreCase(userInput)) {
            System.out.println("Invalid input. Please enter Y/Yes to confirm quitting, or N/No to continue playing.");
            userInput = scanner.nextLine();
        }
        if ("Y".equalsIgnoreCase(userInput) || "Yes".equalsIgnoreCase(userInput)) {
            System.out.println("Quitting the game. Goodbye!");
            System.exit(0);
        } else {
            System.out.println("Returning to the start..");
            return true;
        }
        return true;
    }

}