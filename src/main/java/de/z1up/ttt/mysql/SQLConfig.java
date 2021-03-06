package de.z1up.ttt.mysql;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * The SQLConfig class is there to read out
 * the access data to the SQL database from a
 * file that is stored in the DataFolder of the
 * plugin.
 *
 * @author chris23lngr
 * @since 1.0
 * @see de.z1up.ttt.mysql.SQL
 */
public class SQLConfig implements Configuration {

    private String fileName;

    private File file;
    private YamlConfiguration configuration;

    public SQLConfig(String fileName) {
        this.fileName = fileName;
        loadFile();
        loadConfig();
        setDefaults();
    }

    @Override
    public void loadFile() {

        if(!TTT.getInstance().getDataFolder().exists()) {
            TTT.getInstance().getDataFolder().mkdirs();
        }

        file = new File(TTT.getInstance().getDataFolder(), fileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getConsoleSender().sendMessage("§4Please enter your MySQL access data first, to use the plugin.");
                return;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void loadConfig() {
        if(file != null) {
            configuration = YamlConfiguration.loadConfiguration(file);
            save();
        } else {
            loadFile();
        }
    }

    @Override
    public void save() {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setDefaults() {

        configuration.options().copyHeader(true);
        configuration.options().header("Please enter your access data to the database below.");

        configuration.options().copyDefaults(true);
        configuration.addDefault("host", "localhost");
        configuration.addDefault("port", 3306);
        configuration.addDefault("database", "db");
        configuration.addDefault("username", "root");
        configuration.addDefault("password", "password");

        save();

    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public HashMap<String, Object> read() {

        HashMap<String, Object> data = new HashMap<>();

        /*
        data.put("host", configuration.getString("host"));
        data.put("port", configuration.getInt("port"));
        data.put("database", configuration.getString("database"));
        data.put("username", configuration.getString("username"));
        data.put("password", configuration.getString("password"));
         */


        data.put("host", "localhost");
        data.put("port", 3306);
        data.put("database", "ttt");
        data.put("username", "root");
        data.put("password", "#CWsgMc!strt999");
        //data.put("password", "MeinPasswort12345");

        return data;
    }
}