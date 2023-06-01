package com.tlg.controller;

import com.tlg.model.Player;
import com.tlg.model.Room;
import com.tlg.model.Scene;
import com.tlg.view.DisplayArt;
import com.tlg.view.DisplayEngine;
import com.tlg.view.DisplayInput;
import com.tlg.view.DisplayText;

import java.util.HashMap;
import java.util.List;

class MoveCommand {
    public static boolean moveCommands(String[] instruct, Player player, Scene scene, DisplayEngine displayEngine, DisplayArt art, DisplayText text, DisplayInput input, List<Room> rooms) {
        boolean actionTaken = false;
        //        If the player wants to use the magical amulet to go to the previous room:
        if (instruct[0].equalsIgnoreCase("use") && instruct[1].equalsIgnoreCase("amulet")) {
            if (player.getPrevLocation() == null) {
                player.setPrevLocation(player.getLocation());
                player.setLocation(player.getLocation());
                text.setDisplay("You use the amulet to redo the current room. " + player.getLocation().getDesc()[0]);
                return true;
            }
            for (Room room : rooms) {
                if (room.getName().equals(player.getPrevLocation().getName())) {
                    player.setPrevLocation(player.getLocation());
                    player.setLocation(room);
                    text.setDisplay("You use the amulet to return to the previous room. " + player.getLocation().getDesc()[0]);
                    return true;
                }
            }
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
}