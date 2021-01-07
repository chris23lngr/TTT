package de.z1up.ttt.event;

import de.z1up.ttt.scheduler.TTTRunnable;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimerStartEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private TTTRunnable runnable;
    private boolean isCancelled;

    public TimerStartEvent(TTTRunnable runnable, boolean isCancelled) {
        this.runnable = runnable;
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

    public TTTRunnable getRunnable() {
        return runnable;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}