package de.z1up.ttt.core;

import de.z1up.ttt.command.*;
import de.z1up.ttt.listener.*;
import de.z1up.ttt.listener.custom.*;
import de.z1up.ttt.util.Messages;
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
        new ListenerGameEnd();
        new ListenerGameStateChange();
        new ListenerMapSet();
        new ListenerPlayerAchieveAchievement();
        new ListenerPlayerBecomeSpec();
        new ListenerPlayerKillEnemy();
        new ListenerPlayerKillMate();
        new ListenerPlayerOpenChest();
        new ListenerPlayerTeamSet();
        new ListenerReplay();
        new ListenerTimerFinish();
        new ListenerTimerStart();
        new ListenerTimerTimeChange();

        new ListenerBlockPlace();
        new ListenerBlockBreak();
        new ListenerEntityDamage();
        new ListenerEntityDamageByEntity();
        new ListenerFoodLevelChange();
        new ListenerInventoryClick();
        new ListenerPlayerChat();
        new ListenerPlayerDeath();
        new ListenerPlayerDropItem();
        new ListenerPlayerInteract();
        new ListenerPlayerInteractAtEntity();
        new ListenerPlayerJoin();
        new ListenerPlayerLogin();
        new ListenerPlayerMove();
        new ListenerPlayerPickUpItem();
        new ListenerPlayerQuit();
        new ListenerPlayerRespawn();
        new ListenerPluginDisable();
        new ListenerThunderChange();
        new ListenerWeatherChange();
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
        new CommandSetDefaultSpawn();
    }

}
