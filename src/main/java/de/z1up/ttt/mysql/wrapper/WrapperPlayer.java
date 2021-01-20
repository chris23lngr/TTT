package de.z1up.ttt.mysql.wrapper;

import de.z1up.ttt.interfaces.Wrapper;
import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.util.o.DBPlayer;
import de.z1up.ttt.util.uuid.UUIDFetcher;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WrapperPlayer implements Wrapper {

    private final String TABLE_NAME = "players";
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

    public WrapperPlayer(SQL sql) {
        this.sql = sql;
        createTable();
    }

    @Override
    public void createTable() {

        String stmt = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ATTRIBUTE_UUID + " varchar(64), " + ATTRIBUTE_WINS + " int, " + ATTRIBUTE_LOOSES + " int, " + ATTRIBUTE_T_PASSES + " int, " + ATTRIBUTE_D_PASSES + " int, "
                + ATTRIBUTE_I_PASSES + " int, " + ATTRIBUTE_KARMA + " int, " + ATTRIBUTE_KILLS + " int, " + ATTRIBUTE_DEATHS + " int, " + ATTRIBUTE_ACHIEVEMENTS + " varchar(256), " + ATTRIBUTE_TESTER_ENTERED + " int, " + ATTRIBUTE_PASSES_USED + " int);";

        sql.executeUpdateAsync(stmt, null);

    }

    @Override
    public void insert(Object e) {

        if(!(e instanceof DBPlayer)) return;

        DBPlayer player = (DBPlayer) e;

        String stmt = "INSERT INTO " + TABLE_NAME + "(" + ATTRIBUTE_UUID + ", " + ATTRIBUTE_WINS + ", " + ATTRIBUTE_LOOSES + ", " + ATTRIBUTE_T_PASSES + ", " + ATTRIBUTE_D_PASSES + ", " + ATTRIBUTE_I_PASSES + ", "
                + ATTRIBUTE_KARMA + ", " + ATTRIBUTE_KILLS + ", " + ATTRIBUTE_DEATHS + ", " + ATTRIBUTE_ACHIEVEMENTS + ", " + ATTRIBUTE_TESTER_ENTERED + ", " + ATTRIBUTE_PASSES_USED + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

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

        sql.executeUpdateAsync(stmt,
                Arrays.asList(uuid, wins, looses, tPasses, dPasses, iPasses, karma, kills,
                        deaths, achievementsString, testerEntered, passesUsed));

    }

    @Override
    public void update(Object e) {

        if(!(e instanceof DBPlayer)) return;

        DBPlayer player = (DBPlayer) e;

        String stmt = "UPDATE " + TABLE_NAME + " SET " + ATTRIBUTE_WINS + "=?, " + ATTRIBUTE_LOOSES + "=?, " + ATTRIBUTE_T_PASSES + "=?, " + ATTRIBUTE_D_PASSES + "=?, " + ATTRIBUTE_I_PASSES + "=?, " + ATTRIBUTE_KARMA + "=?, "
                + ATTRIBUTE_KILLS + "=?, " + ATTRIBUTE_DEATHS + "=?, " + ATTRIBUTE_ACHIEVEMENTS + "=?, " + ATTRIBUTE_TESTER_ENTERED + "=?, " + ATTRIBUTE_PASSES_USED + "=? " + "WHERE " + ATTRIBUTE_UUID + "=?;";

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

        sql.executeUpdateAsync(stmt,
                Arrays.asList(wins, looses, tPasses, dPasses, iPasses, karma, kills,
                        deaths, achievementsString, testerEntered, passesUsed, uuid));

    }

    @Override
    public void delete(Object e) {

        String stmt = "DELETE * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_UUID + "=?;";
        Object attribute = null;

        if(e instanceof DBPlayer) {
            attribute = ((DBPlayer) e).getUuid();
        } else if((e instanceof String) || (e instanceof UUID)) {
            attribute = e;
        }

        sql.executeUpdateAsync(stmt, Arrays.asList(attribute));

    }

    @Override
    public Object get(Object e) {

        String stmt = "SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_UUID + "=?;";
        Object attribute = null;

        if(e instanceof Player) {
            attribute = ((Player) e).getUniqueId();
        } else if((e instanceof String) || (e instanceof UUID)) {
            attribute = e;
        }

        ResultSet rs = sql.getResult(stmt, Arrays.asList(attribute));

        try {
            if(rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(ATTRIBUTE_UUID));
                int wins = rs.getInt(ATTRIBUTE_WINS);
                int looses = rs.getInt(ATTRIBUTE_LOOSES);
                int tPasses = rs.getInt(ATTRIBUTE_T_PASSES);
                int dPasses = rs.getInt(ATTRIBUTE_D_PASSES);
                int iPasses = rs.getInt(ATTRIBUTE_I_PASSES);
                int karma = rs.getInt(ATTRIBUTE_KARMA);
                int kills = rs.getInt(ATTRIBUTE_KILLS);
                int deaths = rs.getInt(ATTRIBUTE_DEATHS);
                Collection<Integer> achievements = form(rs.getString(ATTRIBUTE_ACHIEVEMENTS));
                int testerEntered = rs.getInt(ATTRIBUTE_TESTER_ENTERED);
                int passesUsed = rs.getInt(ATTRIBUTE_PASSES_USED);

                DBPlayer dbPlayer = new DBPlayer(uuid, wins, looses, tPasses, dPasses, iPasses, karma, kills, deaths, achievements, testerEntered, passesUsed);
                return dbPlayer;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public boolean exists(Player player) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_UUID, player.getUniqueId());
    }

    public boolean exists(UUID uuid) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_UUID, uuid);
    }

    public String form(Collection<Integer> achievements) {

        if(achievements == null) {
            return "/";
        }

        String achievementsString = "";
        for (Integer achievementID : achievements) {
            achievementsString = achievementsString + achievementID + ";";
        }
        return achievementsString;
    }

    public Collection<Integer> form(String achievementsString) {

        if(achievementsString == null || achievementsString.equals("")) {
            return new ArrayList<>();
        }

        Collection<Integer> achievements = new ArrayList<>();
        String[] achieved = achievementsString.split(";");
        for (String s : achieved) {
            int id = Integer.parseInt(s);
            achievements.add(id);
        }
        return achievements;
    }

    public ArrayList<DBPlayer> getTopPlayers() {

        String stmt = "SELECT * FROM " + TABLE_NAME + " ORDER BY "+ ATTRIBUTE_KARMA + " DESC LIMIT 5;";
        //String stmt = "SELECT * FROM " + TABLE_NAME + "";

        ResultSet rs = sql.getResult(stmt, null);

        ArrayList<DBPlayer> topPlayers = new ArrayList<>();

        try {
            while(rs.next()) {
                DBPlayer player = (DBPlayer) get(UUID.fromString(rs.getString(ATTRIBUTE_UUID)));
                System.out.println(UUIDFetcher.getName(player.getUuid()));
                topPlayers.add(player);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return topPlayers;
    }

}
