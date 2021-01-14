package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerOpenChestEvent;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class ListenerPlayerInteract implements Listener {

    public ListenerPlayerInteract() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerInteractEvent event) {

        Player player = event.getPlayer();

        // cancel everything if the
        // player is in build mode
        if(TTT.core.getBuildManager().canBuild(player)) {
            event.setCancelled(false);
            return;
        }

        // chek if a chest was clicked
        // if so -> call
        // PlayerOpenChestEvent
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {
            Material mat = event.getClickedBlock().getType();

            // check material, just to be
            // on the safe side
            if(mat != null) {

                // check if block is of
                // right material
                if(mat == Material.CHEST || mat == Material.ENDER_CHEST) {

                    // cancel event
                    event.setCancelled(true);

                    // Call the openChestEvent
                    PlayerOpenChestEvent openChestEvent
                            = new PlayerOpenChestEvent(player, event.getClickedBlock(), false);
                    Bukkit.getPluginManager().callEvent(openChestEvent);

                    return;
                }
            }
        }

        // check if event action was
        // a right click
        if((event.getAction() != Action.RIGHT_CLICK_BLOCK) && (event.getAction() != Action.RIGHT_CLICK_AIR) && (event.getAction() != Action.PHYSICAL)) {
            return;
        }

        if(event.getItem() == null) return;
        if(event.getItem().getItemMeta() == null) return;
        if(event.getItem().getType().equals(Material.BOOK)) return;
        if(event.getItem().getItemMeta().getDisplayName() == null) return;
        if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("")) return;

        // check if player is a spec
        if(TTT.core.getPlayerManager().isSpec(player)) {

            // cancel the event
            //            event.setCancelled(true);

            // run the onSpec method
            onSpec(event);
            return;
        }

        // check if players are still
        // in the lobby
        if(TTT.core.getGameManager().inLobby()) {

            // cancel the event
            event.setCancelled(true);

            // run the onLobby method
            onLobby(event);
            return;
        }
    }

    private void onLobby(PlayerInteractEvent context) {

        Player player = context.getPlayer();
        String display = context.getItem().getItemMeta().getDisplayName();

        if(display.equals(Messages.ItemNames.ACHIEVEMENTS)) {
            TTT.core.getInvManager().openAchievementsInv(player);
            return;
        }

        if(display.equals(Messages.ItemNames.SETTINGS)) {
            TTT.core.getInvManager().openSettingsInv(player);
            return;
        }

        if(display.equals(Messages.ItemNames.MAP_VOTING)) {

            if(TTT.core.getVoteManager().isVotePeriodActive()) {
                TTT.core.getInvManager().openVotingInv(player);
            } else {
                player.sendMessage(Messages.VOTE_PERIOD_NOT_ACTIVE);
            }
            return;
        }

        if(display.equals(Messages.ItemNames.QUIT_GAME)) {
            player.kickPlayer(Messages.GAME_LEFT);
            return;
        }

    }

    private void onSpec(PlayerInteractEvent context) {

        ItemStack itemStack = context.getItem();
        String display = itemStack.getItemMeta().getDisplayName();
        System.out.println("CLICKED " + itemStack.getItemMeta().getDisplayName());

        if(display.equals(Messages.ItemNames.NAVIGATOR)) {
            TTT.core.getInvManager().openNavigator(context.getPlayer());
            return;
        }

        if(display.equals(Messages.ItemNames.QUIT_GAME)) {

            Player player = context.getPlayer();
            player.kickPlayer(Messages.GAME_LEFT);

            return;
        }

    }

}
