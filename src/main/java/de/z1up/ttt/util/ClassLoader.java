package de.z1up.ttt.util;

import de.z1up.ttt.command.CommandAddMap;
import de.z1up.ttt.command.CommandBuild;
import de.z1up.ttt.command.CommandSetSpawn;
import de.z1up.ttt.command.CommandStart;
import de.z1up.ttt.listener.*;

public class ClassLoader {

    public void load() {
        registerListener();
        registerCommands();
    }

    private void registerListener() {
        new ListenerBlockPlace();
        new ListenerBreakBlock();
        new ListenerCountdownFinish();
        new ListenerCountdownTimeChange();
        new ListenerEntityDamage();
        new ListenerEntityDamageByEntity();
        new ListenerFoodLevelChange();
        new ListenerInventoryClick();
        new ListenerMapSet();
        new ListenerPlayerAchieveAchievement();
        new ListenerPlayerChat();
        new ListenerPlayerDropItem();
        new ListenerPlayerInteract();
        new ListenerPlayerJoin();
        new ListenerPlayerLogin();
        new ListenerPlayerQuit();
        new ListenerServer();
        new ListenerThunderChange();
        new ListenerWeatherChange();
    }

    private void registerCommands() {
        new CommandAddMap();
        new CommandBuild();
        new CommandStart();
        new CommandSetSpawn();
    }

}
