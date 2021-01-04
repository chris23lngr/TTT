package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.MapSetEvent;
import de.z1up.ttt.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerMapSet implements Listener {

    public ListenerMapSet() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final MapSetEvent event) {

        Bukkit.broadcastMessage(Data.getPrefix() + "§7Es wird gespielt auf: §a" + event.getMap().getName());

    }

}
