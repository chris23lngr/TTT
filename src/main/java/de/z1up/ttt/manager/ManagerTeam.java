package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.util.MathUtils;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;

public class ManagerTeam implements Manager {

    private int traitorCounter;
    private int traitorsNeeded;

    private int detectiveCounter;
    private int detectivesNeeded;

    private int innocentCounter;
    private int innocentsNeeded;

    public ManagerTeam() {
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
        traitorCounter = 0;
        detectiveCounter = 0;
        innocentCounter = 0;
        calcTeamSizes();
    }

    public void calcTeamSizes() {

        int aliveSize = Bukkit.getMaxPlayers();

        if(aliveSize <= 6) {
            detectivesNeeded = 1;
        } else {
            detectivesNeeded = 2;
        }

        double d = aliveSize / 2;

        if(MathUtils.isPointValue(d)) {
            int after = MathUtils.getAfter(d);

            if(after > 6) {
                d = d + (1 - after);
            } else {
                d = d - after;
            }

        }

        traitorsNeeded = (int) d;

        innocentsNeeded = aliveSize - detectivesNeeded - traitorsNeeded;

        // Debug
        Bukkit.getConsoleSender().sendMessage("§4T: " + traitorsNeeded);
        Bukkit.getConsoleSender().sendMessage("§1D: " + detectivesNeeded);
        Bukkit.getConsoleSender().sendMessage("§aI: " + innocentsNeeded);


    }


    public enum Team {
        TRAITOR, DETECTIVE, INNOCENT
    }

    public void selectTeam(TTTPlayer player) {

        if(player.hasTeam()) {
            return;
        }

        if(detectiveCounter < detectivesNeeded) {
            player.setTeam(Team.DETECTIVE);
            detectiveCounter++;
            return;
        }

        if(traitorCounter < traitorsNeeded) {
            player.setTeam(Team.TRAITOR);
            traitorCounter++;
            return;
        }

        if(innocentCounter < innocentsNeeded) {
            player.setTeam(Team.INNOCENT);
            innocentCounter++;
            return;
        }

    }

    public int getDetectiveCounter() {
        return detectiveCounter;
    }

    public int getInnocentCounter() {
        return innocentCounter;
    }

    public int getTraitorCounter() {
        return traitorCounter;
    }

    public void removeDetective() {
        this.detectiveCounter = detectiveCounter - 1;
    }

    public void removeInnocent() {
        this.innocentCounter = innocentCounter - 1;
    }

    public void removeTraitor() {
        this.traitorCounter = traitorCounter - 1;
    }

    public void addDetective() {
        this.detectiveCounter = detectiveCounter + 1;
    }

    public void addInnocent() {
        this.innocentCounter = innocentCounter + 1;
    }
    public void addTraitor() {
        this.traitorCounter = traitorCounter + 1;
    }

    public int getDetectivesNeeded() {
        return detectivesNeeded;
    }

    public int getInnocentsNeeded() {
        return innocentsNeeded;
    }

    public int getTraitorsNeeded() {
        return traitorsNeeded;
    }
}
