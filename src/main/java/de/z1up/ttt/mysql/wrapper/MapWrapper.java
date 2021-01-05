package de.z1up.ttt.mysql.wrapper;

import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MapWrapper implements Wrapper{

    private final String TABLE_NAME = "maps";
    private final String ATTRIBUTE_ID = "ID";
    private final String ATTRIBUTE_NAME = "NAME";
    private final String ATTRIBUTE_ITEM_MAT = "ITEM_MAT";
    private final String ATTRIBUTE_JUDGE_COUNT_VERY_BAD = "VERY_BAD";
    private final String ATTRIBUTE_JUDGE_COUNT_BAD = "BAD";
    private final String ATTRIBUTE_JUDGE_COUNT_NEUTRAL = "NEUTRAL";
    private final String ATTRIBUTE_JUDGE_COUNT_GOOD = "GOOD";
    private final String ATTRIBUTE_JUDGE_COUNT_VERY_GOOD ="VERY_GOOD";

    private SQL sql;

    public MapWrapper(SQL sql) {
        this.sql = sql;
        createTable();
    }

    @Override
    public void createTable() {

        String stmt = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ATTRIBUTE_ID + " int, " + ATTRIBUTE_NAME + " varchar(256), " + ATTRIBUTE_ITEM_MAT + " varchar(256), " + ATTRIBUTE_JUDGE_COUNT_VERY_BAD + " bigint, "
                + ATTRIBUTE_JUDGE_COUNT_BAD + " bigint, " + ATTRIBUTE_JUDGE_COUNT_NEUTRAL + " bigint, " + ATTRIBUTE_JUDGE_COUNT_GOOD + " bigint, " + ATTRIBUTE_JUDGE_COUNT_VERY_GOOD + " bigint)";

        sql.executeUpdateAsync(stmt, null);

    }

    @Override
    public void insert(Object e) {

        if(!(e instanceof Map)) {
            return;
        }

        Map map = (Map) e;

        String stmt = "INSERT INTO " + TABLE_NAME + " (" + ATTRIBUTE_ID + ", " + ATTRIBUTE_NAME + ", " + ATTRIBUTE_ITEM_MAT + ", " + ATTRIBUTE_JUDGE_COUNT_VERY_BAD + ", " + ATTRIBUTE_JUDGE_COUNT_BAD + ", "
                + ATTRIBUTE_JUDGE_COUNT_NEUTRAL + ", " + ATTRIBUTE_JUDGE_COUNT_GOOD + ", " + ATTRIBUTE_JUDGE_COUNT_VERY_GOOD + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        int id = map.getId();
        String name = map.getName();
        String itemMat = map.getItemMat().name();
        int veryBadRatings = map.getRatings().get(Map.RatingType.VERY_BAD);
        int badRatings = map.getRatings().get(Map.RatingType.BAD);
        int neutralRatings = map.getRatings().get(Map.RatingType.NEUTRAL);
        int goodRatings = map.getRatings().get(Map.RatingType.GOOD);
        int veryGoodRatings = map.getRatings().get(Map.RatingType.VERY_GOOD);

        sql.executeUpdateAsync(stmt, Arrays.asList(id, name, itemMat, veryBadRatings, badRatings, neutralRatings, goodRatings, veryGoodRatings));
    }

    @Override
    public void update(Object e) {

        if(!(e instanceof Map)) {
            return;
        }

        Map map = (Map) e;

        String stmt = "UPDATE " + TABLE_NAME + " SET " + ATTRIBUTE_NAME + "=?, " + ATTRIBUTE_ITEM_MAT + "=?, " + ATTRIBUTE_JUDGE_COUNT_VERY_BAD + "=?, " + ATTRIBUTE_JUDGE_COUNT_BAD + "=?, "
                + ATTRIBUTE_JUDGE_COUNT_NEUTRAL + "=?, " + ATTRIBUTE_JUDGE_COUNT_GOOD + "=?, " + ATTRIBUTE_JUDGE_COUNT_VERY_GOOD + "=? WHERE " + ATTRIBUTE_ID + "=?";


        int id = map.getId();
        String name = map.getName();
        String itemMat = map.getItemMat().name();
        int veryBadRatings = map.getRatings().get(Map.RatingType.VERY_BAD);
        int badRatings = map.getRatings().get(Map.RatingType.BAD);
        int neutralRatings = map.getRatings().get(Map.RatingType.NEUTRAL);
        int goodRatings = map.getRatings().get(Map.RatingType.GOOD);
        int veryGoodRatings = map.getRatings().get(Map.RatingType.VERY_GOOD);

        sql.executeUpdateAsync(stmt, Arrays.asList(name, itemMat, veryBadRatings, badRatings, neutralRatings, goodRatings, veryGoodRatings, id));

    }

    @Override
    public void delete(Object e) {

        if(!(e instanceof Map)) {
            return;
        }

        Map map = (Map) e;

        String stmt = "DELETE * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_ID + "=?";

        int id = map.getId();

        sql.executeUpdateAsync(stmt, Arrays.asList(id));
    }

    @Override
    public Object get(Object e) {

        String stmt = "SELECT * FROM " + TABLE_NAME + " WHERE ";

        Object attribute = null;

        if( e instanceof Integer) {
            attribute = (Integer) e;
            stmt = stmt  + ATTRIBUTE_ID + "=?";
        } else if(e instanceof String) {
            attribute = (String) e;
            stmt = stmt  + ATTRIBUTE_NAME + "=?";
        }

        ResultSet rs = sql.getResult(stmt, Arrays.asList(attribute));

        try {
            if(rs.next()) {

                int id = rs.getInt(ATTRIBUTE_ID);
                String name = rs.getString(ATTRIBUTE_NAME);
                Material itemMat = Material.valueOf(rs.getString(ATTRIBUTE_ITEM_MAT));
                int veryBadRatings = rs.getInt(ATTRIBUTE_JUDGE_COUNT_VERY_BAD);
                int badRatings = rs.getInt(ATTRIBUTE_JUDGE_COUNT_BAD);
                int neutralRatings = rs.getInt(ATTRIBUTE_JUDGE_COUNT_NEUTRAL);
                int goodRatings = rs.getInt(ATTRIBUTE_JUDGE_COUNT_GOOD);
                int veryGoodRatings = rs.getInt(ATTRIBUTE_JUDGE_COUNT_VERY_GOOD);

                HashMap<Map.RatingType, Integer> ratings = new HashMap<>();

                ratings.put(Map.RatingType.VERY_BAD, veryBadRatings);
                ratings.put(Map.RatingType.BAD, badRatings);
                ratings.put(Map.RatingType.NEUTRAL, neutralRatings);
                ratings.put(Map.RatingType.GOOD, goodRatings);
                ratings.put(Map.RatingType.VERY_GOOD, veryGoodRatings);

                Map map = new Map(id, name, itemMat, ratings, null);
                return map;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    public ArrayList<Map> getRandomMaps() {
        String stmt = "SELECT * FROM " + TABLE_NAME + " ORDER BY RAND() LIMIT 3";

        ResultSet rs = sql.getResult(stmt, null);

        ArrayList<Map> maps = new ArrayList<>();

        try {
            while (rs.next()){
                int id = rs.getInt(ATTRIBUTE_ID);
                Map map = (Map) get(id);
                maps.add(map);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return maps;
    }

    public boolean existsName(String name) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_NAME, name);
    }

    public boolean existsID(int id) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_ID, id);
    }

    public int createID() {
        int id = new Random().nextInt(999);
        if(existsID(id)) {
            return createID();
        }
        return id;
    }
}
