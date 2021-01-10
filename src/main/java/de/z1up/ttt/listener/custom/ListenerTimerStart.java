package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.TimerStartEvent;
import de.z1up.ttt.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerTimerStart implements Listener {

    public ListenerTimerStart() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final TimerStartEvent event) {

        TTT.core.getVoteManager().setVotePeriodActive(true);

        if(event.getRunnable().getGameState() == GameManager.GameState.LOBBYPHASE) {

            // activate the vote period
            System.out.println("it ist the timer");

        }

    }

}
