package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.mysql.wrapper.WrapperPlayer;
import de.z1up.ttt.util.o.DeadBody;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager extends WrapperPlayer implements Manager {

    private ArrayList<Player> alives;
    private ArrayList<Player> specs;

    private HashMap<UUID, TTTPlayer> players;

    private ArrayList<DeadBody> deadBodies;

    public PlayerManager() {
        super(Core.sql);
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {

        players = new HashMap<>();
        deadBodies = new ArrayList<>();

        alives = new ArrayList<>();
        specs = new ArrayList<>();
    }

    public boolean isSpec(Player player) {
        return specs.contains(player);
    }

    public void addSpectator(Player player) {
        specs.add(player);
    }

    public ArrayList<Player> getSpecs() {
        return specs;
    }

    public ArrayList<Player> getAlives() {
        return alives;
    }

    public TTTPlayer getTTTPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public void registerPlayer(TTTPlayer player) {
        players.put(player.getUuid(), player);
    }

    public boolean existsPlayer(UUID uuid) {
        return players.containsKey(uuid);
    }

    public void updatePlayer(TTTPlayer tttPlayer) {
        players.remove(tttPlayer.getUuid());
        players.put(tttPlayer.getUuid(), tttPlayer);
    }

    public ArrayList<DeadBody> getDeadBodies() {
        return deadBodies;
    }

    public void addDeadBody(DeadBody deadBody) {
        deadBodies.add(deadBody);
    }

    public HashMap<UUID, TTTPlayer> getTTTPlayers() {
        return players;
    }

}


