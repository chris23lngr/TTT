package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.o.Achievement;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class InventoryManager implements Manager {

    @Override
    public void load() {

    }

    @Override
    public void init() {

    }

    public void openAchievementsInv(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(TTT.getInstance(), new Runnable() {
            @Override
            public void run() {

                Inventory inventory = Bukkit.createInventory(player, 9 * 6, "§eAchievements");

                for(int i = 0; i < 50; i++) {
                    Achievement achievement = null;

                    if(achievement != null) {
                        String name = achievement.getName();
                        String desc = achievement.getDescription();

                        ItemStack item = new ItemBuilder(Material.INK_SACK, (short) 8)
                                .setDisplayName("§a" + name).setLore("§7" + desc).build();
                        inventory.setItem(i-1, item);
                    }
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

        Bukkit.getScheduler().runTaskAsynchronously(TTT.getInstance(), new Runnable() {
            @Override
            public void run() {

                Inventory inventory = Bukkit.createInventory(player, 9 * 3, "§bMapvoting");

                ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 8).setDisplayName("§c ").build();
                for(int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, filler);
                }

                Collection<Map> maps = Data.mapManager.getMaps();

                int pos = 9;

                for(Map map : maps) {

                    ItemStack item = new ItemBuilder(map.getItemMat(), (short) 0).setDisplayName("§a" + map.getName()).setLore("§dVotes: " + map.getVotes()).build();
                    inventory.setItem(pos, item);
                    pos = pos + 4;

                }
                player.openInventory(inventory);

            }
        });

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
