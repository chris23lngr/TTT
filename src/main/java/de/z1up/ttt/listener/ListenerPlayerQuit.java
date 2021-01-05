package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
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

        event.setQuitMessage(Data.getPrefix() + "§c" + player.getName() + " §7hat das Spiel verlassen.");

        if(Data.voteManager.hasVoted(player)) {
            if(Data.voteManager.isVotePeriodActive()) {
                Data.voteManager.unvote(player);
            }
        }

    }

}
