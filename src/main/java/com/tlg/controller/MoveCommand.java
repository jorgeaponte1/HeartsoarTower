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
public static boolean moveCommands(String[] instruct, Player player, Scene scene, List<Room> rooms) {
        boolean actionTaken = false;
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
                System.out.println("You have entered the " + player.getLocation().getName() + ".");
                return true;
            }
        }
    }
        return actionTaken;
}
}