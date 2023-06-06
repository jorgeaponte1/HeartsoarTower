package com.tlg.view;

import com.tlg.model.Room;

import java.util.List;

public class DisplayEngine {
    // Split the screen in thirds:
//    1. Top third is the art display. ( or map )
//    2. Middle third is the text display.
//    3. Bottom third is the input display.
    private DisplayArt artDisplay;
    private DisplayText textDisplay;
    private DisplayInput displayInput;
    private static final String lineBreak = "------------------------------------------------------------------------------------------------------------------------";


    public static void printScreen(DisplayArt artDisplay, DisplayText textDisplay, DisplayInput inputDisplay, List<Room> rooms) {
        System.out.print("\033[H\033[2J");
        System.out.println(printMapAndArt(artDisplay, rooms));
        System.out.println(lineBreak);
        System.out.println(DisplayText.getDisplay());
        System.out.println(lineBreak);
        System.out.println(inputDisplay.getDisplay());
    }

    public static String printMapAndArt(DisplayArt artDisplay, List<Room> rooms) {
        String map = MapUI.getMap(rooms);
        String art = artDisplay.getDisplay();
        String[] mapLines = map.split("\n");
        String[] artLines = art.split("\n");
        String[] output = new String[mapLines.length];
        for (int i = 0; i < mapLines.length; i++) {
            if (artLines.length == 0) {
                output[i] = mapLines[i];
            } else {
                output[i] = mapLines[i] + " " + artLines[i];
            }
        }
        return String.join("\n", output);
    }

}