package de.z1up.ttt.mysql;

// imports
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SQLConfig {


    // variables
    private String path;
    private String file;


    // constructor
    public SQLConfig(String path, String file) {
        this.path = path;
        this.file = file;
        this.setStandard();
    }


    // methods

    /**
     *  set the default config
     */
    public void setStandard() {
        FileConfiguration cfg = getFileConfiguration();
        cfg.options().copyDefaults(true);

        cfg.addDefault("host", "localhost");
        cfg.addDefault("port", "3306");
        cfg.addDefault("database", "database");
        cfg.addDefault("username", "username");
        cfg.addDefault("password", "password");

        try {
            cfg.save(this.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the actual file
     * @return
     */
    private File getFile() {
        return new File(this.path, this.file);
    }

    /**
     * get cfg
     * @return
     */
    private FileConfiguration getFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    /**
     * read data from cfg file
     * @return
     */
    public HashMap<String, String> readData() {

        FileConfiguration cfg = getFileConfiguration();

        HashMap<String, String> config = new HashMap<>();
        config.put("host", cfg.getString("host"));
        config.put("port", cfg.getString("port"));
        config.put("database", cfg.getString("database"));
        config.put("username", cfg.getString("username"));
        config.put("password", cfg.getString("password"));

        return config;
    }
}