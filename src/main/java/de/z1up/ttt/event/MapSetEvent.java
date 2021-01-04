package de.z1up.ttt.event;

import de.z1up.ttt.util.o.Achievement;
import de.z1up.ttt.util.o.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MapSetEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Map map;
    private boolean isCancelled;

    public MapSetEvent(Map map, boolean isCancelled) {
        this.map = map;
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

    public Map getMap() {
        return map;
    }
}
