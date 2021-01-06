package de.z1up.ttt.manager;

import de.z1up.ttt.core.Core;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.util.Messages;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BuildManager implements Manager {

    private ArrayList<Player> buildPlayers;

    public BuildManager() {
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
        buildPlayers = new ArrayList<>();
    }

    public boolean canBuild(Player player) {
        return buildPlayers.contains(player);
    }

    public void allowBuilding(Player player) {
        buildPlayers.add(player);
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(Messages.BUILD_ACTIVATED);
    }

    public void disallowBuilding(Player player) {
        buildPlayers.remove(player);
        player.sendMessage(Messages.BUILD_DEACTIVATED);
        player.setGameMode(GameMode.SURVIVAL);
    }

}
