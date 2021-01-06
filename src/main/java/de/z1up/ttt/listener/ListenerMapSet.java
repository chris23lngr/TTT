package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.MapSetEvent;
import de.z1up.ttt.core.Core;
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
        ArrayList<Spawn> spawns = Core.spawnManager.getSpawns(map.getId());

        map.setSpawns(spawns);
        Core.mapManager.setMapToPlay(map);

        Bukkit.broadcastMessage(Core.getPrefix() + "§7Es wird gespielt auf: §a" + map.getName());

        //Bukkit.getOnlinePlayers().forEach(player -> Data.sbManager.updateLobbySB(player));

    }

}
