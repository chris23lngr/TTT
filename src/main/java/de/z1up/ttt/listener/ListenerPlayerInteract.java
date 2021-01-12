package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ListenerPlayerInteract implements Listener {

    public ListenerPlayerInteract() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if(TTT.core.getBuildManager().canBuild(player)) {
            event.setCancelled(false);
            return;
        }

        if(event.getAction() == Action.PHYSICAL
                && event.getClickedBlock().getType().equals(Material.SOIL)) {
            event.setCancelled(true);
            return;
        }

        if(TTT.core.getPlayerManager().isSpec(player)
                && (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || event.getAction().equals(Action.PHYSICAL))) {
            event.setCancelled(true);
            return;
        }

        if(event.getClickedBlock() != null) {
            onChest(event);
            return;
        }

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)
                || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if(TTT.core.getGameManager().inLobby()) {

                if(event.getItem() == null) return;
                if(event.getItem().getItemMeta() == null) return;
                if(event.getItem().getType().equals(Material.BOOK)) return;
                if(event.getItem().getItemMeta().getDisplayName() == null) return;
                if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("")) return;

                String display = event.getItem().getItemMeta().getDisplayName();

                if(display.equals("§eErfolge")) {
                    TTT.core.getInvManager().openAchievementsInv(player);
                    return;
                }

                if(display.equals("§4Einstellungen")) {
                    TTT.core.getInvManager().openSettingsInv(player);
                    return;
                }

                if(display.equals("§bMap voting")) {

                    if(TTT.core.getVoteManager().isVotePeriodActive()) {
                        TTT.core.getInvManager().openVotingInv(player);
                    } else {
                        player.sendMessage(Messages.VOTE_PERIOD_NOT_ACTIVE);
                    }
                    return;
                }

                if(display.equals("§8Spiel verlassen")) {
                    player.kickPlayer(Messages.PREFIX + "§cDu hast das Spiel verlassen!");
                    return;
                }

                return;
            }

        }

    }

    public void onChest(PlayerInteractEvent event) {

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getAction().equals(Action.PHYSICAL)) {

            if(event.getClickedBlock() == null) {
                return;
            }

            Block block = event.getClickedBlock();
            Player player = event.getPlayer();

            if(block.getType().equals(Material.CHEST)) {

                if(addRandomItem(event.getPlayer())) {

                    event.setCancelled(true);
                    event.getClickedBlock().setType(Material.AIR);

                } else {
                    event.setCancelled(true);
                    player.closeInventory();
                }

                return;
            }

            if(block.getType().equals(Material.ENDER_CHEST)) {

                if(TTT.core.getGameManager().getGameState() != GameManager.GameState.INGAME) {
                    player.sendMessage(Messages.EC_NOT_OPEN_ATM);
                    return;
                }

                event.getClickedBlock().setType(Material.AIR);

                event.setCancelled(true);
                player.closeInventory();

                ItemStack sword = new ItemBuilder(Material.IRON_SWORD, 0).setDisplayName("§eEisenschwert").build();
                player.getInventory().addItem(sword);


            }

        }

    }

    public boolean addRandomItem(Player player) {

        int i = new Random().nextInt(3);

        ItemStack itemStack = null;

        switch (i) {
            case 0: itemStack = new ItemBuilder(Material.WOOD_SWORD, 0).setDisplayName("§fHolzschwert").build();
                break;
            case 1: itemStack = new ItemBuilder(Material.STONE_SWORD, 0).setDisplayName("§fSteinsschwert").build();
                break;
            case 2: itemStack = new ItemBuilder(Material.BOW, 0).setDisplayName("§fBogen").build();
                break;
        }

        if(!player.getInventory().contains(itemStack)) {
            addRandomItem(player);

            if(itemStack.getType().equals(Material.BOW)) {
                ItemStack arrow = new ItemBuilder(Material.ARROW, 0).setDisplayName("§fPfeil").build();
                for(int x = 0; x < 32; i++) {
                    player.getInventory().addItem(arrow);
                }
            }
            return true;
        }

        addRandomItem(player);
        return false;
    }

}
