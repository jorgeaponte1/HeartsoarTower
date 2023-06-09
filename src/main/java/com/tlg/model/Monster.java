package com.tlg.model;

import com.tlg.view.DisplayArt;

import java.util.List;
import java.util.Random;

public class Monster {
    private String name;
    private List<String> failures; // Single words that result in failure
    private List<String[]> successes; // Noun + verb that result in success
    private List<String> description; // Description of the monster after each success action
    private String sceneFailed; // Scene to go to if the player fails to kill the monster
    private List<String> dialogue;
    private String art;
    private String item;
    private String graphic;

    public Monster(String name, List<String[]> successes, List<String> failures, List<String> description,
                   String sceneFailed, List<String> dialogue, String art, String graphic, String item) {
        this.name = name;
        this.successes = successes;
        this.failures = failures;
        this.description = description;
        this.sceneFailed = sceneFailed;
        this.dialogue = dialogue;
        this.art = art;
        this.graphic = graphic;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public String talk() {
        Random random = new Random();
        int dialogueIndex = random.nextInt(dialogue.size());  // Randomly select a dialogue
        return dialogue.get(dialogueIndex);  // Return the selected dialogue
    }

    public List<String> getFailures() {
        return failures;
    }

    public List<String[]> getSuccesses() {
        return successes;
    }

    public String progressDescription(){
        return description.remove(0);
    }

    public String getSceneFailed() {
        return sceneFailed;
    }

    public String getArt() {
        return art;
    }

    public void setArt(DisplayArt art) {
        this.art = art.getDisplay();
    }

    public String getItem() {
        return item;
    }

    public void addItem(String item) {
        this.item = item;
    }

    public String deleteItem() {
        String result = item;
        item = null;
        return result;
    }

    public String getGraphic() {
        return graphic;
    }
}