package de.z1up.ttt.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillMateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player killer;
    private Player target;
    private boolean isCancelled;

    public PlayerKillMateEvent(Player killer, Player target, boolean isCancelled) {
        this.killer = killer;
        this.target = target;
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

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getKiller() {
        return killer;
    }

    public Player getTarget() {
        return target;
    }
}
