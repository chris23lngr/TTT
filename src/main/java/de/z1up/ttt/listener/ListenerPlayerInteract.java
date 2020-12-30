package de.z1up.ttt.listener;

import com.mysql.jdbc.Buffer;
import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerPlayerInteract implements Listener {

    public ListenerPlayerInteract() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if(Data.buildManager.canBuild(player)) {
            event.setCancelled(false);
            return;
        }

        if(event.getAction() == Action.PHYSICAL
                && event.getClickedBlock().getType().equals(Material.SOIL)) {
            event.setCancelled(true);
            return;
        }

        if(Data.playerManager.isSpec(player)
                && (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || event.getAction().equals(Action.PHYSICAL))) {
            event.setCancelled(true);
            return;
        }

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if(Data.gameManager.inLobby()) {

                if(event.getItem() == null) return;
                if(event.getItem().getItemMeta() == null) return;
                if(event.getItem().getItemMeta().getDisplayName() == null) return;
                if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("")) return;
                if(event.getItem().getType().equals(Material.BOOK)) return;

                String display = event.getItem().getItemMeta().getDisplayName();

                if(display.equals("§eErfolge")) {
                    Data.invManager.openAchievementsInv(player);
                    return;
                }

                if(display.equals("§4Einstellungen")) {
                    Data.invManager.openSettingsInv(player);
                    return;
                }

                if(display.equals("§bMap voting")) {
                    Data.invManager.openVotingInv(player);
                    return;
                }

                if(display.equals("§8Spiel verlassen")) {
                    player.kickPlayer(Data.getPrefix() + "§cDu hast das Spiel verlassen!");
                    return;
                }

                return;
            }

        }

    }

}
