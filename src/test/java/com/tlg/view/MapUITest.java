package com.tlg.view;

import com.tlg.controller.HeartsoarTower;
import com.tlg.model.Factory;
import com.tlg.model.Room;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapUITest {
    private MapUI mapUI;
    private Factory factory;


    @Test
    public void testGetMap_withAllTrueDiscovery() {
        List<Room> rooms = new ArrayList<>();

        // Create Room objects and set their discovered status
        for (int i = 0; i < 10; i++) {
            Room room = new Room();
            room.setDiscovered(false);
            rooms.add(room);
        }

        // Set the discovered status for specific rooms
        rooms.get(9).setDiscovered(true);
        rooms.get(8).setDiscovered(true);
        rooms.get(7).setDiscovered(true);
        rooms.get(6).setDiscovered(true);
        rooms.get(5).setDiscovered(true);
        rooms.get(4).setDiscovered(true);
        rooms.get(3).setDiscovered(true);
        rooms.get(1).setDiscovered(true);
        rooms.get(2).setDiscovered(true);

        // Invoke the method under test
        String actualMap = MapUI.getMap(rooms);

        // Define the expected map string based on the room discoveries
        String expectedMap =
                "+--------------------------------+\n" +
                        "|          [       }}}           |\n" +
                        "|        [           }}          |\n" +
                        "|       [  Bedroom ++  }         |\n" +
                        "|       [       +--+|  +------+  |\n" +
                        "|       [-------+---+--+------+  |\n" +
                        "|      ++          __  |         |\n" +
                        "|      ||         |<>| |         |\n" +
                        "|      ||Conservatory  |         |\n" +
                        "|      ++----+   +-----+         |\n" +
                        "|            |   |               |\n" +
                        "|         +--+   +----+          |\n" +
                        "|         |  Ballroom |     +-+-++\n" +
                        "|         +-----------++----+ | ||\n" +
                        "|         |            | Vamp   ||\n" +
                        "|         |           ++--------++\n" +
                        "|         |  Parlor   |          |\n" +
                        "|         |           |          |\n" +
                        "|         +-----------+          |\n" +
                        "|         |           |          |\n" +
                        "|         |   Den     |          |\n" +
                        "|         |           |          |\n" +
                        "|         +-----+-----+          |\n" +
                        "|         |     |     |          |\n" +
                        "|         |  X  |     |          |\n" +
                        "|         |  X  |     |          |\n" +
                        "|         |     |     |          |\n" +
                        "| +-------+   Foyer   +--------+ |\n" +
                        "| |  Entrance           Kitchen| |\n" +
                        "+-+----------------------------+-+\n";

        // Assert the expected map string matches the actual map string
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void testGetMap_withSomeFalseDiscovery() {
        List<Room> rooms = new ArrayList<>();

        // Create Room objects and set their discovered status
        for (int i = 0; i < 10; i++) {
            Room room = new Room();
            room.setDiscovered(false);
            rooms.add(room);
        }

        // Set the discovered status for specific rooms
        rooms.get(9).setDiscovered(false);
        rooms.get(8).setDiscovered(false);
        rooms.get(7).setDiscovered(true);
        rooms.get(6).setDiscovered(true);
        rooms.get(5).setDiscovered(true);
        rooms.get(4).setDiscovered(true);
        rooms.get(3).setDiscovered(true);
        rooms.get(1).setDiscovered(true);
        rooms.get(2).setDiscovered(true);

        // Invoke the method under test
        String actualMap = MapUI.getMap(rooms);

        // Define the expected map string based on the room discoveries
        String expectedMap =
                "+--------------------------------+\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|                                |\n" +
                        "|         +--+   +----+          |\n" +
                        "|         |  Ballroom |     +-+-++\n" +
                        "|         +-----------++----+ | ||\n" +
                        "|         |            | Vamp   ||\n" +
                        "|         |           ++--------++\n" +
                        "|         |  Parlor   |          |\n" +
                        "|         |           |          |\n" +
                        "|         +-----------+          |\n" +
                        "|         |           |          |\n" +
                        "|         |   Den     |          |\n" +
                        "|         |           |          |\n" +
                        "|         +-----+-----+          |\n" +
                        "|         |     |     |          |\n" +
                        "|         |  X  |     |          |\n" +
                        "|         |  X  |     |          |\n" +
                        "|         |     |     |          |\n" +
                        "| +-------+   Foyer   +--------+ |\n" +
                        "| |  Entrance           Kitchen| |\n" +
                        "+-+----------------------------+-+\n";

        // Assert the expected map string matches the actual map string
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void testGetFullMap() {
        // Expected full map string
        String expectedFullMap =
                "+--------------------------------+\n" +
                        "|          [       }}}           |\n" +
                        "|        [           }}          |\n" +
                        "|       [  Bedroom ++  }         |\n" +
                        "|       [       +--+|  +------+  |\n" +
                        "|       [-------+---+--+------+  |\n" +
                        "|      ++          __  |         |\n" +
                        "|      ||         |<>| |         |\n" +
                        "|      ||Conservatory  |         |\n" +
                        "|      ++----+   +-----+         |\n" +
                        "|            |   |               |\n" +
                        "|         +--+   +----+          |\n" +
                        "|         |  Ballroom |     +-+-++\n" +
                        "|         +-----------++----+ | ||\n" +
                        "|         |            | Vamp   ||\n" +
                        "|         |           ++--------++\n" +
                        "|         |  Parlor   |          |\n" +
                        "|         |           |          |\n" +
                        "|         +-----------+          |\n" +
                        "|         |           |          |\n" +
                        "|         |   Den     |          |\n" +
                        "|         |           |          |\n" +
                        "|         +-----+-----+          |\n" +
                        "|         |     |     |          |\n" +
                        "|         |  X  |     |          |\n" +
                        "|         |  X  |     |          |\n" +
                        "|         |     |     |          |\n" +
                        "| +-------+   Foyer   +--------+ |\n" +
                        "| |  Entrance           Kitchen| |\n" +
                        "+-+----------------------------+-+\n";

        // Invoke the method under test
        String actualFullMap = MapUI.getFullMap();

        // Assert the expected full map string matches the actual full map string
        assertEquals(expectedFullMap, actualFullMap);
    }
}