package de.z1up.ttt.listener;

import com.sun.xml.internal.ws.resources.UtilMessages;
import de.z1up.ttt.TTT;
import de.z1up.ttt.event.CountdownTimeChangeEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.scheduler.TTTRunnable;
import de.z1up.ttt.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
            }

        }

    }

}
