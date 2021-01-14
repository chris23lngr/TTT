package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerBecomeSpecEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerPlayerRespawn implements Listener {

    public ListenerPlayerRespawn() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerRespawnEvent event) {

        PlayerBecomeSpecEvent becomeSpecEvent = new PlayerBecomeSpecEvent(event.getPlayer(), false);
        Bukkit.getPluginManager().callEvent(becomeSpecEvent);


        new BukkitRunnable() {
            @Override
            public void run() {
                event.getPlayer().teleport(TTT.core.getSpawnManager().getSpecSpawn().getLocation());
            }
        }.runTaskLater(TTT.getInstance(), 15);

    }

}
