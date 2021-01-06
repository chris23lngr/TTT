package de.z1up.ttt.manager;

import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.mysql.wrapper.WrapperMap;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collection;

public class ManagerMap extends WrapperMap implements Manager {

    private ArrayList<Map> maps;
    private Map mapToPlay;

    public ManagerMap() {
        super(Data.sql);
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
        maps = getRandomMaps();
        mapToPlay = null;
    }

    public Collection<Map> getMaps() {
        return maps;
    }

    public void registerMap(Map map) {
        if(existsID(map.getId())) {
            Bukkit.getConsoleSender().sendMessage(Messages.ID_ALREADY_EXIST_EXC);
            return;
        }
        if(existsName(map.getName())) {
            Bukkit.getConsoleSender().sendMessage(Messages.NAME_ALREADY_EXIST_EXC);
            return;
        }
        insert(map);
    }

    public void removeMap(Map map) {
        if(!existsID(map.getId())) {
            Bukkit.getConsoleSender().sendMessage(Messages.ID_NOT_FOUND_EXC);
            return;
        }
        delete(map);
    }

    public void updateMap(Map map) {
        if(!existsID(map.getId())) {
            Bukkit.getConsoleSender().sendMessage(Messages.ID_NOT_FOUND_EXC);
            return;
        }
        update(map);
    }

    public Map getMap(String name) {
        if(existsName(name)) {
            Bukkit.getConsoleSender().sendMessage(Messages.NAME_NOT_FOUND_EXC);
            return null;
        }
        Map map = (Map) get(name);
        return getMap(map.getId());
    }

    public Map getMap(int id) {
        if(!existsID(id)) {
            Bukkit.getConsoleSender().sendMessage(Messages.ID_NOT_FOUND_EXC);
            return null;
        }
        return (Map) get(id);
    }

    public boolean isMapSet() {
        return mapToPlay != null;
    }

    public void setMapToPlay(Map mapToPlay) {
        this.mapToPlay = mapToPlay;
    }

    public Map getMapToPlay() {
        return mapToPlay;
    }

    public Map getVotedMap() {

        int mapVotesOne = maps.get(0).getVotes();
        int mapVotesTwo = maps.get(1).getVotes();
        int mapVotesThree = maps.get(2).getVotes();

        Map map = maps.get(0);

        if(mapVotesTwo > mapVotesOne) {
            map = maps.get(1);
        }

        if(mapVotesThree > mapVotesTwo) {
            map = maps.get(2);
        }

        return map;
    }

}
