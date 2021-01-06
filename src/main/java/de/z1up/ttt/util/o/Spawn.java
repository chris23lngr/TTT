package de.z1up.ttt.util.o;

import org.bukkit.Location;

public class Spawn extends Object {

    private int id;
    private Location location;
    private Map map;

    public Spawn(int id, Location location, Map map) {
        this.id = id;
        this.location = location;
        this.map = map;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

}
