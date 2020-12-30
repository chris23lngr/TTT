package de.z1up.ttt.listener;

import com.mysql.jdbc.Buffer;
import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ListenerBreakBlock implements Listener {

    public ListenerBreakBlock() {
        Bukkit.getPluginManager().registerEvents(this,
                TTT.getInstance());
    }

    @EventHandler
    public void onCall(final BlockBreakEvent event) {

        final Player player = event.getPlayer();

        if(Data.buildManager.canBuild(player)) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }

    }

}
