package de.z1up.ttt.event;

import de.z1up.ttt.manager.GameManager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The GameEndEvent is called when there is a GameResult.
 * A {@link de.z1up.ttt.manager.GameManager.GameResult}
 * must be passed to determine which team has finally won.
 *
 * @author chris23lngr
 * @since 1.0
 * @see org.bukkit.event.Event
 * @see org.bukkit.event.Cancellable
 * @see de.z1up.ttt.manager.GameManager.GameResult
 */
public class GameEndEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private GameManager.GameResult gameResult;
    private boolean isCancelled;

    public GameEndEvent(GameManager.GameResult gameResult, boolean isCancelled) {
        this.gameResult = gameResult;
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

    public GameManager.GameResult getGameResult() {
        return gameResult;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
