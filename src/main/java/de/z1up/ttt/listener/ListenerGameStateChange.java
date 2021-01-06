package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.GameStateChangeEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
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

        if((from == GameManager.GameState.LOBBYPHASE) && (to == GameManager.GameState.SCHUTZPHASE)) {

            Iterator iterator = Bukkit.getOnlinePlayers().iterator();
            ArrayList<Player> players = new ArrayList<>();

            while (iterator.hasNext()) {
                Player player = (Player) iterator.next();
                if(!Core.playerManager.isSpec(player)) {
                    players.add(player);
                }
            }

            Map map = Core.mapManager.getMapToPlay();
            Bukkit.getConsoleSender().sendMessage("MAP ID: " + map.getId());
            ArrayList<Spawn> spawns = map.getSpawns();
            spawns.forEach(spawn -> System.out.println("SPAWN ID" + spawn.getId()));

            for(int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                Spawn spawn = spawns.get(i);
                player.teleport(spawn.getLocation());
                player.sendMessage("pictch" + spawn.getLocation().getPitch());
                player.sendMessage("yaw" + spawn.getLocation().getYaw());
            }
        }

    }

}
