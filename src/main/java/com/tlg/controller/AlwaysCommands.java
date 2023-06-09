package com.tlg.controller;

import com.tlg.model.Item;
import com.tlg.model.Player;
import com.tlg.model.Room;
import com.tlg.model.Scene;
import com.tlg.view.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;


/**
 * AlwaysCommands are commands that are available to the player regardless of the location
 */
public class AlwaysCommands {
    protected static Boolean alwaysAvailableCommands(String[] instruct, Player player, Scene scene, List<Room> rooms, DisplayEngine displayEngine, DisplayArt art, DisplayText text, DisplayInput inputter, MusicPlayer musicPlayer) {
//            Functions that we need REGARDLESS of what room we are in or our inventory state:
        if (instruct[0] == null && instruct[1] == null) {
            text.setDisplay("Invalid Command.");
            DisplayEngine.printScreen(art, text, inputter, rooms);
            return true;
        }
        if (instruct[0] != null) {
            if (instruct[0].equalsIgnoreCase("quit")) {
                text.setDisplay("You want to leave us. So soon?");
                gameOver();
                player.setGameOver(true);
                return true;
            }
            else if (instruct[0].equalsIgnoreCase("look")) {
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
        // TODO This command is not really called. Have to check if we can still use this. Add inventory to noun if so.
        if (instruct[1] != null) {
            if (instruct[1].equalsIgnoreCase("inventory")) {
                StringBuilder inventory = new StringBuilder();
                for (Item item : player.getInventory()) {
                    inventory.append(item.getName()).append(", ");
                }
                text.setDisplay("You have the following items in your inventory:" + inventory);
                DisplayEngine.printScreen(art, text, inputter, rooms);
            }
        }
        return false;
    }

    public static void gameOver() {
        String path = "/Ascii_art/GameOver.txt";
        showPrompt(path);
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
            // TODO Here we would need to instead showcase an image of the sword or whatever
            String graphic = foundItem.getGraphic();
            DisplayEngine.printScreen(art, text, inputter, rooms);
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

    public static void musicSettings(MusicPlayer musicPlayer) {
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

    private static void showPrompt(String path) {
        System.out.println("\033[H\033[2J");
        System.out.flush();
        try (InputStream is = AlwaysCommands.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + path);
            }
            System.out.println(new String(is.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}