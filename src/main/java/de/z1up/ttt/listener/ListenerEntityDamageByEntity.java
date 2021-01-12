package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ListenerEntityDamageByEntity implements Listener {

    public ListenerEntityDamageByEntity() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final EntityDamageByEntityEvent event) {

        if(event.getEntity() instanceof Zombie) {
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
