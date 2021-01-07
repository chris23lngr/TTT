package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ListenerBlockPlace implements Listener {

    public ListenerBlockPlace() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final BlockPlaceEvent event) {

        final Player player = event.getPlayer();

        if(TTT.core.getBuildManager().canBuild(player)) {
            event.setCancelled(false);
            event.setBuild(true);
            return;
        }

        if(TTT.core.getGameManager().inGame()) {

            Block block = event.getBlock();

            if (!block.getType().equals(Material.BEACON)
                    && !block.getType().equals(Material.TRAPPED_CHEST)) {
                event.setCancelled(true);
                event.setBuild(false);
                return;
            }

            event.setCancelled(false);
            event.setBuild(true);

            if (block.getType().equals(Material.BEACON)) {
                // Healstation.onHeal(e.getBlock());
            }

            return;
        }

        event.setCancelled(true);
        event.setBuild(false);

    }
}
