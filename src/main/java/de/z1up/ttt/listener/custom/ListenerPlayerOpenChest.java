package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerOpenChestEvent;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ListenerPlayerOpenChest implements Listener {

    public ListenerPlayerOpenChest() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerOpenChestEvent event) {

        if(TTT.core.getPlayerManager().isSpec(event.getPlayer())) {
            return;
        }

        if(!TTT.core.getGameManager().inGame() && !TTT.core.getGameManager().inSavePhase()) {
            return;
        }

        Block block = event.getBlock();

        Material material = block.getType();

        if(material == Material.ENDER_CHEST) {
            onEnderChest(event);
        } else if(material == Material.CHEST) {
            onChest(event);
        }

    }

    public void onChest(PlayerOpenChestEvent context) {

        int i = new Random().nextInt(3);

        ItemStack woodSword = new ItemBuilder(Material.WOOD_SWORD, 0).setDisplayName("§7Holzschwert").build();
        ItemStack stoneSword = new ItemBuilder(Material.STONE_SWORD, 0).setDisplayName("§7Steinsschwert").build();
        ItemStack bow = new ItemBuilder(Material.BOW, 0).setDisplayName("§7Bogen").build();

        ItemStack itemStack = null;

        switch (i) {
            case 0: itemStack = woodSword;
                break;
            case 1: itemStack = stoneSword;
                break;
            case 2: itemStack = bow;
                break;
        }

        Player player = context.getPlayer();

        if(itemStack != null) {

            if(player.getInventory().contains(woodSword)
                    && player.getInventory().contains(stoneSword)
                    && player.getInventory().contains(bow)) {
                return;
            }

            if(player.getInventory().contains(itemStack)) {
                onChest(context);
                return;
            }

            // Save the Block
            TTT.core.getMapResetter().onMapBlockBreak(context.getBlock());

            context.getBlock().setType(Material.AIR);

            player.getInventory().addItem(itemStack);

            if(itemStack.getType() == Material.BOW) {
                addArrows(player);
            }

        }

    }

    private void addArrows(Player player) {
        ItemStack arrow = new ItemBuilder(Material.ARROW, 0).setDisplayName("§7Pfeil").build();
        for(int x = 0; x < 32; x++) {
            player.getInventory().addItem(arrow);
        }
    }

    public void onEnderChest(PlayerOpenChestEvent context) {

        if(!TTT.core.getGameManager().inGame()) {
            context.getPlayer().sendMessage(Messages.EC_NOT_OPEN_ATM);
            return;
        }

        // Save the Block
        TTT.core.getMapResetter().onMapBlockBreak(context.getBlock());

        context.getBlock().setType(Material.AIR);

        ItemStack ironSword = new ItemBuilder(Material.IRON_SWORD, 0).setDisplayName("§7Eisenschwert").build();
        context.getPlayer().getInventory().addItem(ironSword);

    }

}
