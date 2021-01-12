package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ListenerPlayerPickUpItem implements Listener {

    public ListenerPlayerPickUpItem() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerPickupItemEvent event) {

        Player player = event.getPlayer();

        if(TTT.core.getPlayerManager().isSpec(player)) {
            event.setCancelled(true);
            return;
        }

    }

}
