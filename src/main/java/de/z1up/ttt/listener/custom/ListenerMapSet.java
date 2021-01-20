package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.MapSetEvent;
import de.z1up.ttt.util.Messages;
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
        ArrayList<Spawn> spawns = TTT.core.getSpawnManager().getSpawns(map.getId());

        map.setSpawns(spawns);
        TTT.core.getMapManager().setMapToPlay(map);

        Bukkit.broadcastMessage(Messages.PLAYING_ON + map.getName());

        Bukkit.getOnlinePlayers().forEach(target -> TTT.core.getScoreboardAPI().updateMapCounter(target));

    }

}
