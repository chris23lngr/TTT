package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.CountdownTimeChangeEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.scheduler.TTTRunnable;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerCountdownTimeChange implements Listener {

    public ListenerCountdownTimeChange() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final CountdownTimeChangeEvent event) {

        TTTRunnable task = event.getRunnable();

        if(task.getGameState() == GameManager.GameState.LOBBYPHASE) {

            if(task.getTime() == 59) {
                Data.voteManager.setVotePeriodActive(true);
            } else if(task.getTime() == 5) {
                Data.voteManager.setVotePeriodActive(false);
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.closeInventory();
                });

                if(!Data.mapManager.isMapSet()) {
                    Map map = Data.mapManager.getVotedMap();
                    Data.mapManager.setMapToPlay(map);
                    Bukkit.broadcastMessage(Data.getPrefix() + "§aEs wird gespielt auf: §b" + map.getName());
                    Bukkit.getOnlinePlayers().forEach(player -> Data.sbManager.updateLobbySB(player));
                } else {
                    Map map = Data.mapManager.getMapToPlay();
                    Bukkit.broadcastMessage(Data.getPrefix() + "§aEs wird gespielt auf: §b" + map.getName());
                }
            }

        }

    }

}
