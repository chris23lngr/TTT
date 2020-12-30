package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ListenerWeatherChange implements Listener {

    public ListenerWeatherChange() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final WeatherChangeEvent event) {
        Boolean rain = event.toWeatherState();
        if (rain) {
            event.setCancelled(true);
            event.getWorld().setStorm(false);
        }
    }
}
