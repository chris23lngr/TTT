package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.o.Map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapManager {

    private static List<Map> maps;

    public MapManager() {
        init();
    }

    void init() {
        maps = new ArrayList<>();
        loadMaps();
    }

    public void registerNewMap(final String name) {
        Map map = new Map(name);
        maps.add(map);
        setToConfig();
    }

    public boolean existsMap(final String mapName) {
        Iterator var = maps.iterator();
        while (var.hasNext()) {
            Map map = (Map) var.next();
            return map.getName().equals(mapName);
        }
        return false;
    }

    void loadMaps() {
        TTT.getInstance().getConfig().getStringList("Maps").forEach(name -> {
            Map map = new Map(name);
            maps.add(map);
        });
    }

    void setToConfig() {
        ArrayList<String> mapNames = new ArrayList<>();
        maps.forEach(map -> mapNames.add(map.getName()));
        TTT.getInstance().getConfig().set("Maps", mapNames);
        TTT.getInstance().saveConfig();
    }
}
