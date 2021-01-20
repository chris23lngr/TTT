package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ListenerPlayerRespawn implements Listener {

    public ListenerPlayerRespawn() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerRespawnEvent event) {


    }

}
