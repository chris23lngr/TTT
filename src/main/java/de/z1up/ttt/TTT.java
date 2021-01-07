package de.z1up.ttt;

import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The TTT class is the main class of the plug-in. It is
 * used to access the essential core. The onLoad(), onEnable()
 * and onDisable() methods are also called here.
 *
 * @author chris23lngr
 * @since 1.0
 * @see org.bukkit.plugin.java.JavaPlugin
 * @see de.z1up.ttt.core.Core
 */
public class TTT extends JavaPlugin {

    /**
     * An instance of the TTT class which is usually used to
     * return the current plugin. For example: The method
     * {@code registerEvents()} of the PluginManager class
     * sets a plugin as an attribute, to which the respective
     * listener is assigned.
     */
    private static TTT instance;

    /**
     * The static {@code core} is used to access the core class.
     * This then refers to the managers and wrappers. The MySQL
     * connection is also accessed via the core.
     */
    public static Core core;


    /**
     * The {@code onLoad()} method is called, when the plugin is
     * being loaded.
     *
     * @see org.bukkit.plugin.java.JavaPlugin
     */
    @Override
    public void onLoad() {
        super.onLoad();
    }

    /**
     * The {@code onEnable()} method is called, when the plugin is
     * being enabled. This is where the {@code init()} and the
     * {@code load()} method are called.
     *
     * @see org.bukkit.plugin.java.JavaPlugin
     */
    @Override
    public void onEnable() {
        super.onEnable();
        init();
        load();
    }

    /**
     * The {@code onLoad()} method is called, when the plugin is
     * being disabled.
     *
     * @see org.bukkit.plugin.java.JavaPlugin
     */
    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * The {@code init()} method initialises the two class attributes
     * {@code instance} and {@code core}. A new, suitable instance
     * is created for each. For the instance attribute, this is this
     * TTT class. For the core attribute, it is a new instance of the
     * core class.
     */
    void init() {
        if(instance == null) {
            instance = this;
        }
        if(core == null) {
            core = new Core();
        }
    }

    /**
     * The load() method loads the core using the Core.load() method.
     * If the core does not exit, the init() method is executed again
     * to ensure that the plugin is running correctly. If the core is
     * still not initialised, the server is stopped to avoid further
     * errors.
     */
    void load() {
        if(core != null) {
            core.load();
        } else {

            init();

            // Check again if the core exists, if not so,
            // shut server down. Plugin won't be able to
            // work properly.
            if(core == null) {
                String err = "§4FATAL ERROR: Core cannot be loaded. "
                        + "Please contact developer!";
                Bukkit.getConsoleSender().sendMessage(err);
                Bukkit.shutdown();
                return;
            }

            // Load the core again
            core.load();
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
