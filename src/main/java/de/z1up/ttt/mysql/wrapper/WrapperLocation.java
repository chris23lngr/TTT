package de.z1up.ttt.mysql.wrapper;

import de.z1up.ttt.TTT;
import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class WrapperLocation {

    private SQL sql;

    private final String TABLE_NAME = "locations";
    private final String ATTRIBUTE_NAME = "NAME";
    private final String ATTRIBUTE_WORLD_NAME = "WORLD_NAME";
    private final String ATTRIBUTE_X = "X";
    private final String ATTRIBUTE_Y = "Y";
    private final String ATTRIBUTE_Z = "Z";
    private final String ATTRIBUTE_YAW = "YAW";
    private final String ATTRIBUTE_PITCH = "PITCH";

    public WrapperLocation(SQL sql) {
        this.sql = sql;
        createTable();
    }

    public void createTable() {
        String stmt = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ATTRIBUTE_NAME + " varchar(256), " + ATTRIBUTE_WORLD_NAME + " varchar(256), "
                + ATTRIBUTE_X + " double, " + ATTRIBUTE_Y + " double, " + ATTRIBUTE_Z + " double, " + ATTRIBUTE_YAW + " float, " + ATTRIBUTE_PITCH + " float);";
        sql.executeUpdateAsync(stmt, null);
    }


    public void insert(String name, Location location) {

        String stmt = "INSERT INTO " + TABLE_NAME + "(" + ATTRIBUTE_NAME + ", " + ATTRIBUTE_WORLD_NAME + ", " + ATTRIBUTE_X + ", " + ATTRIBUTE_Y + ", " + ATTRIBUTE_Z + ", " + ATTRIBUTE_YAW + ", " + ATTRIBUTE_PITCH + ") VALUES (?, ?, ?, ?, ?, ?, ?);";

        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        sql.executeUpdateAsync(stmt, Arrays.asList(name, worldName, x, y, z, yaw, pitch));

    }

    public void update(String name, Location location) {


        String stmt = "UPDATE " + TABLE_NAME + " SET " + ATTRIBUTE_WORLD_NAME + "=?, " + ATTRIBUTE_X + "=?, " + ATTRIBUTE_Y + "=?, " + ATTRIBUTE_Z + "=?, " + ATTRIBUTE_YAW + "=?, " + ATTRIBUTE_PITCH + "=? WHERE " + ATTRIBUTE_NAME + "=?;";


        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getYaw();

        sql.executeUpdateAsync(stmt, Arrays.asList(worldName, x, y, z, yaw, pitch, name));

    }

    public void delete(String name) {

        String stmt = "DELETE * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_NAME + "=?;";
        sql.executeUpdateAsync(stmt, Arrays.asList(name));

    }

    public Location get(String name) {

        String stmt = "SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_NAME + "=?;";

        ResultSet rs = sql.getResult(stmt, Arrays.asList(name));

        try {
            if(rs.next()) {

                String worldName = rs.getString(ATTRIBUTE_WORLD_NAME);
                double x = rs.getDouble(ATTRIBUTE_X);
                double y = rs.getDouble(ATTRIBUTE_Y);
                double z = rs.getDouble(ATTRIBUTE_Z);
                float yaw = rs.getFloat(ATTRIBUTE_YAW);
                float pitch = rs.getFloat(ATTRIBUTE_PITCH);

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
                return location;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    public boolean exists(String name) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_NAME, name);
    }
}
