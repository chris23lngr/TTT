package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.o.Map;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class VoteManager implements Manager {

    private HashMap<Player, Map> votes;
    private boolean votePeriodActive;

    public VoteManager() {
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
        votes = new HashMap<>();
        votePeriodActive = false;
    }

    public void vote(Player player, Map voted) {
        votes.put(player, voted);
        for(Map map : TTT.core.getMapManager().getMaps()) {
            if (map.getName().equals(voted.getName())) {
                map.addVote();
            }
        }
    }

    public void unvote(Player player) {
        Map voted = votes.get(player);
        votes.remove(player);
        for(Map map : TTT.core.getMapManager().getMaps()) {
            if (map.getName().equals(voted.getName())) {
                map.removeVote();
            }
        }
    }

    public boolean hasVoted(Player player) {
        return votes.containsKey(player);
    }

    public void setVotePeriodActive(boolean b) {
        this.votePeriodActive = b;
    }

    public boolean isVotePeriodActive() {
        return votePeriodActive;
    }
}
