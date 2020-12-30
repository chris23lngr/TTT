package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

    public void openAchievementsInv(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(TTT.getInstance(), new Runnable() {
            @Override
            public void run() {

                Inventory inventory = Bukkit.createInventory(player, 9 * 6, "§eAchievements");

                ItemStack filler = new ItemBuilder(Material.INK_SACK, (short) 8).setDisplayName("§7Erfolg").build();
                for(int i = 0; i < 50; i++) {
                    inventory.setItem(i, filler);
                }

                player.openInventory(inventory);

            }
        });
    }

    public void openSettingsInv(Player player) {

        Bukkit.getScheduler().runTaskAsynchronously(TTT.getInstance(), new Runnable() {
            @Override
            public void run() {

                Inventory inventory = Bukkit.createInventory(player, 9 * 3, "§cEinstellungen");

                ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 14).setDisplayName("§c").build();
                for(int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, filler);
                }

                inventory.setItem(9, new ItemBuilder(Material.REDSTONE_COMPARATOR, (short) 0)
                        .setDisplayName("§7Einstellungen").build());

                inventory.setItem(11, new ItemBuilder(Material.MAP, (short) 0)
                        .setDisplayName("§7Pässe").build());

                inventory.setItem(13, new ItemBuilder(Material.DIAMOND, (short) 0)
                        .setDisplayName("§7Force start").build());

                inventory.setItem(15, new ItemBuilder(Material.IRON_BARDING, (short) 0)
                        .setDisplayName("§7Traitorfalle").build());

                inventory.setItem(17, new ItemBuilder(Material.BARRIER, (short) 0)
                        .setDisplayName("§cSchließen").build());

                player.openInventory(inventory);

            }
        });
    }

    public void openVotingInv(Player player) {

    }

    public void setLobbyItems(Player player) {

        player.getInventory().setItem(0, new ItemBuilder(Material.NETHER_STAR, (short) 0)
                .setDisplayName("§eErfolge").build());

        player.getInventory().setItem(2, new ItemBuilder(Material.REDSTONE_TORCH_ON, (short) 0)
                .setDisplayName("§4Einstellungen").build());

        player.getInventory().setItem(4, Data.ruleBook.getBook());

        player.getInventory().setItem(6, new ItemBuilder(Material.MAP, (short) 0)
                .setDisplayName("§bMap voting").build());

        player.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM, (short) 0)
                .setDisplayName("§8Spiel verlassen").build());

    }

    public void setSpecInv(Player player) {
        player.getInventory().setItem(0,
                new ItemBuilder(Material.COMPASS, (short) 0)
                        .setDisplayName("§9Lebende Spieler").build());
        player.getInventory().setItem(8,
                new ItemBuilder(Material.MAGMA_CREAM, (short) 0)
                        .setDisplayName("§8Spiel verlassen").build());
    }

}
