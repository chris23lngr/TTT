package de.z1up.ttt.util;

import de.z1up.ttt.TTT;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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
        player.setGameMode( GameMode.SURVIVAL);

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
     * Resets the player by running the {@code resetPlayer()}
     * method in a new Thread.
     *
     * @param player The player which will be reset.
     */
    public static void resetPlayerAsync(final Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {

                resetPlayer(player);

            }
        }.runTaskAsynchronously(TTT.getInstance());
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
     * Runs the {@code playJoinEffects()} method in a new
     * Thread.
     *
     * @param player The player who will receive the effects.
     */
    public static void playJoinEffectsAsync(Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {

                playJoinEffects(player);

            }
        }.runTaskAsynchronously(TTT.getInstance());
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

    /**
     * Runs the {@code setToSpec()} method in a new
     * Thread.
     *
     * @param player The player that will be set to spec.
     */
    public static void setToSpecAsync(Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {

                setToSpec(player);

            }
        }.runTaskAsynchronously(TTT.getInstance());

    }


}
