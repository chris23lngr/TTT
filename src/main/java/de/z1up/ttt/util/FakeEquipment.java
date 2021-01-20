package de.z1up.ttt.util;

import de.z1up.ttt.TTT;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.o.TTTPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FakeEquipment {

    public static void sendTraitorEquipment(Player player, Player target) {

        int entityId = player.getEntityId();
        int slot = 2;

        ItemStack itemStack = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        meta.setColor(Color.RED);
        itemStack.setItemMeta(meta);

        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityId, slot, CraftItemStack.asNMSCopy(itemStack));
        ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet);

    }

    public static void sendInnoEquipment(Player player, Player target) {

        int entityId = player.getEntityId();
        int slot = 3;

        ItemStack itemStack = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        meta.setColor(Color.LIME);
        itemStack.setItemMeta(meta);

        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityId, slot, CraftItemStack.asNMSCopy(itemStack));
        ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet);

    }

    public static void sendEquipment() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            if(TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {

                TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(player);

                if(tttPlayer.getTeam() == ManagerTeam.Team.TRAITOR) {

                    for(Player target : Bukkit.getOnlinePlayers()) {

                        if(TTT.core.getPlayerManager().existsPlayer(target.getUniqueId())) {

                            TTTPlayer tttTarget = TTT.core.getPlayerManager().getTTTPlayer(target);

                            if (tttTarget.getTeam() == ManagerTeam.Team.TRAITOR) {
                                sendTraitorEquipment(player, target);
                            } else {
                                sendInnoEquipment(player, target);
                            }
                        }

                    }

                }
            }

        }

    }

}
