package de.z1up.ttt.manager;

import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.mysql.wrapper.WrapperSpawn;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class ManagerSpawn extends WrapperSpawn implements Manager {

    public ManagerSpawn() {
        super(Core.sql);
    }

    @Override
    public void load() {

    }

    @Override
    public void init() {
    }

    public Spawn getSpawn(int id) {

        if(!existsID(id)) {
            Bukkit.getConsoleSender().sendMessage(Messages.ID_NOT_FOUND_EXC);
            return null;
        }

        Spawn spawn = (Spawn) get(id);
        return spawn;
    }

    public ArrayList<Spawn> getSpawns(Map map) {
        return getSpawns(map.getId());
    }

    public ArrayList<Spawn> getSpawns(int id) {

        if(!existsID(id)) {
            Bukkit.getConsoleSender().sendMessage(Messages.ID_NOT_FOUND_EXC);
            return null;
        }
        return getSpawnsFor(Core.mapManager.getMap(id));
    }

}
