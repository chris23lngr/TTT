package de.z1up.ttt.manager;

import de.z1up.ttt.interfaces.Manager;

public class ManagerTeam implements Manager {

    public ManagerTeam() {
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {

    }
}
