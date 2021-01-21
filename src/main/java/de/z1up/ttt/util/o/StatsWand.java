package de.z1up.ttt.util.o;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.uuid.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import java.util.ArrayList;
import java.util.UUID;

public class StatsWand {

    public void build() {


        ArrayList<DBPlayer> topPlayers = TTT.core.getPlayerManager().getTopPlayers();
        int players = topPlayers.size();

        for(int i = 0; i < players; i++) {


            Location location = TTT.core.getLocationManager().getStatsWandLocation(i + 1);

            if(location == null) {
                return;
            }

            DBPlayer player = topPlayers.get(i);

            UUID uuid = player.getUuid();
            String name = UUIDFetcher.getName(uuid);
            int karma = player.getKarma();
            int pos = i + 1;

            System.out.println(location.getX());
            System.out.println(location.getY());
            System.out.println(location.getZ());
            System.out.println(location.getWorld().getName());

            setHead(name, location);
            setSign(pos, name, karma, location.subtract(0, 1, 0));

        }

    }

    private void setHead(String name, Location location) {

        Block block = location.getWorld().getBlockAt(location);

        if(block == null) {
            return;
        }

        if(block.getType() != Material.SKULL) {
            block.setType(Material.SKULL);
        }

        Skull s = (Skull) location.getBlock().getState();
        s.setSkullType(SkullType.PLAYER);
        s.setOwner(name);
        s.update();

    }

    private void setSign(int pos, String name, int karma, Location location) {

        Block block = location.getBlock();

        if(block.getState() instanceof Sign) {

            Sign sign = (Sign) block.getState();
            sign.setLine(0, "§c#" + pos);
            sign.setLine(1, "§8" + name);
            sign.setLine(2, "§8Karma: §c" + karma);
            sign.update();

        }

    }

}
