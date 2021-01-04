package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class ListenerServer implements Listener {

    public ListenerServer() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PluginDisableEvent event) {

        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§cDer Server restartet jetzt."));

    }

}
