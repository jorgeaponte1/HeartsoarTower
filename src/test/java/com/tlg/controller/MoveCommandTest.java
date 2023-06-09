package com.tlg.controller;

import com.tlg.model.*;
import com.tlg.view.DisplayArt;
import com.tlg.view.DisplayEngine;
import com.tlg.view.DisplayInput;
import com.tlg.view.DisplayText;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.*;

public class MoveCommandTest {

    private Player player;
    private DisplayText text;
    private List<Room> rooms;
    private List<Item> items;
    private Factory factory;
    private HeartsoarTower heartsoarTower;
    private Scene scene;
    private DisplayEngine displayEngine;
    private DisplayArt art;
    private DisplayInput input;


    @BeforeEach
    public void setup() {
        try {
            heartsoarTower = new HeartsoarTower();
            factory = heartsoarTower.factory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        rooms = heartsoarTower.factory.getRooms();
        items = heartsoarTower.factory.getItems();
        player = heartsoarTower.getPlayer();
        text = heartsoarTower.text;
        scene = heartsoarTower.scene;
        displayEngine = new DisplayEngine();
        art = new DisplayArt();
        input = heartsoarTower.inputter;
    }

    /**
     * Tests if "moveCommands()" correctly handles using the amulet.
     * Player starts with 1 charge and a current room. After calling the method,
     * we expect the player to have no charges, no amulet in their inventory, and
     * the function to return true indicating the action was successful.
     */
    @Test
    public void testMoveCommandsUseAmulet() {
        player.setAmuletCharges(1);
        Room currentRoom = rooms.get(3);
        player.setLocation(currentRoom);

        boolean result = MoveCommand.moveCommands(new String[]{"use", "amulet"}, player, scene, displayEngine, art, text, input, rooms);

        Assertions.assertTrue(result);
        Assertions.assertEquals(0, player.getAmuletCharges());
        Assertions.assertFalse(player.getInventory().contains("amulet"));
    }

    /**
     * Tests if "useAmulet()" correctly handles the scenario when there's no previous room.
     * Player starts with 1 charge and in a certain room. After calling the method, we expect
     * the player to still be in the same room, have no charges, and the function to return true.
     */
    @Test
    public void testUseAmuletNoPreviousLocation() {
        player.setAmuletCharges(1);
        Room room = rooms.get(0);
        player.setLocation(room);

        boolean result = MoveCommand.useAmulet(player, rooms, text);
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, player.getAmuletCharges());
        Assertions.assertEquals(room, player.getLocation());
    }

    /**
     * Tests if "useAmulet()" correctly handles the scenario when there's a previous room.
     * Player starts with 1 charge and is moved from one room to another.
     * After calling the method, we expect the player to be in the previous room,
     * have no charges, and the function to return true.
     */
    @Test
    public void testUseAmuletWithPreviousLocation() {
        // Start off with 1 Charge
        player.setAmuletCharges(1);
        // Get a room
        Room prevRoom = rooms.get(0);
        // Set the player location to that room
        player.setLocation(prevRoom);
        // Get the next room
        Room currentRoom = rooms.get(1);

        player.setLocation(currentRoom);
        player.setPrevLocation(prevRoom);

        // Call the useAmulet()
        player.useAmulet();

        boolean result = MoveCommand.useAmulet(player, rooms, text);
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, player.getAmuletCharges());
        Assertions.assertEquals(prevRoom, player.getLocation());
    }

    /**
     * Tests if "useAmulet()" correctly handles the scenario when there are no amulet charges left.
     * Player starts with 0 charges, a current room and a previous room. After calling the method,
     * we expect the game to be over, the player to still have no charges, and the function to return true.
     */
    @Test
    public void testUseAmuletNoCharges() {
        player.setAmuletCharges(0);

        Room prevRoom = rooms.get(0);
        Room currentRoom = rooms.get(1);

        player.setLocation(currentRoom);
        player.setPrevLocation(prevRoom);

        player.useAmulet();

        boolean result = MoveCommand.useAmulet(player, rooms, text);
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, player.getAmuletCharges());
        Assertions.assertTrue(player.isGameOver());
    }
}
