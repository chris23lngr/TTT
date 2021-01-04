package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.CountdownFinishEvent;
import de.z1up.ttt.event.GameStateChangeEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.scheduler.TTTRunnable;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerCountdownFinish implements Listener {

    public ListenerCountdownFinish() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final CountdownFinishEvent event) {

        TTTRunnable task = event.getRunnable();

        if(task.getGameState() == GameManager.GameState.LOBBYPHASE) {

            GameStateChangeEvent gameStateChangeEvent = new GameStateChangeEvent(GameManager.GameState.LOBBYPHASE, GameManager.GameState.SCHUTZPHASE, false);
            if(Bukkit.getOnlinePlayers().size() > Bukkit.getMaxPlayers()) {
                gameStateChangeEvent.setCancelled(true);
            }
            if(!gameStateChangeEvent.isCancelled()) {
                Bukkit.getPluginManager().callEvent(gameStateChangeEvent);
            }
            return;
        }

        if(task.getGameState() == GameManager.GameState.SCHUTZPHASE) {

            GameStateChangeEvent gameStateChangeEvent = new GameStateChangeEvent(GameManager.GameState.SCHUTZPHASE, GameManager.GameState.INGAME, false);
            if(!gameStateChangeEvent.isCancelled()) {
                Bukkit.getPluginManager().callEvent(gameStateChangeEvent);
            }
            return;
        }

        if(task.getGameState() == GameManager.GameState.INGAME) {

            GameStateChangeEvent gameStateChangeEvent = new GameStateChangeEvent(GameManager.GameState.INGAME, GameManager.GameState.RESTART, false);
            if(!gameStateChangeEvent.isCancelled()) {
                Bukkit.getPluginManager().callEvent(gameStateChangeEvent);
            }
            return;
        }

    }

}
