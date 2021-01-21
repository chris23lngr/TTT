package de.z1up.ttt.util;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import de.dytanic.cloudnet.lib.player.permission.PermissionPool;
import de.z1up.ttt.TTT;
import de.z1up.ttt.util.o.DBPlayer;
import de.z1up.ttt.util.o.StatsHologram;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;

/**
 * The UserAPI contains methods that generally occur
 * frequently and should be stored in a central location.
 *
 * @author chris23lngr
 * @since 1.0
 */
public class UserAPI {

    /**
     * Resets the player to the normal game state so
     * that, for example, health or the gamemode do
     * not remain at the old level.
     *
     * @param player The player which will be reset.
     */
    public static void resetPlayer(final Player player) {

        // clear the inventory
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        // set game mode
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);

        // set health
        player.setHealthScale(20.0D);
        player.setHealth(20.0D);

        // set food
        player.setFoodLevel(20);

        // set exp
        player.setExp(0.0F);
        player.setLevel(0);

        // remove potion effects
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    /**
     * Sends the given player effects that he should receive
     * when he joins the game.
     *
     * @param player The player who will receive the effects.
     */
    public static void playJoinEffects(Player player) {

        // send the title
        MessageAPI.sendTitle(player, 20, 40, 20,
                "§4TTT", "§aRenixinside.de");

        // play a sound
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 2.0F);

    }

    /**
     * Resets the player and than adds a {@link PotionEffect} of
     * {@link PotionEffectType} INVISIBILITY so that he's invisible
     * to the other players.
     *
     * @param player The player that will be set to spec.
     */
    public static void setToSpec(Player player) {

        // reset the player first
        resetPlayer(player);

        // than add invisibility
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                99999999, 1, false, false));

    }

    public static void setScoreboard(Player player) {
        TTT.core.getScoreboardAPI().createScoreboard(player);
    }

    public static void setHolo(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                TTT.core.getStatsManager().setHolo(player);
            }
        }.runTaskLaterAsynchronously(TTT.getInstance(), 15);
    }

    public static void setTablistName(Player player) {

        CloudPlayer cloudPlayer = CloudAPI.getInstance().getOnlinePlayer(player.getUniqueId());
        PermissionPool pool = CloudAPI.getInstance().getPermissionPool();
        PermissionGroup group = cloudPlayer.getPermissionEntity().getHighestPermissionGroup(pool);
        String cc = ChatColor.translateAlternateColorCodes('&', group.getColor());
        String prefix = group.getPrefix();
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        player.setPlayerListName(prefix + cc + player.getName());

    }

    public static void updatePlayerteams() {
        TTT.core.getScoreboardAPI().updatePlayerTeams();
    }

    

}
