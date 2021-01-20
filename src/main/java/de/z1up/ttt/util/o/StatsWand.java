package de.z1up.ttt.util.o;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.uuid.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class StatsWand {

    public static void setStats() {

        Location location = new Location(Bukkit.getWorld("Wartelobby"), 623, 18, -480);


        ArrayList<DBPlayer> topPlayers = TTT.core.getPlayerManager().getTopPlayers();

        System.out.println("SIZE " + topPlayers.size());

        for(DBPlayer player : topPlayers) {

            UUID uuid = player.getUuid();
            String name = UUIDFetcher.getName(uuid);
            int karma = player.getKarma();
            int pos = topPlayers.indexOf(player) + 1;


            location.getWorld().getBlockAt(location).setType(Material.SKULL);
            Skull s = (Skull) location.getBlock().getState();
            s.setSkullType(SkullType.PLAYER);
            s.setOwner(name);
            s.setRotation(BlockFace.EAST);
            s.update();

            Location signLocation = location.clone().subtract(0, 1, 0);

            if(signLocation.getBlock().getState() instanceof Sign) {

                Sign sign = (Sign) signLocation.getBlock().getState();
                sign.setLine(0, "§c#" + pos);
                sign.setLine(1, "§8" + name);
                sign.setLine(2, "§8Karma: §c" + karma);
                sign.update();

            }

            location = location.subtract(0, 0 , 1);

        }

    }

}
