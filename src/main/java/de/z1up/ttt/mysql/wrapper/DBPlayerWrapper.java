package de.z1up.ttt.mysql.wrapper;

import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.util.o.DBPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class DBPlayerWrapper {

    private final String TABLE_NAME = "ttt_player";
    private final String ATTRIBUTE_UUID = "UUID";
    private final String ATTRIBUTE_WINS = "WINS";
    private final String ATTRIBUTE_LOOSES = "LOOSES";
    private final String ATTRIBUTE_T_PASSES = "TRAITOR_PASSES";
    private final String ATTRIBUTE_D_PASSES = "DETECTIVE_PASSES";
    private final String ATTRIBUTE_I_PASSES = "INNOCENT_PASSES";
    private final String ATTRIBUTE_KARMA = "KARMA";
    private final String ATTRIBUTE_KILLS = "KILLS";
    private final String ATTRIBUTE_DEATHS = "DEATHS";
    private final String ATTRIBUTE_ACHIEVEMENTS = "ACHIEVEMENTS";
    private final String ATTRIBUTE_TESTER_ENTERED = "TESTER_ENTERED";
    private final String ATTRIBUTE_PASSES_USED = "PASSES_USED";

    private SQL sql;

    public DBPlayerWrapper(SQL sql) {
        this.sql = sql;
        createTable();
    }

    void createTable() {

        sql.executeUpdateAsync("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + ATTRIBUTE_UUID + " varchar(64), "
                + ATTRIBUTE_WINS + " int, "
                + ATTRIBUTE_LOOSES + " int, "
                + ATTRIBUTE_T_PASSES + " int, "
                + ATTRIBUTE_D_PASSES + " int, "
                + ATTRIBUTE_I_PASSES + " int, "
                + ATTRIBUTE_KARMA + " int, "
                + ATTRIBUTE_KILLS + " int, "
                + ATTRIBUTE_DEATHS + " int, "
                + ATTRIBUTE_ACHIEVEMENTS + " varchar(256), "
                + ATTRIBUTE_TESTER_ENTERED + " int, "
                + ATTRIBUTE_PASSES_USED + " int)", null);

    }

    public void insertPlayer(DBPlayer player) {

        UUID uuid = player.getUuid();
        int wins = player.getWins();
        int looses = player.getLooses();
        int tPasses = player.getTPasses();
        int dPasses = player.getDPasses();
        int iPasses = player.getIPasses();
        int karma = player.getKarma();
        int kills = player.getKills();
        int deaths = player.getDeaths();
        Collection<Integer> achievements = player.getAchievements();
        String achievementsString = form(achievements);
        int testerEntered = player.getTesterEntered();
        int passesUsed = player.getPassesUsed();

        sql.executeUpdateAsync("INSERT INTO " + TABLE_NAME + "("
                + ATTRIBUTE_UUID + ", "
                + ATTRIBUTE_WINS + ", "
                + ATTRIBUTE_LOOSES + ", "
                + ATTRIBUTE_T_PASSES + ", "
                + ATTRIBUTE_D_PASSES + ", "
                + ATTRIBUTE_I_PASSES + ", "
                + ATTRIBUTE_KARMA + ", "
                + ATTRIBUTE_KILLS + ", "
                + ATTRIBUTE_DEATHS + ", "
                + ATTRIBUTE_ACHIEVEMENTS + ", "
                + ATTRIBUTE_TESTER_ENTERED + ", "
                + ATTRIBUTE_PASSES_USED
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Arrays.asList(uuid, wins, looses, tPasses, dPasses, iPasses, karma, kills,
                        deaths, achievementsString, testerEntered, passesUsed));

    }

    public void updatePlayer(DBPlayer player) {

        UUID uuid = player.getUuid();
        int wins = player.getWins();
        int looses = player.getLooses();
        int tPasses = player.getTPasses();
        int dPasses = player.getDPasses();
        int iPasses = player.getIPasses();
        int karma = player.getKarma();
        int kills = player.getKills();
        int deaths = player.getDeaths();
        Collection<Integer> achievements = player.getAchievements();
        String achievementsString = form(achievements);
        int testerEntered = player.getTesterEntered();
        int passesUsed = player.getPassesUsed();

        sql.executeUpdateAsync("UPDATE " + TABLE_NAME + " SET "
                        + ATTRIBUTE_WINS + "=?, "
                        + ATTRIBUTE_LOOSES + "=?, "
                        + ATTRIBUTE_T_PASSES + "=?, "
                        + ATTRIBUTE_D_PASSES + "=?, "
                        + ATTRIBUTE_I_PASSES + "=?, "
                        + ATTRIBUTE_KARMA + "=?, "
                        + ATTRIBUTE_KILLS + "=?, "
                        + ATTRIBUTE_DEATHS + "=?, "
                        + ATTRIBUTE_ACHIEVEMENTS + "=?, "
                        + ATTRIBUTE_TESTER_ENTERED + "=?, "
                        + ATTRIBUTE_PASSES_USED + "=? "
                        + "WHERE " + ATTRIBUTE_UUID + "=?",
                Arrays.asList(wins, looses, tPasses, dPasses, iPasses, karma, kills,
                        deaths, achievementsString, testerEntered, passesUsed, uuid));

    }

    public boolean isRegistered(UUID uuid) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_UUID, uuid);
    }

    public String form(Collection<Integer> achievements) {
        String achievementsString = "";
        for (Integer achievementID : achievements) {
            achievementsString = achievementsString + achievementID + ";";
        }
        return achievementsString;
    }

    public Collection<Integer> form(String achievementsString) {
        Collection<Integer> achievements = new ArrayList<>();
        String[] achieved = achievementsString.split(";");
        for (String s : achieved) {
            int id = Integer.parseInt(s);
            achievements.add(id);
        }
        return achievements;
    }

}
