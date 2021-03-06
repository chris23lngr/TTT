package de.z1up.ttt.manager;

import de.dytanic.cloudnet.bridge.internal.util.ItemStackBuilder;
import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.MathUtils;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Achievement;
import de.z1up.ttt.util.o.DBPlayer;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Iterator;

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

                Inventory inventory = Bukkit.createInventory(player, 9 * 6, Messages.ItemNames.ACHIEVEMENTS);

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

                Inventory inventory = Bukkit.createInventory(player, 9 * 3, Messages.ItemNames.SETTINGS);

                ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE, 8).setDisplayName("§c").build();
                for(int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, filler);
                }

                inventory.setItem(9, new ItemBuilder(Material.REDSTONE_COMPARATOR, 0)
                        .setDisplayName(Messages.ItemNames.PLAYER_SETTINGS).build());

                inventory.setItem(11, new ItemBuilder(Material.MAP, 0)
                        .setDisplayName(Messages.ItemNames.TICKETS).build());

                inventory.setItem(13, new ItemBuilder(Material.DIAMOND,0)
                        .setDisplayName("§7Force start").build());

                inventory.setItem(15, new ItemBuilder(Material.IRON_FENCE, 0)
                        .setDisplayName("§7Traitorfalle").build());

                inventory.setItem(17, new ItemBuilder(Material.BARRIER, 0)
                        .setDisplayName("§cSchließen").build());

                player.openInventory(inventory);

            }
        });
    }

    public void openVotingInv(Player player) {

        Bukkit.getScheduler().runTaskAsynchronously(TTT.getInstance(), new Runnable() {
            @Override
            public void run() {

                Inventory inventory = Bukkit.createInventory(player, 9 * 3, Messages.ItemNames.MAP_VOTING);

                ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 8).setDisplayName("§c ").build();
                for(int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, filler);
                }

                Collection<Map> maps = TTT.core.getMapManager().getMaps();

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
                .setDisplayName(Messages.ItemNames.ACHIEVEMENTS).build());

        player.getInventory().setItem(2, new ItemBuilder(Material.REDSTONE_TORCH_ON, (short) 0)
                .setDisplayName(Messages.ItemNames.SETTINGS).build());

        player.getInventory().setItem(4, TTT.core.getRuleBook().getBook());

        player.getInventory().setItem(6, new ItemBuilder(Material.MAP, (short) 0)
                .setDisplayName(Messages.ItemNames.MAP_VOTING).build());

        player.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM, (short) 0)
                .setDisplayName(Messages.ItemNames.QUIT_GAME).build());

    }

    public void setGameItems(Player player) {

        ItemStack identifier = new ItemBuilder(Material.STICK, 0).setDisplayName(Messages.ItemNames.IDENTIFIER).build();
        player.getInventory().setItem(8, identifier);

    }

    public void setGameItemsShop(Player player) {

        ItemStack shop = new ItemBuilder(Material.EMERALD, 0).setDisplayName(Messages.ItemNames.SHOP).build();
        player.getInventory().setItem(7, shop);

    }

    public void openNavigator(Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {

                Inventory inventory = Bukkit.createInventory(player, calcSize(),Messages.ItemNames.NAVIGATOR);

                Iterator targets = Bukkit.getOnlinePlayers().iterator();

                while (targets.hasNext()) {

                    Player target = (Player) targets.next();

                    if(TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {

                        if(!TTT.core.getPlayerManager().isSpec(target)) {
                            ItemStack head = new ItemBuilder(target.getName()).setDisplayName("§e" + target.getName()).build();
                            inventory.addItem(head);
                        }
                    }
                }

                player.openInventory(inventory);

            }
        }.runTaskAsynchronously(TTT.getInstance());

    }

    private int calcSize() {

        int size = Bukkit.getOnlinePlayers().size();
        int actualSize = size - TTT.core.getPlayerManager().getSpecs().size();

        double d = actualSize / 9;

        if(MathUtils.isPointValue(d)) {
            int after = MathUtils.getAfter(d);
            d = d - after;
        }

        d = (d + 1) * 9;
        return (int) d;
    }

    public void openPlayerSettings(Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {

                Inventory inventory = Bukkit.createInventory(player, calcSize(),Messages.ItemNames.PLAYER_SETTINGS);

                ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 8).setDisplayName("§c").build();
                for(int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, filler);
                }

                player.openInventory(inventory);

            }
        }.runTaskAsynchronously(TTT.getInstance());

    }

    public void openPlayerTickets(Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {

                Inventory inventory = Bukkit.createInventory(player, 9,Messages.ItemNames.TICKETS);

                if(!TTT.core.getPlayerManager().exists(player)) {
                   return;
                }

                DBPlayer dbPlayer = (DBPlayer) TTT.core.getPlayerManager().get(player);
                int traitorTickets = dbPlayer.getTPasses();
                int detectiveTickets = dbPlayer.getDPasses();
                int innnoTickets = dbPlayer.getIPasses();

                ItemStack itemTraitor = new ItemBuilder(Material.WOOL, 14)
                        .setDisplayName(Messages.ItemNames.TICKET_TRAITOR)
                        .setLore("§7Übrig: §a" + traitorTickets)
                        .build();

                ItemStack itemDetective = new ItemBuilder(Material.WOOL, 11)
                        .setDisplayName(Messages.ItemNames.TICKET_DETECTIVE)
                        .setLore("§7Übrig: §a" + detectiveTickets)
                        .build();

                ItemStack itemInnocent = new ItemBuilder(Material.WOOL, 5)
                        .setDisplayName(Messages.ItemNames.TICKET_INNOCENT)
                        .setLore("§7Übrig: §a" + innnoTickets)
                        .build();

                inventory.setItem(1, itemTraitor);
                inventory.setItem(4, itemDetective);
                inventory.setItem(7, itemInnocent);

                player.openInventory(inventory);

            }
        }.runTaskAsynchronously(TTT.getInstance());

    }

}
