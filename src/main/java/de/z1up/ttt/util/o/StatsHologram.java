package de.z1up.ttt.util.o;

import de.z1up.ttt.TTT;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class StatsHologram {

    private Player player;
    private Location location;
    private String[] lines;
    private ArrayList<EntityArmorStand> armorStands;

    public StatsHologram(Player player, Location location, String... lines) {

        this.player = player;
        this.location = TTT.core.getLocationManager().getHoloLocation();
        this.lines = lines;
        this.armorStands = new ArrayList<>();

    }

    public void show() {
        for(EntityArmorStand armor : armorStands) {
            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armor);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void build() {

        Iterator iterator = Arrays.stream(lines).iterator();

        while (iterator.hasNext()) {

            String line = (String) iterator.next();

            EntityArmorStand armor = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ());
            armor.setCustomName(line);
            armor.setCustomNameVisible(true);
            armor.setInvisible(true);
            armor.setGravity(false);

            armorStands.add(armor);

            location.subtract(0,  0.25D, 0);
        }

        int i = armorStands.size();
        for(int x = 0; x < i; x++) {
            location.add(0, 0.25D, 0);
        }
    }

}
