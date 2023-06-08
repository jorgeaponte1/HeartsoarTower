package com.tlg.model;

public class Item {
    private String name;
    private String description;
    private String onUse;
    private boolean isStorable;
    private String art;
    private String graphic;


    public Item(String name, String description, boolean isStorable, String onUse, String art) {
        this.name = name;
        this.description = description;
        this.isStorable = isStorable;
        this.onUse = onUse;
        this.art = art;
    }

    public String use(Room room) {
        return onUse;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStorable() {
        return isStorable;
    }

    public String getArt() {
        return art;
    }

    public String getGraphic() {
        return graphic;
    }
}
