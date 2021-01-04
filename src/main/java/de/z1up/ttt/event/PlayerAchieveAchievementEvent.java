package de.z1up.ttt.event;

import de.z1up.ttt.util.o.Achievement;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAchieveAchievementEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private Achievement achievement;
    private boolean isCancelled;

    public PlayerAchieveAchievementEvent(Player player, Achievement achievement, boolean isCancelled) {
        this.player = player;
        this.achievement = achievement;
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

    public Player getPlayer() {
        return player;
    }

    public Achievement getAchievement() {
        return achievement;
    }
}