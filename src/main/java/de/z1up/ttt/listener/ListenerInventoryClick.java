package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ListenerInventoryClick implements Listener {

    public ListenerInventoryClick() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final InventoryClickEvent event) {

        if(!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player whoClicked = (Player) event.getWhoClicked();

        if(Data.buildManager.canBuild(whoClicked)) {
            event.setCancelled(false);
            return;
        }

        if (Data.playerManager.isSpec(whoClicked)) {
            event.setCancelled(true);
            return;
        }

        if(Data.gameManager.inGame()) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);

    }

}
