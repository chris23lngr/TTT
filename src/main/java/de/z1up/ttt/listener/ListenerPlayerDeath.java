package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.DeadBody;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerPlayerDeath implements Listener {

    public ListenerPlayerDeath() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerDeathEvent event) {

        Player player = event.getEntity();

        event.setDeathMessage(null);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(TTT.getInstance(), 10);

        TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(player);

        DeadBody deadBody = null;

        if(player.getKiller() != null) {
            if(player.getKiller() instanceof Player) {
                deadBody = new DeadBody(player.getUniqueId(), tttPlayer.getTeam(), player.getKiller().getUniqueId(), player.getLocation(), false);
            } else {
                deadBody = new DeadBody(player.getUniqueId(), tttPlayer.getTeam(), null, player.getLocation(), false);
            }
        } else {
            deadBody = new DeadBody(player.getUniqueId(), tttPlayer.getTeam(), null, player.getLocation(), false);
        }

        deadBody.spawn();
        TTT.core.getPlayerManager().addDeadBody(deadBody);

        String deathsMsg = "";

        if(player.getKiller() != null) {
            deathsMsg = Messages.PREFIX + "§cDu wurdest von §4" + player.getKiller().getName() + " §cgetötet§8!";
        } else {
            deathsMsg = Messages.PREFIX + "§cDu bist gestorben§8!";
        }


    }

}
