package com.tlg.view;

import com.tlg.model.Monster;
import com.tlg.model.Room;

import java.util.List;

public class DisplayArt extends Display {
    private final static int MAX_LINES = 30;
    private String display;
    private String prevDisplay;

    public DisplayArt() {
        super(MAX_LINES);
    }

    public void displayMonster(Monster monster) {
        setDisplay(monster.getArt());
    }
    public void defeatMonster(Monster monster, DisplayText text, DisplayInput inputter, List<Room> rooms) {
        StringBuilder sb = new StringBuilder();
        sb.append(monster.getArt());
        for (int i = 0; i < MAX_LINES - 1; i++){
            sb.insert(0, "\n");
            setDisplay(sb.toString());
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DisplayEngine.printScreen(this, text, inputter, rooms );
        }
        monster.setArt(this);
    }

    public void setDisplay(String display) {
        this.display = super.trimDisplay(display, MAX_LINES);
    }

    public String getDisplay() {
        return display;
    }

    public String getPrevDisplay() {
        return prevDisplay;
    }

    public void setPrevDisplay(String prevDisplay) {
        this.prevDisplay = prevDisplay;
    }

    public void swapToPrevArt() {
        if (prevDisplay != null) {
            display = prevDisplay;
        }
    }
}

