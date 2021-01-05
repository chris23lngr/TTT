package de.z1up.ttt.manager;

import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Map;

import java.util.ArrayList;
import java.util.Collection;

public class MapManager implements Manager {

    private ArrayList<Map> maps;

    private Map mapToPlay;

    public MapManager() {
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
        maps = Data.mapWrapper.getRandomMaps();
        mapToPlay = null;
    }

    public Collection<Map> getMaps() {
        return maps;
    }

    public void registerMap(Map map) {
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
