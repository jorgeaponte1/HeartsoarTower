package com.tlg.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayText extends Display{
    private final static int MAX_LINES = 4;
    private final int MAX_WIDTH = 250;
    private static String display;
    private String prevDisplay;

    public DisplayText() {
        super(MAX_LINES);
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
//        First ensure that no line is longer than 150 characters long.  Or else split it after a word.
        StringBuilder sb = new StringBuilder();
        List<String> lines = new ArrayList<String>();
        List<String> words = new ArrayList<String>();
        lines = Arrays.asList(display.split("\r\n|\r|\n"));
        for (String line : lines) {
            if (line.length() > MAX_WIDTH) {
                words = Arrays.asList(line.split("\\s+"));
                int counter = 0;
                for (String word : words) {
                    if (counter + word.length() > MAX_WIDTH) {
                        sb.append("\n");
                        counter = 0;
                    }
                    sb.append(word);
                    sb.append(" ");
                    counter += word.length() + 1;
                }
                sb.append("\n");
            } else {
                sb.append(line);
                sb.append("\n");
            }
        }
        display = sb.toString();
        this.display = super.trimDisplay(display, MAX_LINES);
    }


    public String getPrevDisplay() {
        return prevDisplay;
    }

    public void setPrevDisplay(String prevDisplay) {
        this.prevDisplay = prevDisplay;
    }

    public void swapToPrevDisplay() {
        if (prevDisplay != null) {
            display = prevDisplay;
        }
    }
}