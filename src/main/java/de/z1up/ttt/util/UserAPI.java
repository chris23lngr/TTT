package de.z1up.ttt.util;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        MessageAPI.sendTitle(player, 20, 40, 20, "§4TTT", "§aRenixinside.de");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 2.0F);
    }

    public static void setSpecInv(Player player) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(TTT.getInstance(), () -> {

            //player.teleport(Spawns.getLobby());
            TTT.core.getInvManager().setSpecInv(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setFoodLevel(20);
            player.setHealth(20.0D);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                    99999999, 1, false, false));

        }, 10L);
    }

}
