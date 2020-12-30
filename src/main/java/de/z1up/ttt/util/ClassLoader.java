package de.z1up.ttt.util;

import de.z1up.ttt.command.CommandAddMap;
import de.z1up.ttt.command.CommandBuild;
import de.z1up.ttt.listener.*;

public class ClassLoader {

    public void load() {
        registerListener();
        registerCommands();
    }

    private void registerListener() {
        new ListenerBlockPlace();
        new ListenerBreakBlock();
        new ListenerEntityDamage();
        new ListenerEntityDamageByEntity();
        new ListenerFoodLevelChange();
        new ListenerInventoryClick();
        new ListenerPlayerChat();
        new ListenerPlayerDropItem();
        new ListenerPlayerJoin();
        new ListenerPlayerLogin();
        new ListenerThunderChange();
        new ListenerWeatherChange();
    }

    private void registerCommands() {
        new CommandAddMap();
        new CommandBuild();
    }

}
