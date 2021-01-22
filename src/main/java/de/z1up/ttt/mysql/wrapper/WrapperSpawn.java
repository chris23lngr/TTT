package de.z1up.ttt.mysql.wrapper;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Wrapper;
import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WrapperSpawn implements Wrapper {

    private final String TABLE_NAME = "spawns";

    private final String ATTRIBUTE_ID = "ID";
    private final String ATTRIBUTE_MAP = "MAP_ID";
    private final String ATTRIBUTE_WORLD_NAME = "WORLD";
    private final String ATTRIBUTE_X = "X";
    private final String ATTRIBUTE_Y = "Y";
    private final String ATTRIBUTE_Z = "Z";
    private final String ATTRIBUTE_YAW = "YAW";
    private final String ATTRIBUTE_PITCH = "PITCH";

    private SQL sql;

    public WrapperSpawn(SQL sql) {
        this.sql = sql;
        createTable();
    }

    @Override
    public void createTable() {
        String stmt = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ATTRIBUTE_ID + " int, " + ATTRIBUTE_MAP + " int, " + ATTRIBUTE_WORLD_NAME + " varchar(256), "
                + ATTRIBUTE_X + " double, " + ATTRIBUTE_Y + " double, " + ATTRIBUTE_Z + " double, " + ATTRIBUTE_YAW + " float, " + ATTRIBUTE_PITCH + " float);";
        sql.executeUpdateAsync(stmt, null);
    }

    @Override
    public void insert(Object e) {

        if(!(e instanceof Spawn)) {
            return;
        }

        String stmt = "INSERT INTO " + TABLE_NAME + "(" + ATTRIBUTE_ID + ", " + ATTRIBUTE_MAP + ", " + ATTRIBUTE_WORLD_NAME + ", " + ATTRIBUTE_X + ", " + ATTRIBUTE_Y + ", " + ATTRIBUTE_Z + ", " + ATTRIBUTE_YAW + ", " + ATTRIBUTE_PITCH + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        Spawn spawn = (Spawn) e;

        int id = spawn.getId();
        int mapID = 0;
        if(spawn.getMap() != null) {
            mapID = spawn.getMap().getId();
        }
        String worldName = spawn.getLocation().getWorld().getName();
        double x = spawn.getLocation().getX();
        double y = spawn.getLocation().getY();
        double z = spawn.getLocation().getZ();
        float yaw = spawn.getLocation().getYaw();
        float pitch = spawn.getLocation().getPitch();

        sql.executeUpdateAsync(stmt, Arrays.asList(id, mapID, worldName, x, y, z, yaw, pitch));

    }

    @Override
    public void update(Object e) {

        if(!(e instanceof Spawn)) {
            return;
        }

        String stmt = "UPDATE " + TABLE_NAME + " SET " + ATTRIBUTE_MAP + "=?, " + ATTRIBUTE_WORLD_NAME + "=?, " + ATTRIBUTE_X + "=?, " + ATTRIBUTE_Y + "=?, " + ATTRIBUTE_Z + "=?, " + ATTRIBUTE_YAW + "=?, " + ATTRIBUTE_PITCH + "=? WHERE " + ATTRIBUTE_ID + "=?;";

        Spawn spawn = (Spawn) e;

        int id = spawn.getId();
        int mapId = spawn.getMap().getId();
        String worldName = spawn.getLocation().getWorld().getName();
        double x = spawn.getLocation().getX();
        double y = spawn.getLocation().getY();
        double z = spawn.getLocation().getZ();
        float yaw = spawn.getLocation().getYaw();
        float pitch = spawn.getLocation().getYaw();

        sql.executeUpdateAsync(stmt, Arrays.asList(mapId, worldName, x, y, z, yaw, pitch, id));

    }

    @Override
    public void delete(Object e) {

        if(!(e instanceof Spawn)) {
            return;
        }

        Spawn spawn = (Spawn) e;

        String stmt = "DELETE * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_ID + "=?;";
        sql.executeUpdateAsync(stmt, Arrays.asList(spawn.getId()));

    }

    @Override
    public Object get(Object e) {

        if(!(e instanceof Integer)) {
            return true;
        }

        String stmt = "SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_ID + "=?;";

        ResultSet rs = sql.getResult(stmt, Arrays.asList((Integer) e));

        try {
            if(rs.next()) {

                int id = rs.getInt(ATTRIBUTE_ID);
                int mapId = rs.getInt(ATTRIBUTE_MAP);
                String worldName = rs.getString(ATTRIBUTE_WORLD_NAME);
                double x = rs.getDouble(ATTRIBUTE_X);
                double y = rs.getDouble(ATTRIBUTE_Y);
                double z = rs.getDouble(ATTRIBUTE_Z);
                float yaw = rs.getFloat(ATTRIBUTE_YAW);
                float pitch = rs.getFloat(ATTRIBUTE_PITCH);

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
                Map map = TTT.core.getMapManager().getMap(mapId);

                Spawn spawn = new Spawn(id, location, map);
                return spawn;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return null;
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

    public ArrayList<Spawn> getSpawnsFor(Map map) {

        ArrayList<Spawn> spawns = new ArrayList<>();
        int id = map.getId();

        String stmt = "SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_MAP + "=?;";
        ResultSet rs = sql.getResult(stmt, Arrays.asList(id));

        try {
            while (rs.next()) {
                Spawn spawn = (Spawn) get(rs.getInt(ATTRIBUTE_ID));
                System.out.println(spawn.getId());
                spawns.add(spawn);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return spawns;
    }

    public Spawn getSpecSpawnFor(Map map) {

        String stmt = "SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_ID + "=?;";

        ResultSet rs = sql.getResult(stmt, Arrays.asList(map.getId()));

        try {
            if(rs.next()) {

                int id = rs.getInt(ATTRIBUTE_ID);
                int mapId = rs.getInt(ATTRIBUTE_MAP);
                String worldName = rs.getString(ATTRIBUTE_WORLD_NAME);
                double x = rs.getDouble(ATTRIBUTE_X);
                double y = rs.getDouble(ATTRIBUTE_Y);
                double z = rs.getDouble(ATTRIBUTE_Z);
                float yaw = rs.getFloat(ATTRIBUTE_YAW);
                float pitch = rs.getFloat(ATTRIBUTE_PITCH);

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);

                Spawn spawn = new Spawn(id, location, map);
                return spawn;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return null;

    }

    public void insertSpecSpawn(Spawn spawn) {

        String stmt = "INSERT INTO " + TABLE_NAME + "(" + ATTRIBUTE_ID + ", " + ATTRIBUTE_MAP + ", " + ATTRIBUTE_WORLD_NAME + ", " + ATTRIBUTE_X + ", " + ATTRIBUTE_Y + ", " + ATTRIBUTE_Z + ", " + ATTRIBUTE_YAW + ", " + ATTRIBUTE_PITCH + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        int id = spawn.getId();
        int mapID = 0;
        if(spawn.getMap() != null) {
            mapID = spawn.getMap().getId();
        }
        String worldName = spawn.getLocation().getWorld().getName();
        double x = spawn.getLocation().getX();
        double y = spawn.getLocation().getY();
        double z = spawn.getLocation().getZ();
        float yaw = spawn.getLocation().getYaw();
        float pitch = spawn.getLocation().getPitch();

        sql.executeUpdateAsync(stmt, Arrays.asList(id, mapID, worldName, x, y, z, yaw, pitch));

    }

}
