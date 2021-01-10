package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.TimerFinishEvent;
import de.z1up.ttt.event.GameStateChangeEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.scheduler.TTTRunnable;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerTimerFinish implements Listener {

    public ListenerTimerFinish() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final TimerFinishEvent event) {

        TTTRunnable task = event.getRunnable();

        if(task.getGameState() == GameManager.GameState.LOBBYPHASE) {

            task.cancel();

            GameStateChangeEvent gameStateChangeEvent = new GameStateChangeEvent(GameManager.GameState.LOBBYPHASE, GameManager.GameState.PRE_SAVEPHASE, false);
            if(Bukkit.getOnlinePlayers().size() < Bukkit.getMaxPlayers()) {
                gameStateChangeEvent.setCancelled(true);

                // if there are not enough players
                // online, reInit the lobby timer and
                // start the timer which send the hotbar
                // messages
                TTT.core.getTimerManager().initLobbyTimer();
                TTT.core.getTimerManager().startTimerWaitingForPlayers();
            }
            TTT.core.getGameManager().setGameState(GameManager.GameState.PRE_SAVEPHASE);
            if(!gameStateChangeEvent.isCancelled()) {
                Bukkit.getPluginManager().callEvent(gameStateChangeEvent);
            }

            return;
        }

        if(task.getGameState() == GameManager.GameState.PRE_SAVEPHASE) {

            TTT.core.getGameManager().setGameState(GameManager.GameState.SAVEPHASE);
            GameStateChangeEvent gameStateChangeEvent = new GameStateChangeEvent(GameManager.GameState.PRE_SAVEPHASE, GameManager.GameState.SAVEPHASE, false);
            Bukkit.getPluginManager().callEvent(gameStateChangeEvent);

            return;
        }

        if(task.getGameState() == GameManager.GameState.SAVEPHASE) {

            TTT.core.getGameManager().setGameState(GameManager.GameState.INGAME);
            GameStateChangeEvent gameStateChangeEvent = new GameStateChangeEvent(GameManager.GameState.SAVEPHASE, GameManager.GameState.INGAME, false);
            Bukkit.getPluginManager().callEvent(gameStateChangeEvent);

            return;
        }

        if(task.getGameState() == GameManager.GameState.INGAME) {

            GameStateChangeEvent gameStateChangeEvent = new GameStateChangeEvent(GameManager.GameState.INGAME, GameManager.GameState.RESTART, false);
            if(!gameStateChangeEvent.isCancelled()) {
                Bukkit.getPluginManager().callEvent(gameStateChangeEvent);
            }
            return;
        }

        if(task.getGameState() == GameManager.GameState.RESTART) {
            Bukkit.getServer().reload();
        }

    }

}
