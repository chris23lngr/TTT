package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class ListenerInventoryClick implements Listener {

    public ListenerInventoryClick() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final InventoryClickEvent event) {


        System.out.println("VERION 01");

        if(!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player whoClicked = (Player) event.getWhoClicked();

        if(TTT.core.getBuildManager().canBuild(whoClicked)) {
            event.setCancelled(false);
            return;
        }

        if(TTT.core.getGameManager().inGame()) {

            if(!this.contextInvalid(event)) {

                ItemStack itemStack = event.getCurrentItem();

                if(itemStack.getType() == Material.LEATHER_CHESTPLATE) {
                    event.setCancelled(true);
                    return;
                }

                if(itemStack.getType() == Material.STICK) {
                    event.setCancelled(true);
                    return;
                }

                if(itemStack.getType() == Material.EMERALD) {
                    event.setCancelled(true);
                    return;
                }

            }

        }

        if (TTT.core.getPlayerManager().isSpec(whoClicked)) {
            event.setCancelled(true);
        }

        if(this.contextInvalid(event)) {
            return;
        }

        if(TTT.core.getGameManager().inLobby()) {
            event.setCancelled(true);
        }

        if(event.getClickedInventory().getTitle().equals(Messages.ItemNames.NAVIGATOR)) {
            this.onNavigatorClick(event);
            return;
        }

        if(event.getClickedInventory().getTitle().equals(Messages.ItemNames.MAP_VOTING)) {
            this.onMapClick(event);
            return;
        }

        if(event.getClickedInventory().getTitle().equals(Messages.ItemNames.SETTINGS)) {
            this.onSettingsClick(event);
            return;
        }

        if(event.getClickedInventory().getTitle().equals(Messages.ItemNames.TICKETS)) {
            this.onTicketsClick(event);
            return;
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
        if(context.getCurrentItem().getItemMeta().getDisplayName().equals("")) {
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
            if (TTT.core.getVoteManager().hasVoted(player)) {
                // If so, unvote from old map
                TTT.core.getVoteManager().unvote(player);
            }

            // Vote for new map and send success message
            TTT.core.getVoteManager().vote(player, map);
            player.sendMessage(Messages.MAPS_YOU_VOTED_FOR + map.getName());
            player.closeInventory();
        }

    }

    private void onNavigatorClick(final InventoryClickEvent context) {

        ItemStack itemStack = context.getCurrentItem();
        String display = itemStack.getItemMeta().getDisplayName();

        String targetName = display.replaceAll("§e", "");

        if(Bukkit.getPlayer(targetName) == null) {
            context.getWhoClicked().closeInventory();
            return;
        }

        Player target = Bukkit.getPlayer(targetName);

        context.getWhoClicked().teleport(target.getLocation());
        context.getWhoClicked().closeInventory();

    }

    private void onSettingsClick(final InventoryClickEvent context) {

        ItemStack clicked = context.getCurrentItem();

        if(clicked.getType() == Material.REDSTONE_COMPARATOR) {

            context.getWhoClicked().closeInventory();


        } else if(clicked.getType() == Material.DIAMOND) {

            context.getWhoClicked().closeInventory();

            if(context.getWhoClicked() instanceof Player) {
                Player player = (Player) context.getWhoClicked();
                player.chat("/forcestart");
            }

        } else if(clicked.getType() == Material.IRON_FENCE) {

        } else if(clicked.getType() == Material.MAP) {

            context.getWhoClicked().closeInventory();

            if(context.getWhoClicked() instanceof Player) {

                Player player = (Player) context.getWhoClicked();
                player.closeInventory();
                TTT.core.getInvManager().openPlayerTickets(player);
            }

        } else if(clicked.getType() == Material.BARRIER) {

            context.getWhoClicked().closeInventory();

        }

    }

    private void onTicketsClick(final InventoryClickEvent context) {

        ItemStack clicked = context.getCurrentItem();
        String attribute = "none";


        if(clicked.getItemMeta().getDisplayName().equals(Messages.ItemNames.TICKET_TRAITOR)) {

            attribute = "traitor";

        } else if(clicked.getItemMeta().getDisplayName().equals(Messages.ItemNames.TICKET_INNOCENT)) {

            attribute = "innocent";

        }else if(clicked.getItemMeta().getDisplayName().equals(Messages.ItemNames.TICKET_DETECTIVE)) {

            attribute = "detective";

        }

        if(context.getWhoClicked() instanceof Player) {

            Player player = (Player) context.getWhoClicked();
            player.chat("/ticket " + attribute);
        }

    }

}
