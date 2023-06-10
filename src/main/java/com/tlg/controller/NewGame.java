package com.tlg.controller;

import java.util.Scanner;

public class NewGame {

    public static void newGame() {
        Scanner inputScanner = new Scanner(System.in);
        String userInput;

        System.out.println("Welcome to Heartsoar Tower!");
        while (true) {
            System.out.println("Please enter 'New Game' to start a new game:");
            userInput = inputScanner.nextLine();
            if ("New Game".equalsIgnoreCase(userInput)) {
                System.out.println("Starting a new game...");
                break;
            } else {
                System.out.println("Invalid. Please enter 'New Game' to start the game.");
            }
        }

        System.out.println("Welcome, Harmony, to the enchanted world of Terra Motus."+
        "\nStory:"+
        "\nThe King and Queen of Terra Motus are grief-stricken. An evil curse has sealed away their young Prince Timore in a tower at the edge of their kingdom."+
        "\nThe royalty have requested you, Harmony, to enter the tower and save their gentle son."+
        "\nArmed with only your trusted sword and an amulet wrapped in a handkerchief given by the queen, you approach the ominous tower, unsure of what lies within."+
        "\nObjective:"+
        "\nYour mission is to navigate through the tower, overcoming challenges along the way."+
        "\nTo win, you must free Prince Timore from the curse by reaching the top of the tower."+

        "\nAre you ready to begin your journey, Harmony? The fate of Prince Timore rests in your hands!");

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPress Enter to start your journey...");
        scanner.nextLine();
    }
}