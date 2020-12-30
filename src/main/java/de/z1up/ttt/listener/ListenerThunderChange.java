package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;

public class ListenerThunderChange implements Listener {

    public ListenerThunderChange() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final ThunderChangeEvent event) {
        Boolean thunder = event.toThunderState();
        if (thunder) {
            event.setCancelled(true);
            event.getWorld().setThundering(false);
        }
    }

}
