package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class ListenerPluginDisable implements Listener {

    public ListenerPluginDisable() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PluginDisableEvent event) {

        TTT.core.getPlayerManager().getDeadBodies().forEach(body -> body.despawn());

        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§cDer Server restartet jetzt."));

    }

}
