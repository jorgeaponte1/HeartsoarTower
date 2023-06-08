package com.tlg.controller;

import com.tlg.model.*;
import com.tlg.view.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.tlg.controller.AlwaysCommands.alwaysAvailableCommands;
import static com.tlg.controller.MoveCommand.moveCommands;
import static com.tlg.controller.SpecificCommands.specificCommands;

public class HeartsoarTower implements GameInputListener{
    public Factory factory = new Factory();
    private List<Room> rooms = factory.getRooms();
    private List<Item> items = factory.getItems();
    private List<Monster> monsters = factory.getMonsters();
    private TreeMap<String, ArrayList<String>> VERBS = factory.getVerbs();
    private TreeMap<String, ArrayList<String>> NOUNS = factory.getNouns();
    private TextParser textParser = new TextParser(VERBS, NOUNS);
    private Player player;
    Scene scene;
    private boolean isRunning;
    private List<Scene> scenes = factory.getScenes();
    private DisplayEngine displayEngine = new DisplayEngine();
    DisplayArt art = new DisplayArt();
    private DisplayInput inputter;
    DisplayText text = new DisplayText();
    private MusicPlayer musicPlayer;
    private String[] instruct;
    private BlockingQueue<String[]> instructQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<String> yesNoInstructQueue = new LinkedBlockingQueue<>();
    private CombatEngine combatEngine;
    private boolean justEntered;
    private Scene previousScene;
    private int amuletCharges = 3;
    private boolean gameOver = false;
    private boolean wonGame = false;


    public HeartsoarTower() throws IOException {
        this.player = new Player(rooms, items, amuletCharges, gameOver, wonGame);
        this.isRunning = true;
        this.musicPlayer = new MusicPlayer("Music/medievalrpg-music.wav");
        this.inputter = new DisplayInput(player);
        this.instruct = new String[]{"", ""};
        this.combatEngine = new CombatEngine(this);
    }

    public void gameLoop() {
        grabScene();
        launchGUI();
        justEntered = true;
    }

    public boolean processNonMovementCommand(String[] instruct) {
        boolean actionTaken = false;
        if (scene.getAllSceneMonsters().size() != 0) {
            actionTaken = combatEngine.combatCommands(instruct, player, scene, art, text, inputter, displayEngine, rooms, items);
        }
        if (!actionTaken) {
            actionTaken = alwaysAvailableCommands(instruct, player, scene, rooms, displayEngine, art, text, inputter, musicPlayer);
        }
        if (!actionTaken) {
            actionTaken = specificCommands(instruct, player, scene, displayEngine, art, text, inputter, rooms);
        }
        return actionTaken;
    }

    public void grabScene() {
        for (Scene scene : scenes) {
            if (scene.getRoom().equals(player.getLocation())) {
                this.previousScene = this.scene;
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
        DisplayEngine.printScreen(art, text, inputter, rooms);
    }

    @Override
    public void onInputReceived(String[] input) {
        //instructQueue.offer(textParser.validCombo(input));
        String[] inputAfterParser = textParser.validCombo(input);
        if(!processNonMovementCommand(inputAfterParser)) {
            if (moveCommands(inputAfterParser, player, scene, displayEngine, art, text, inputter, rooms)) {
                // Refactor grabScene to grab specific information to the GUI
                grabScene();
            }
            else {
                text.setDisplay("I do not know that command.  Please try again:    ");
            }
        }
        this.instruct = inputAfterParser;
    }

    public Scene getPreviousScene() {
        return this.previousScene;
    }

    public Player getPlayer() {
        return player;
    }

    private void launchGUI() {
        EventQueue.invokeLater(() -> {
            try {
                GuiBuild frame = new GuiBuild(HeartsoarTower.this, player, rooms, items, art);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        HeartsoarTower game = new HeartsoarTower();
        game.gameLoop();
    }
}

