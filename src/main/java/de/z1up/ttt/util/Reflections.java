package de.z1up.ttt.util;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class Reflections {

    protected static void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    protected static void sendPacket(Packet<?> packet) {
        Bukkit.getOnlinePlayers().forEach(target -> sendPacket(target, packet));
    }

    protected static void setValue(Object object, String name, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(name);

            field.setAccessible(true);
            field.set(object, value);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    protected static Object getValue(Object object, String name) {
        try {
            Field field = object.getClass().getField(name);

            field.setAccessible(true);
            return field.get(object);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
