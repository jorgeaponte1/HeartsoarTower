package com.tlg.controller;

import com.tlg.model.Player;
import com.tlg.model.Room;
import com.tlg.model.Scene;
import com.tlg.view.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlwaysCommandsTest {
    private Player player;
    private Scene scene;
    private List<Room> rooms;
    private DisplayEngine displayEngine;
    private DisplayArt art;
    private DisplayText text;
    private DisplayInput inputter;
    private MusicPlayer musicPlayer;

    @BeforeEach
    void setUp() {
//        player = new Player();
//        scene = new Scene();
        rooms = new ArrayList<>();
        displayEngine = new DisplayEngine();
        art = new DisplayArt();
        text = new DisplayText();
//        inputter = new DisplayInput();
//        musicPlayer = new MusicPlayer();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAlwaysAvailableCommands_useAmulet() {
        String[] instruct = {"use", "amulet"};

        AlwaysCommands.alwaysAvailableCommands(instruct, player, scene, rooms, displayEngine, art, text, inputter, musicPlayer);

        // Assert that the player's location has changed
        assertNotNull(player.getLocation());
        assertNull(player.getPrevLocation());
    }

    @Test
    void testAlwaysAvailableCommands_quit() {
        String[] instruct = {"quit"};

        // Set up a mock System.exit() call
        SecurityManager securityManager = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                if (perm.getName().startsWith("exitVM")) {
                    throw new SecurityException("System.exit attempted and blocked");
                }
            }
        };
        System.setSecurityManager(securityManager);
        try {
            assertThrows(SecurityException.class, () -> AlwaysCommands.alwaysAvailableCommands(instruct, player, scene, rooms, displayEngine, art, text, inputter, musicPlayer));
        } finally {
            System.setSecurityManager(null);
        }
    }

    @Test
    void testAlwaysAvailableCommands_help() {
        String[] instruct = {"help"};

        AlwaysCommands.alwaysAvailableCommands(instruct, player, scene, rooms, displayEngine, art, text, inputter, musicPlayer);

        // Assert that the help message has been displayed
        assertNotNull(text.getDisplay());
    }

    @Test
    void testAlwaysAvailableCommands_lookAround() {
        String[] instruct = {"look", "around"};

        AlwaysCommands.alwaysAvailableCommands(instruct, player, scene, rooms, displayEngine, art, text, inputter, musicPlayer);

        // Assert that the room description has been printed
        assertNotNull(text.getDisplay());
    }

    @Test
    void testAlwaysAvailableCommands_musicSettings() {
        String[] instruct = {"music"};

        AlwaysCommands.alwaysAvailableCommands(instruct, player, scene, rooms, displayEngine, art, text, inputter, musicPlayer);

        // Assert that the music settings menu has been displayed
        assertNotNull(text.getDisplay());
    }

    @Test
    void testAlwaysAvailableCommands_invalidCommand() {
        String[] instruct = {"invalid", "command"};

        AlwaysCommands.alwaysAvailableCommands(instruct, player, scene, rooms, displayEngine, art, text, inputter, musicPlayer);

        // Assert that the invalid command message has been displayed
        assertNotNull(text.getDisplay());
    }
}
