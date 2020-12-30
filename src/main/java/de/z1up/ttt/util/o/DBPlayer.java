package de.z1up.ttt.util.o;


import java.util.Collection;
import java.util.UUID;

public class DBPlayer {

    UUID uuid;
    int wins;
    int looses;
    int tPasses;
    int dPasses;
    int iPasses;
    int karma;
    int kills;
    int deaths;
    Collection<Integer> achievements;

    public DBPlayer(UUID uuid, int wins, int looses, int tPasses, int dPasses, int iPasses, int karma, int kills, int deaths, Collection<Integer> achievements) {
        this.uuid = uuid;
        this.wins = wins;
        this.looses = looses;
        this.tPasses = tPasses;
        this.dPasses = dPasses;
        this.iPasses = iPasses;
        this.karma = karma;
        this.kills = kills;
        this.deaths = deaths;
        this.achievements = achievements;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.wins = wins + 1;
    }

    public int getLooses() {
        return looses;
    }

    public void addLoose() {
        this.looses = looses + 1;
    }

    public int getTPasses() {
        return tPasses;
    }

    public void addTPass() {
        this.tPasses = tPasses + 1;
    }

    public void removeTPass() {
        this.tPasses = tPasses - 1;
    }

    public int getDPasses() {
        return dPasses;
    }

    public void addDPass() {
        this.dPasses = dPasses + 1;
    }

    public void removeDPass() {this.dPasses = dPasses - 1;}

    public int getIPasses() {
        return iPasses;
    }

    public void addIPass() {
        this.iPasses = iPasses + 1;
    }

    public void removeIPass() {this.iPasses = iPasses - 1;}

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public void addKarma(int karma) {
        this.karma = this.karma + karma;
    }

    public void removeKarma(int karma) {
        this.karma = this.karma - karma;
    }

    public void update() {

    }
}
