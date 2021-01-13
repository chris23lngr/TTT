package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ListenerPlayerDropItem implements Listener {

    public ListenerPlayerDropItem() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerDropItemEvent event) {

        final Player player = event.getPlayer();
        final ItemStack droppedItemStack = event.getItemDrop().getItemStack();

        if(TTT.core.getPlayerManager().isSpec(player)) {
            event.setCancelled(true);
            return;
        }

        if(TTT.core.getGameManager().inGame()) {

            if(itemCanntBeDropped(droppedItemStack)) {
                event.setCancelled(true);
                return;
            }

            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);

    }

    boolean itemCanntBeDropped(ItemStack itemStack) {

        if(itemStack.getItemMeta() == null) {
            return false;
        }

        if(itemStack.getItemMeta().getDisplayName() == null) {
            return false;
        }

        ArrayList<String> forbiddenItemNames = new ArrayList<String>();
        forbiddenItemNames.add(Messages.ItemNames.IDENTIFIER);
        forbiddenItemNames.add(Messages.ItemNames.SHOP);

        for (String forbiddenItemName : forbiddenItemNames) {
            if(itemStack.getItemMeta().getDisplayName().equals(forbiddenItemName)) {
                return true;
            }
        }
        return false;
    }
}
