package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.MapSetEvent;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class ListenerMapSet implements Listener {

    public ListenerMapSet() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final MapSetEvent event) {

        Map map = event.getMap();

        ArrayList<Spawn> spawns = Data.spawnWrapper.getSpawnsFor(map);

        Data.mapManager.getMapToPlay().setSpawns(spawns);

        Bukkit.broadcastMessage(Data.getPrefix() + "§7Es wird gespielt auf: §a" + map);

        Bukkit.getOnlinePlayers().forEach(player -> Data.sbManager.updateLobbySB(player));

    }

}
