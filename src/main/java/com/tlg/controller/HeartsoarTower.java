package com.tlg.controller;

import com.tlg.model.*;
import com.tlg.view.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import static com.tlg.controller.AlwaysCommands.alwaysAvailableCommands;
import static com.tlg.controller.CombatEngine.combatCommands;
import static com.tlg.controller.MoveCommand.moveCommands;
import static com.tlg.controller.NewGame.newGame;
import static com.tlg.controller.SpecificCommands.specificCommands;

class HeartsoarTower {
    private Factory factory = new Factory();
    private List<Room> rooms = factory.getRooms();
    private List<Item> items = factory.getItems();
    private List<Monster> monsters = factory.getMonsters();
    private TreeMap<String, ArrayList<String>> VERBS = factory.getVerbs();
    private TreeMap<String, ArrayList<String>> NOUNS = factory.getNouns();
    private TextParser textParser = new TextParser(VERBS, NOUNS);
    private Player player;
    private Scene scene;
    private boolean isRunning;
    private List<Scene> scenes = factory.getScenes();
    private DisplayEngine displayEngine = new DisplayEngine();
    private DisplayArt art = new DisplayArt();
    private DisplayInput inputter;
    private DisplayText text = new DisplayText();
    private MusicPlayer musicPlayer;


    HeartsoarTower() throws IOException {
        this.player = new Player(rooms, items);
        this.isRunning = true;
        this.musicPlayer = new MusicPlayer("Music/medievalrpg-music.wav");
        this.inputter = new DisplayInput(player);
    }

    void gameLoop() {
//        musicPlayer.play();
        TitleScreen.displayTitleScreen();
        newGame();
        Scanner scanner = new Scanner(System.in);
        boolean justEntered = true;
        while (isRunning) {
//            Just entered a room:
            if (justEntered) grabScene();
            justEntered = false;
            String input = scanner.nextLine();
            String[] instruct = textParser.validCombo(input);
            Boolean actionTaken = false;
            if (scene.getAllSceneMonsters().size() != 0)
                actionTaken = combatCommands(instruct, player, scene, art, text, inputter, displayEngine, rooms, items);
            if (!actionTaken) actionTaken = alwaysAvailableCommands(instruct, player, scene, rooms, displayEngine, art, text, inputter, musicPlayer);
            if (!actionTaken) actionTaken = specificCommands(instruct, player, scene, displayEngine, art, text, inputter, rooms);
            if (!actionTaken) {
                actionTaken = moveCommands(instruct, player, scene, displayEngine, art, text, inputter, rooms);
                if (actionTaken) {
                    justEntered = true;
                }
            }
            if (!actionTaken) {
                text.setDisplay("I do not know that command.  Please try again:    ");
            }
        }
    }

    private void grabScene() {
        for (Scene scene : scenes) {
            if (scene.getRoom().equals(player.getLocation())) {
                this.scene = scene;
                break;
            }
        }
//        Print the description based on if the monster is present (0), if an item is present(1), or if complete(3)
        if (scene.getAllSceneMonsters().size() != 0) {
            String monsterPicture = scene.getAllSceneMonsters().get(0).getArt();
            art.setDisplay(monsterPicture);
            text.setDisplay(scene.getDescription(0));
        }
        else if (scene.getSceneItems().size() != 0) {
            art.setDisplay(scene.getSceneItems().get(0).getArt());
            text.setDisplay(scene.getDescription(0));
        }
        else {
            art.setDisplay("");
            text.setDisplay(scene.getDescription(0));
        }
        displayEngine.printScreen(art, text, inputter, rooms);
    }

    public static void main(String[] args) throws IOException {
        HeartsoarTower game = new HeartsoarTower();
        game.gameLoop();
    }
}

