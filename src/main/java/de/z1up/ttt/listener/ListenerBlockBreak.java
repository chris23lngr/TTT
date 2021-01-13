package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ListenerBlockBreak implements Listener {

    public ListenerBlockBreak() {
        Bukkit.getPluginManager().registerEvents(this,
                TTT.getInstance());
    }

    @EventHandler
    public void onCall(final BlockBreakEvent event) {

        final Player player = event.getPlayer();

        if(TTT.core.getBuildManager().canBuild(player)) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }

        TTT.core.getMapResetter().onMapBlockBreak(event);

    }

}
