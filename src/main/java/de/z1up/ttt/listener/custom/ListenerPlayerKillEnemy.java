package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerKillEnemyEvent;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerPlayerKillEnemy implements Listener {

    public ListenerPlayerKillEnemy() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerKillEnemyEvent event) {

        Player killer = event.getKiller();
        if(!TTT.core.getPlayerManager().existsPlayer(killer.getUniqueId())) {
            return;
        }
        TTTPlayer tttKiller = TTT.core.getPlayerManager().getTTTPlayer(killer);

        Player target = event.getTarget();
        if(!TTT.core.getPlayerManager().existsPlayer(target.getUniqueId())) {
            return;
        }
        TTTPlayer tttTarget = TTT.core.getPlayerManager().getTTTPlayer(target);

        tttKiller.addKill();
        tttKiller.addKarma(20);
        tttKiller.update();
        killer.sendMessage(Messages.PREFIX + "§aDu hast einen §e" + tttTarget.getTeam().toString() + " §agetötet!");
        killer.sendMessage(Messages.PREFIX + "§eKarma§8: §b+20");

        tttTarget.addDeath();
        tttTarget.update();

    }

}
