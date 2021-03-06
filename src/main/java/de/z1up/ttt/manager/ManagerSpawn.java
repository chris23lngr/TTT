package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.mysql.wrapper.WrapperSpawn;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

public class ManagerSpawn extends WrapperSpawn implements Manager {

    public ManagerSpawn() {
        super(Core.sql);
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
    }

    public Spawn getSpawn(int id) {

        if(!existsID(id)) {
            Bukkit.getConsoleSender().sendMessage(Messages.ErrorMessages.ID_NOT_FOUND_EXC);
            return null;
        }

        Spawn spawn = (Spawn) get(id);
        return spawn;
    }

    public ArrayList<Spawn> getSpawns(Map map) {
        return getSpawns(map.getId());
    }

    public ArrayList<Spawn> getSpawns(int id) {

        if(!TTT.core.getMapManager().existsID(id)) {
            Bukkit.getConsoleSender().sendMessage(Messages.ErrorMessages.ID_NOT_FOUND_EXC);
            return null;
        }
        return getSpawnsFor(TTT.core.getMapManager().getMap(id));
    }

}
