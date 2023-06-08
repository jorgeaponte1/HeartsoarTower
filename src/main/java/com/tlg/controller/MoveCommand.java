package com.tlg.controller;

import com.tlg.model.Monster;
import com.tlg.model.Player;
import com.tlg.model.Room;
import com.tlg.model.Scene;
import com.tlg.view.DisplayArt;
import com.tlg.view.DisplayEngine;
import com.tlg.view.DisplayInput;
import com.tlg.view.DisplayText;

import java.util.HashMap;
import java.util.List;

import static com.tlg.controller.AlwaysCommands.gameOver;

class MoveCommand {
    public static boolean moveCommands(String[] instruct, Player player, Scene scene, DisplayEngine displayEngine, DisplayArt art, DisplayText text, DisplayInput input, List<Room> rooms) {
        boolean actionTaken = false;
//        If the player wants to use the magical amulet to go to the previous room:
        if (instruct[0].equalsIgnoreCase("use") && instruct[1].equalsIgnoreCase("amulet")) {
            actionTaken = useAmulet(player, rooms, text);
            if (player.getAmuletCharges() == 0) {
                player.removeItemFromInventory(instruct[1]);
            }
            return actionTaken;
        }
        if (instruct[0].equalsIgnoreCase("go")) {
            HashMap<String, String> acceptableDirections = player.getLocation().getNeighborRooms();
            if (!acceptableDirections.containsKey(instruct[1])) {
                System.out.println("You cannot go that way.");
                return true;
            }

            player.setPrevLocation(player.getLocation());

            Room nextRoom;
            String direction = instruct[1];
            String roomDir = player.getLocation().getNeighborRooms().get(direction);
//            TODO: Rework Rooms and Scene to transfer descriptions to scene
            for (Room room : rooms) {
                if (room.getName().equals(roomDir)) {
                    nextRoom = room;
                    player.setLocation(nextRoom);
                    if (!room.isDiscovered()) {
                        room.setDiscovered(true);
                    }
                    return true;
                }
            }
        }
        return actionTaken;
    }

    public static boolean useAmulet(Player player, List<Room> rooms, DisplayText text) {
        if (player.getPrevLocation() == null) {
            int amuletCharges = player.getAmuletCharges();
            if (amuletCharges > 0) {
                amuletCharges--;
                player.setAmuletCharges(amuletCharges);
                player.setPrevLocation(player.getLocation());
                player.setLocation(player.getLocation());
                text.setDisplay("\nYou use the amulet to redo the current room. \n" + player.getLocation().getDesc()[0]);
            }
            else {
                gameOver();
                player.setGameOver(true);
            }
//            player.setPrevLocation(player.getLocation());
//            player.setLocation(player.getLocation());
            return true;
        }
        for (Room room : rooms) {
            if (room.getName().equals(player.getPrevLocation().getName())) {
                int amuletCharges = player.getAmuletCharges();
                if (amuletCharges > 0) {
                    amuletCharges--;
                    player.setAmuletCharges(amuletCharges);
                    player.useAmulet();
                }
//                player.setPrevLocation(player.getLocation());
//                player.setLocation(room);
                return true;
            }
        }
        return true;
    }
}