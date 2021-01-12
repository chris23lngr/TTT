package de.z1up.ttt.core;

import de.z1up.ttt.command.*;
import de.z1up.ttt.listener.*;
import de.z1up.ttt.listener.custom.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * The ClassLoader class is there to register the event listeners 
 * and commands in a single place. To do this, a new instance of
 * the ClassLoader must be created from which the load method can be
 * called, which then calls the two sub-methods call and run.
 * 
 * @author chris23lngr
 * @since 1.0
 */
public class ClassLoader {

    /**
     * The load method calls the two methods
     * {@code registerListener()} and {@code registerCommands()},
     * which register the commands and listeners.
     */
    public void load() {
        registerListener();
        registerCommands();
    }

    /**
     * In the {@code registerListener()} method, the constructors of the event
     * listeners are called. The respective listener is registered in
     * the individual constructors via the Bukkit plugin manager. The
     * TTT class is specified as the plugin parameter.
     */
    private void registerListener() {
        new ListenerBlockPlace();
        new ListenerBreakBlock();

        new ListenerTimerStart();
        new ListenerTimerFinish();
        new ListenerTimerTimeChange();

        new ListenerEntityDamage();
        new ListenerEntityDamageByEntity();

        new ListenerFoodLevelChange();
        new ListenerGameStateChange();

        new ListenerInventoryClick();
        new ListenerMapSet();

        new ListenerPlayerAchieveAchievement();
        new ListenerPlayerChat();

        new ListenerPlayerDropItem();
        new ListenerPlayerInteract();

        new ListenerPlayerJoin();
        new ListenerPlayerLogin();

        new ListenerPlayerQuit();
        new ListenerPluginDisable();

        new ListenerThunderChange();
        new ListenerWeatherChange();

        new ListenerPlayerMove();
        new ListenerPlayerTeamSet();

        new ListenerPlayerInteractAtEntity();
        new ListenerPlayerDeath();
    }

    /**
     * With this method, the commands are registered. Only
     * one constructor needs to be called, as the predefined
     * constructors of the individual commands specify how
     * they have to be registered.
     */
    private void registerCommands() {

        new CommandAddMap();
        new CommandBuild();

        new CommandForceMap();
        new CommandMap();

        new CommandRemoveSpawn();
        new CommandSetSpawn();

        new CommandShop();
        new CommandStart();

        new CommandStats();
    }

}
