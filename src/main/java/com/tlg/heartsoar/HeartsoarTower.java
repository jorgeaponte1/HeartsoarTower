package com.tlg.heartsoar;

import com.tlg.language.TextParser;

import java.io.IOException;
import java.util.*;

class HeartsoarTower {
    Factory factory = new Factory();
    List<Room> rooms = factory.getRooms();
    List<Item> items = factory.getItems();
    List<Monster> monsters = factory.getMonsters();


    TreeMap<String, ArrayList<String>> VERBS = factory.getVerbs();
    TreeMap<String, ArrayList<String>> NOUNS = factory.getNouns();

    HeartsoarTower() throws IOException {

    }


//
////    TODO: Place in proper location once game loop established
//    // Take input from the user via the console:
//    Scanner scanner = new Scanner(System.in);
//    String input = scanner.nextLine();
//    String[] instruct = textParser.validCombo(input);

    void newGame() {
        Scanner inputScanner = new Scanner(System.in);
        String userInput;

        System.out.println("Welcome to Heartsoar Tower!");
        System.out.println("Please enter 'New Game' to start a new game:");
        userInput = inputScanner.nextLine();
        if ("New Game".equalsIgnoreCase(userInput)) {
            System.out.println("Starting a new game...");
            //playGame()
        } else {
            System.out.println("Invalid. Please enter 'New Game' to start the game.");
        }

        inputScanner.close();
    }

    public static void main(String[] args) throws IOException {
        HeartsoarTower heartsoarTower = new HeartsoarTower();
        heartsoarTower.newGame();
    }
}

