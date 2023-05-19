package com.tlg.heartsoar;

import java.util.List;

public class Monster {
    private String name;
    private boolean isAlive = true;
    private List<String> failures; // Single words that result in failure
    private List<String> successes; // Noun + verb that result in success
    private List<String> desc; // Description of the monster after each success action
    private String sceneFailed; // Scene to go to if the player fails to kill the monster

    public Monster(String name, List<String> failures, List<String> successes, List<String> desc, String sceneFailed) {
        this.name = name;
        this.failures = failures;
        this.successes = successes;
        this.desc = desc;
        this.sceneFailed = sceneFailed;
    }


    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public List<String> getFailures() {
        return failures;
    }

    public List<String> getSuccesses() {
        return successes;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
