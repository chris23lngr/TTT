package de.z1up.ttt;

import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TTT extends JavaPlugin {

    /**
     * An instance of the TTT class which is usually used to
     * return the current plugin. For example: The method
     * {@code registerEvents()} of the PluginManager class
     * sets a plugin as an attribute, to which the respective
     * listener is assigned.
     */
    private static TTT instance;

    public static Core core;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        init();
        load();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    void init() {
        instance = this;
        core = new Core();
    }

    void load() {
        if(core != null) {
            core.load();
        } else {

            init();

            if(core == null) {
                String err = "§4FATAL ERROR: Core cannot be loaded. Please contact developer!";
                Bukkit.getConsoleSender().sendMessage(err);
                Bukkit.getConsoleSender().sendMessage(err);
                Bukkit.getConsoleSender().sendMessage(err);
                Bukkit.shutdown();
            }

        }
    }

    /**
     * Returns the given instant of the TTT class.
     * @return The given class attribute {@code instance}
     */
    public static TTT getInstance() {
        return instance;
    }
}
