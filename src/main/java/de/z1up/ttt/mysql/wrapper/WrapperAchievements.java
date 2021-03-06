package de.z1up.ttt.mysql.wrapper;

import de.z1up.ttt.interfaces.Wrapper;
import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.util.o.Achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class WrapperAchievements implements Wrapper {

    private final String TABLE_NAME = "achievements";
    private final String ATTRIBUTE_ID = "ID";
    private final String ATTRIBUTE_NAME = "NAME";
    private final String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
    private final String ATTRIBUTE_ACHIEVED_BY = "ACHIEVED_BY";

    private SQL sql;

    public WrapperAchievements(SQL sql) {

        this.sql = sql;

        createTable();
        insertAchievements();
    }

    @Override
    public void createTable() {

        String stmt = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ATTRIBUTE_ID + " int, " + ATTRIBUTE_NAME + " varchar(256), " + ATTRIBUTE_DESCRIPTION + " varchar(256), " + ATTRIBUTE_ACHIEVED_BY + " int);";
        sql.executeUpdateAsync(stmt, null);

    }

    @Override
    public void insert(Object e) {

        if(!(e instanceof Achievement)) {
            return;
        }

        Achievement achievement = (Achievement) e;

        int id = achievement.getId();
        String name = achievement.getName();
        String desc = achievement.getDescription();
        int achievedBy = achievement.getAchievedBy();

        String stmt = "INSERT INTO " + TABLE_NAME + " (" + ATTRIBUTE_ID + ", " + ATTRIBUTE_NAME + ", " + ATTRIBUTE_DESCRIPTION + ", " + ATTRIBUTE_ACHIEVED_BY + ") VALUES (?, ?, ?, ?);";

        sql.executeUpdateAsync(stmt, Arrays.asList(id, name, desc, achievedBy));
    }

    @Override
    public void update(Object e) {

        if(!(e instanceof Achievement)) {
            return;
        }

        Achievement achievement = (Achievement) e;

        int id = achievement.getId();
        String name = achievement.getName();
        String desc = achievement.getDescription();
        int achievedBy = achievement.getAchievedBy();

        String stmt = "UPDATE " + TABLE_NAME + " SET " + ATTRIBUTE_NAME + "=?, " + ATTRIBUTE_DESCRIPTION + "=?, " + ATTRIBUTE_ACHIEVED_BY + "=? " + "WHERE " + ATTRIBUTE_ID + "=?;";

        sql.executeUpdateAsync(stmt,
                Arrays.asList(name, desc, achievedBy, id));
    }

    @Override
    public void delete(Object e) {

        if(!(e instanceof Achievement)) {
            return;
        }

        Achievement achievement = (Achievement) e;

        int id = achievement.getId();

        String stmt = "DELETE * FROM " + TABLE_NAME + "WHERE " + ATTRIBUTE_ID + "=?;";
        sql.executeUpdateAsync(stmt, Arrays.asList(e));

    }

    @Override
    public Object get(Object e) {

        String stmt = "SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_ID + "=?;";
        ResultSet rs = sql.getResult(stmt, Arrays.asList(e));

        try {
            rs.next();

            int id = rs.getInt(ATTRIBUTE_ID);
            String name = rs.getString(ATTRIBUTE_NAME);
            String desc = rs.getString(ATTRIBUTE_DESCRIPTION);
            int achievedBy = rs.getInt(ATTRIBUTE_ACHIEVED_BY);

            Achievement achievement = new Achievement(id, name, desc, achievedBy);
            return achievement;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    private void insertAchievements() {
        int id = 0;
        while (id < 15) {
            id = id + 1;
            //if(!existsAchievement(id)) insertAchievement(getNewAchievement(id));
        }
    }

    public boolean existsAchievement(Object e) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_ID, e);
    }

    public Achievement getNewAchievement(int id) {
        if(id == 0 || id > 50) {
            return getFirstKill();
        }
        if(id == 1) return getFirstKill();
        if(id == 2) return getGameMode();
        if(id == 3) return getFirstWin();
        if(id == 4) return getFirstDetectiveWin();
        if(id == 5) return getFirstTraitorWin();
        if(id == 6) return getLaeuft();
        if(id == 7) return getDoneWell();
        if(id == 8) return getPro();
        if(id == 9) return getWOW();
        if(id == 10) return getMaschine();
        if(id == 11) return getSchoen();
        if(id == 12) return getSchoenGemacht();
        if(id == 13) return getGeil();
        if(id == 14) return getNice();
        if(id == 15) return getWinner();
        if(id == 16) return getOP();
        if(id == 17) return getTester();
        return null;
    }

    public Achievement getFirstKill() {
        Achievement achievement = new Achievement(1, "First Kill", "Kille als erstes in der Runde einen Spieler", 0);
        return achievement;
    }

    public Achievement getGameMode() {
        Achievement achievement = new Achievement(2, "Gamemode?", "Gewinne einen Kampf ohne Schaden zu bekommen", 0);
        return achievement;
    }

    public Achievement getFirstWin() {
        Achievement achievement = new Achievement(3, "First Win", "Dein erster Sieg :3", 0);
        return achievement;
    }

    public Achievement getFirstDetectiveWin() {
        Achievement achievement = new Achievement(4, "First Detektiv win", "Dein erster Detektiv Sieg", 0);
        return achievement;
    }

    public Achievement getFirstTraitorWin() {
        Achievement achievement = new Achievement(5, "First Traitor win", "Dein erster Traitor Sieg", 0);
        return achievement;
    }

    public Achievement getLaeuft() {
        Achievement achievement = new Achievement(6, "Läuft", "Mache 10 Kills", 0);
        return achievement;
    }

    public Achievement getDoneWell() {
        Achievement achievement = new Achievement(7, "Gut gemacht", "Mache 25 Kills", 0);
        return achievement;
    }

    public Achievement getPro() {
        Achievement achievement = new Achievement(8, "Pro!", "Mache 50 Kills", 0);
        return achievement;
    }

    public Achievement getWOW() {
        Achievement achievement = new Achievement(9, "WOW!", "Mache 75 Kills", 0);
        return achievement;
    }

    public Achievement getMaschine() {
        Achievement achievement = new Achievement(10, "Maschine", "Mache 100 Kills", 0);
        return achievement;
    }

    public Achievement getSchoen() {
        Achievement achievement = new Achievement(11, "Schön!", "Mache 10 Siege", 0);;
        return achievement;
    }

    public Achievement getSchoenGemacht() {
        Achievement achievement = new Achievement(12, "Schön Gemacht", "Mache 25 Siege", 0);
        return achievement;
    }


    public Achievement getGeil() {
        Achievement achievement = new Achievement(13, "Geil", "Du hast 50 Siege erreicht! Respekt :D", 0);
        return achievement;
    }

    public Achievement getNice() {
        Achievement achievement = new Achievement(14, "Nice", "Mache 75 Siege", 0);
        return achievement;
    }

    public Achievement getWinner() {
        Achievement achievement = new Achievement(15, "Winner", "Mache 100 Siege. Respekt :D", 0);
        return achievement;
    }

    public Achievement getOP() {
        Achievement achievement = new Achievement(16, "OP", "Öffne eine Enderchest", 0);
        return achievement;
    }

    public Achievement getTester() {
        Achievement achievement = new Achievement(17, "Tester", "Betrete den Tester als Innocent", 0);
        return achievement;
    }

    public Achievement getMutig() {
        Achievement achievement = new Achievement(18, "Mutig", "Betrete den Tester als Traitor", 0);
        return achievement;
    }

    public Achievement getUnnoetig() {
        Achievement achievement = new Achievement(19, "Unnötig?", "Betrete den Tester als Detektiv", 0);
        return achievement;
    }

    public Achievement getFirstDeath() {
        Achievement achievement = new Achievement(20, "First Death", "Sterbe als erster in der Runde", 0);
        return achievement;
    }


}
