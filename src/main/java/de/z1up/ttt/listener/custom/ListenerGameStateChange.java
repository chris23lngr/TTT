package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.GameStateChangeEvent;
import de.z1up.ttt.event.PlayerTeamSetEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.util.UserAPI;
import de.z1up.ttt.util.o.DBPlayer;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Iterator;

public class ListenerGameStateChange implements Listener {

    public ListenerGameStateChange() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final GameStateChangeEvent event) {

        GameManager.GameState from = event.getFrom();
        GameManager.GameState to = event.getTo();

        if((from == GameManager.GameState.LOBBYPHASE) && (to == GameManager.GameState.PRE_SAVEPHASE)) {

            // teleport all the players who aren't
            // specs to a Spawnpoint
            Iterator iterator = Bukkit.getOnlinePlayers().iterator();
            ArrayList<Player> players = new ArrayList<>();

            while (iterator.hasNext()) {
                Player player = (Player) iterator.next();
                if(!TTT.core.getPlayerManager().isSpec(player)) {
                    players.add(player);
                }
            }

            Map map = TTT.core.getMapManager().getMapToPlay();
            ArrayList<Spawn> spawns = map.getSpawns();

            for(int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                Spawn spawn = spawns.get(i);
                player.teleport(spawn.getLocation());
                UserAPI.resetPlayerAsync(player);
            }

            // Start the PreSavephase timer
            TTT.core.getTimerManager().getPreSavePhaseTimer().runAsync();

            return;
        }

        if((from == GameManager.GameState.PRE_SAVEPHASE) && (to == GameManager.GameState.SAVEPHASE)) {

            TTT.core.getTimerManager().getSavePhaseTimer().runAsync();

            return;
        }

        if((from == GameManager.GameState.SAVEPHASE) && (to == GameManager.GameState.INGAME)) {

            TTT.core.getTeamManager().calcTeamSizes();

            Iterator players = Bukkit.getOnlinePlayers().iterator();

            while (players.hasNext()) {

                Player player = (Player) players.next();

                if(!TTT.core.getPlayerManager().isSpec(player)) {

                    TTTPlayer tttPlayer;

                    if(!TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {
                        DBPlayer dbPlayer = (DBPlayer) TTT.core.getPlayerManager().get(player);
                        tttPlayer = new TTTPlayer(dbPlayer, null, 0, true);
                    } else {
                        tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(player);
                    }

                    if (!tttPlayer.hasTeam()) {

                        TTT.core.getTeamManager().selectTeam(tttPlayer);
                        TTT.core.getPlayerManager().updatePlayer(tttPlayer);

                    }

                    PlayerTeamSetEvent teamSetEvent = new PlayerTeamSetEvent(tttPlayer, tttPlayer.getTeam(), false);
                    Bukkit.getPluginManager().callEvent(teamSetEvent);

                }

            }

            TTT.core.getTimerManager().getGameTimer().runAsync();

            return;
        }

    }

}
