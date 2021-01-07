package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerEntityDamage implements Listener {

    public ListenerEntityDamage() {
        Bukkit.getPluginManager().registerEvents(this,
                TTT.getInstance());
    }

    @EventHandler
    public void onCall(final EntityDamageEvent event) {

        if(!(event.getEntity() instanceof Player)) {
            event.setCancelled(true);
            return;
        }

        if(TTT.core.getGameManager().inGame()) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }

    }

}
