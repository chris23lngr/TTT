package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerQuit implements Listener {

    public ListenerPlayerQuit() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.setQuitMessage(Core.getPrefix() + "§c" + player.getName() + " §7hat das Spiel verlassen.");

        if(Core.voteManager.hasVoted(player)) {
            if(Core.voteManager.isVotePeriodActive()) {
                Core.voteManager.unvote(player);
            }
        }

    }

}
