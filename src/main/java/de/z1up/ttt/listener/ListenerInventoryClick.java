package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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

        if(TTT.core.getBuildManager().canBuild(whoClicked)) {
            event.setCancelled(false);
            return;
        }

        if (TTT.core.getPlayerManager().isSpec(whoClicked)) {
            event.setCancelled(true);
            return;
        }

        if(TTT.core.getGameManager().inGame()) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);

        if(this.contextInvalid(event)) {
            return;
        }

        if(event.getClickedInventory().getTitle().equals("§bMapvoting")) {
            this.onMapClick(event);
        }

    }

    private boolean contextInvalid(final InventoryClickEvent context) {
        if(context.getSlot() == -1 ) {
            return true;
        }
        if(context.getSlotType() == null) {
            return true;
        }
        if(context.getInventory() == null) {
            return true;
        }
        if(context.getCurrentItem() == null) {
            return true;
        }
        if(context.getClickedInventory() == null) {
            return true;
        }
        if(!(context.getWhoClicked() instanceof Player)) {
            return true;
        }
        if(context.getCurrentItem().getItemMeta() == null) {
            return true;
        }
        if(context.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return true;
        }
        if(context.getCurrentItem().getItemMeta().getDisplayName().equals(" ")) {
            return true;
        }
        return false;
    }

    private void onMapClick(final InventoryClickEvent context) {

        ItemStack itemStack = context.getCurrentItem();
        String display = itemStack.getItemMeta().getDisplayName();

        String mapName = display.replaceAll("§a", "");

        if(!TTT.core.getMapManager().existsName(mapName)) {
            return;
        }

        Player player = (Player) context.getWhoClicked();
        Map map = TTT.core.getMapManager().getMap(mapName);

        if(TTT.core.getVoteManager().isVotePeriodActive()) {

            // Check if the player has voted already
            if(TTT.core.getVoteManager().hasVoted(player)) {
                // If so, unvote from old map
                TTT.core.getVoteManager().unvote(player);
            }

            // Vote for new map and send success message
            TTT.core.getVoteManager().vote(player, map);
            player.sendMessage(Messages.MAPS_YOU_VOTED_FOR + map.getName());
            player.closeInventory();
        }


    }

}
