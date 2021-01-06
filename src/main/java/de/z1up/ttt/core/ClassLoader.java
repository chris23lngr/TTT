package de.z1up.ttt.core;

import de.z1up.ttt.command.*;
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
        new ListenerServer();

        new ListenerThunderChange();
        new ListenerWeatherChange();
    }

    private void registerCommands() {
        new CommandAddMap();
        new CommandBuild();

        new CommandStart();
        new CommandSetSpawn();

        new CommandForceMap();
        new CommandRemoveSpawn();

        new CommandMap();
    }

}
