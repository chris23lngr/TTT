package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import net.minecraft.server.v1_8_R3.EnumItemRarity;
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

        if(TTT.core.getPlayerManager().isSpec(player)) {
            event.setQuitMessage(null);
            return;
        }

        String quit = Messages.PREFIX + "§c" + player.getName()
                + " §7hat das Spiel verlassen.";
        event.setQuitMessage(quit);

        if(TTT.core.getVoteManager().hasVoted(player)) {
            if(TTT.core.getVoteManager().isVotePeriodActive()) {
                TTT.core.getVoteManager().unvote(player);
            }
        }

    }

}
