package de.z1up.ttt.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BuildManager {

    private ArrayList<Player> buildPlayers;

    public BuildManager() {
        init();
    }

    void init() {
        buildPlayers = new ArrayList<>();
    }

    public boolean canBuild(Player player) {
        return buildPlayers.contains(player);
    }

    public void allowBuilding(Player player) {
        buildPlayers.add(player);
    }

    public void disallowBuilding(Player player) {
        buildPlayers.remove(player);
    }

}
