package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ListenerEntityDamageByEntity implements Listener {

    public ListenerEntityDamageByEntity() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final EntityDamageByEntityEvent event) {

        if(Data.gameManager.inGame()) {

            event.setCancelled(false);



        } else {
            event.setCancelled(true);
        }

    }
}
