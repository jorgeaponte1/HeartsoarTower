package com.tlg.controller;

import com.tlg.model.*;
import com.tlg.view.DisplayArt;
import com.tlg.view.DisplayEngine;
import com.tlg.view.DisplayInput;
import com.tlg.view.DisplayText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CombatEngineTest {
    private CombatEngine combatEngine;
    private Player player;
    private Scene scene;
    private DisplayArt art;
    private DisplayText text;
    private DisplayInput inputter;
    private DisplayEngine displayEngine;
    private List<Room> rooms;
    private List<Item> items;
    private List<Monster> monsters;
    private SceneBuilder scenebuilder;
    private HeartsoarTower heartsoarTower;

    @BeforeEach
    void setUp() throws IOException {
        heartsoarTower = new HeartsoarTower();
        heartsoarTower.factory = new Factory();
        combatEngine = new CombatEngine(heartsoarTower);
    }

    @Test
    void testCombatCommands_ValidCommand_ReturnsTrue() throws IOException {
        // Create test data for the method parameters
        String[] instruct = { "use", "amulet" };

        // Call the method under test
        boolean result = combatEngine.combatCommands(instruct, player, scene, art, text, inputter, displayEngine, rooms, items);

        // Assert the result
        assertTrue(result);
    }

    @Test
    void testCombatCommands_InvalidCommand_ReturnsTrue() {
        // Set up test scenario
        String[] instruct = { null, null };

        // Invoke combatCommands and assert the result
        boolean result = combatEngine.combatCommands(instruct, player, scene, art, text, inputter, displayEngine, rooms, items);
        assertTrue(result);
        assertEquals("Invalid Command.", text.getDisplay());
    }

    @Test
    void testCombatCommands_MissingMonster_ReturnsFalse() {
        // Set up test scenario
        String[] instruct = { "use", "sword" };

        // Invoke combatCommands and assert the result
        boolean result = combatEngine.combatCommands(instruct, player, scene, art, text, inputter, displayEngine, rooms, items);
        assertFalse(result);
    }
}
