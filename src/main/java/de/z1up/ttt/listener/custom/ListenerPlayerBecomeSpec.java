package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerBecomeSpecEvent;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Iterator;

public class ListenerPlayerBecomeSpec implements Listener {

    public ListenerPlayerBecomeSpec() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerBecomeSpecEvent event) {

        Player player = event.getPlayer();

        this.resetSpec(player);
        this.hideSpec(player);
        this.setSpecInv(player);

        player.sendMessage(Messages.PLAYING_AS_SPEC);
    }

    private void resetSpec(Player player) {

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

        player.setAllowFlight(true);
        player.spigot().setCollidesWithEntities(false);
        player.setPlayerListName("§7" + player.getName());

        TTT.core.getPlayerManager().addSpectator(player);
    }

    private void setSpecInv(Player player) {

        ItemStack navigator = new ItemBuilder(Material.COMPASS, 0).setDisplayName(Messages.ItemNames.NAVIGATOR).build();
        player.getInventory().setItem(4, navigator);

        ItemStack quit = new ItemBuilder(Material.MAGMA_CREAM, 0).setDisplayName(Messages.ItemNames.QUIT_GAME).build();
        player.getInventory().setItem(8, quit);

    }

    private void hideSpec(Player player) {

        Iterator targets = Bukkit.getOnlinePlayers().iterator();

        while (targets.hasNext()) {
            Player target = (Player) targets.next();

            if(!TTT.core.getPlayerManager().isSpec(target)) {
                target.hidePlayer(player);
            } else {
                target.showPlayer(player);
            }

            player.showPlayer(target);
        }

    }

}
