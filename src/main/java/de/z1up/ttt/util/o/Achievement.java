package de.z1up.ttt.util.o;

import org.bukkit.event.Event;

public class Achievement {

    int id;
    String name;
    String description;

    public Achievement(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
