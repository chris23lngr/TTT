package de.z1up.ttt.util.o;

import de.z1up.ttt.manager.ManagerTeam;

import java.util.Collection;
import java.util.UUID;

public class TTTPlayer extends DBPlayer {

    private ManagerTeam.Team team;
    private int gameKills;
    private boolean alive;

    public TTTPlayer(DBPlayer dbPlayer, ManagerTeam.Team team, int gameKills, boolean alive) {
        this(dbPlayer.getUuid(), dbPlayer.getWins(), dbPlayer.getLooses(), dbPlayer.getTPasses(), dbPlayer.getDPasses(), dbPlayer.getIPasses(), dbPlayer.getKarma(), dbPlayer.getKills(), dbPlayer.getDeaths(), dbPlayer.getAchievements(), dbPlayer.getTesterEntered(), dbPlayer.getPassesUsed(), team, gameKills, alive);
    }

    public TTTPlayer(UUID uuid, int wins, int looses, int tPasses, int dPasses, int iPasses, int karma, int kills, int deaths, Collection<Integer> achievements, int testerEntered, int passesUsed, ManagerTeam.Team team, int gameKills, boolean alive) {
        super(uuid, wins, looses, tPasses, dPasses, iPasses, karma, kills, deaths, achievements, testerEntered, passesUsed);

        this.team = team;
        this.gameKills = gameKills;
        this.alive = alive;
    }

    public ManagerTeam.Team getTeam() {
        return team;
    }

    public void setTeam(ManagerTeam.Team team) {
        this.team = team;
    }

    public boolean hasTeam() {
        return team != null;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void addGameKill() {
        this.kills = kills + 1;
    }
}
