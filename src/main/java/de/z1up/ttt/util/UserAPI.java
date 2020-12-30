package de.z1up.ttt.util;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class UserAPI {

    public static void resetPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode( GameMode.SURVIVAL);
        player.setHealthScale(20.0D);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setExp(0.0F);
        player.setLevel(0);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public static void playJoinEffects(Player player) {
        MessageAPI.sendTitle(player, 20, 40, 20, "§4TTT", "§a§lRenixinside.de");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 2.0F);
    }

}
