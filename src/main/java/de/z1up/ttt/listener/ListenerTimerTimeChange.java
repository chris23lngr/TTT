package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.TimerTimeChangeEvent;
import de.z1up.ttt.event.MapSetEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.scheduler.TTTRunnable;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerTimerTimeChange implements Listener {

    public ListenerTimerTimeChange() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final TimerTimeChangeEvent event) {

        TTTRunnable task = event.getRunnable();

        if(task.getGameState() == GameManager.GameState.LOBBYPHASE) {

            Bukkit.getOnlinePlayers().forEach(player -> {
                player.setLevel(task.getTime());
            });

            if(task.getTime() == 59) {

                // activate everyones
                TTT.core.getVoteManager().setVotePeriodActive(true);
            } else if(task.getTime() == 5) {
                TTT.core.getVoteManager().setVotePeriodActive(false);

                // close everyones inventory so that
                // they can't vote anymore
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.closeInventory();
                });

                if(!TTT.core.getMapManager().isMapSet()) {
                    Map map = TTT.core.getMapManager().getVotedMap();
                    TTT.core.getMapManager().setMapToPlay(map);
                    Event mapSetEvent = new MapSetEvent(map, false);
                    Bukkit.getPluginManager().callEvent(mapSetEvent);
                }
            }

        }

    }

}
