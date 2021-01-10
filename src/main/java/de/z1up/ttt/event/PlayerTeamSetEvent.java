package de.z1up.ttt.event;

import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTeamSetEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private TTTPlayer tttPlayer;
    private ManagerTeam.Team team;
    private boolean isCancelled;

    public PlayerTeamSetEvent(TTTPlayer tttPlayer, ManagerTeam.Team team, boolean isCancelled) {
        this.tttPlayer = tttPlayer;
        this.team = team;
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

    public ManagerTeam.Team getTeam() {
        return team;
    }

    public TTTPlayer getTttPlayer() {
        return tttPlayer;
    }
}