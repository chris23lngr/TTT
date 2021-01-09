package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPlayerMove implements Listener {

    public ListenerPlayerMove() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerMoveEvent event) {

        if(TTT.core.getTimerManager().getPreSavePhaseTimer().isActive()) {

            Location from = event.getFrom();

            Location tp = new Location(from.getWorld(), from.getX(), from.getY(), from.getZ());
            event.getPlayer().teleport(tp);

        }

    }

}
