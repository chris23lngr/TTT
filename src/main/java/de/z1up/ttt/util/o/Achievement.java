package de.z1up.ttt.util.o;

import de.z1up.ttt.util.Data;

public class Achievement {

    int id;
    String name;
    String description;
    int achievedBy;

    public Achievement(int id, String name, String description, int achievedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.achievedBy = achievedBy;
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

    public int getAchievedBy() {
        return achievedBy;
    }

    public void update() {
        Data.achievementWrapper.updateAchievement(this);
    }
}
