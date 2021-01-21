package de.z1up.ttt.manager;

import de.z1up.ttt.core.Core;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.mysql.wrapper.WrapperLocation;
import org.bukkit.Location;

public class LocationManager extends WrapperLocation implements Manager {

    private final String NAME_LOBBY = "LOBBY_LOC";
    private final String NAME_HOLO = "HOLO_LOC";
    private final String NAME_STATS = "STATS_LOC";

    public LocationManager() {
        super(Core.sql);
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
    }

    public Location getLobbyLocation() {
        return get(NAME_LOBBY);
    }

    public void setLobbyLocation(Location location) {

        if(exists(NAME_LOBBY)) {
            update(NAME_LOBBY, location);
        } else {
            insert(NAME_LOBBY, location);
        }

    }

    public Location getHoloLocation() {
        return get(NAME_HOLO);
    }

    public void setHoloLocation(Location location) {

        if(exists(NAME_HOLO)) {
            update(NAME_HOLO, location);
        } else {
            insert(NAME_HOLO, location);
        }

    }

    public Location getStatsWandLocation(int id) {
        return get(NAME_STATS + "_" + id);
    }

    public void setStatsLocation(Location location, int id) {

        final String FINAL_TAG = NAME_STATS + "_" + id;

        if(exists(FINAL_TAG)) {
            update(FINAL_TAG, location);
        } else {
            insert(FINAL_TAG, location);
        }

    }
}
