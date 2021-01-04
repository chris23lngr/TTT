package de.z1up.ttt.event;

import de.z1up.ttt.manager.GameManager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private GameManager.GameState from;
    private GameManager.GameState to;
    private boolean isCancelled;

    public GameStateChangeEvent(GameManager.GameState from, GameManager.GameState to, boolean isCancelled) {
        this.from = from;
        this.to = to;
        this.isCancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public GameManager.GameState getFrom() {
        return from;
    }

    public GameManager.GameState getTo() {
        return to;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
