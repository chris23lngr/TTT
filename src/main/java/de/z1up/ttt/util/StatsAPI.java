package de.z1up.ttt.util;

import de.z1up.ttt.TTT;
import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.entity.Player;

public class StatsAPI {

    private SQL sql;

    public StatsAPI(SQL sql) {
        this.sql = sql;
    }

    public void onTeamKill(Player killer) {
        TTTPlayer tttKiller = TTT.core.getPlayerManager().getTTTPlayer(killer);
        tttKiller.removeKarma(20);
        tttKiller.update();
    }

    public void onOponentKill(Player killer, Player target) {

        TTTPlayer tttKiller = TTT.core.getPlayerManager().getTTTPlayer(killer);
        tttKiller.addKarma(20);
        tttKiller.addKill();
        tttKiller.update();

        TTTPlayer tttTarget = TTT.core.getPlayerManager().getTTTPlayer(target);
        tttTarget.addDeath();
        tttTarget.update();

    }

}
