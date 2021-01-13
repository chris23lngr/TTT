package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.MapResetter;
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

        TTT.core.getMapResetter().stop();
        TTT.core.getMapResetter().reset();

        TTT.core.getPlayerManager().getDeadBodies().forEach(body -> body.despawn());

        //Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§cDer Server restartet jetzt."));

    }

}
