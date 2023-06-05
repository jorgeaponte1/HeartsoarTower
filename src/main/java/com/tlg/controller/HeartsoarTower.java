package com.tlg.controller;

import com.tlg.model.*;
import com.tlg.view.*;
import com.tlg.view.GuiBuild;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.tlg.controller.AlwaysCommands.alwaysAvailableCommands;
//import static com.tlg.controller.CombatEngine.combatCommands;
import static com.tlg.controller.MoveCommand.moveCommands;
import static com.tlg.controller.NewGame.newGame;
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
    private Scene scene;
    private boolean isRunning;
    private List<Scene> scenes = factory.getScenes();
    private DisplayEngine displayEngine = new DisplayEngine();
    private DisplayArt art = new DisplayArt();
    private DisplayInput inputter;
    private DisplayText text = new DisplayText();
    private MusicPlayer musicPlayer;
    private String[] instruct;
    private BlockingQueue<String[]> instructQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<String> yesNoInstructQueue = new LinkedBlockingQueue<>();
    private CombatEngine combatEngine;
    private boolean justEntered;
    private GameInputListener gameInputListener;


    HeartsoarTower() throws IOException {
        this.player = new Player(rooms, items);
        this.isRunning = true;
        this.musicPlayer = new MusicPlayer("Music/medievalrpg-music.wav");
        this.inputter = new DisplayInput(player);
        this.instruct = new String[]{"", ""};
        this.combatEngine = new CombatEngine(this);
    }

    void gameLoop() {
        grabScene();
        launchGUI();
        //musicPlayer.play();
//        TitleScreen.displayTitleScreen();
//        newGame();
        justEntered = true;
        //while (isRunning) {
//            Just entered a room:
//            if (justEntered) {
//                grabScene();
//            }
//            justEntered = false;
//            try {
//                instruct = instructQueue.take();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            boolean actionTaken = false;
//            actionTaken = processNonMovementCommand(instruct);
//            if (!actionTaken) {
//                actionTaken = moveCommands(instruct, player, scene, displayEngine, art, text, inputter, rooms);
//                if (actionTaken) {
//                    justEntered = true;
//                }
//            }
//            if (!actionTaken) {
//                text.setDisplay("I do not know that command.  Please try again:    ");
//            }
            //instruct = new String[]{"", ""};
        //}
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

        if(!processNonMovementCommand(textParser.validCombo(input))) {
            if (moveCommands(instruct, player, scene, displayEngine, art, text, inputter, rooms)) {
                // Refactor grabScene to grab specific information to the GUI
                grabScene();
            }
            else {
                text.setDisplay("I do not know that command.  Please try again:    ");
            }
        }
        this.instruct = input;
    }

    // TODO: Continue working on this method.
    @Override
    public void onYesNoInputReceived(String input) {
        yesNoInstructQueue.offer(input);
    }

    private void launchGUI() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiBuild frame = new GuiBuild(HeartsoarTower.this);
                //frame.displayGuiMap(rooms);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        HeartsoarTower game = new HeartsoarTower();
        game.gameLoop();
    }
}

