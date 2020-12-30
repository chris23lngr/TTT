package de.z1up.ttt;

import de.z1up.ttt.util.Data;
import org.bukkit.plugin.java.JavaPlugin;

public class TTT extends JavaPlugin {

    private static TTT instance;

    Data data;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    void init() {
        instance = this;
        data = new Data();
        data.init();
    }

    public static TTT getInstance() {
        return instance;
    }
}
