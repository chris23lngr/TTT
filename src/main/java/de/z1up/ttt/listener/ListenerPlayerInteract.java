package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import org.apache.logging.log4j.message.Message;
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

        if(TTT.core.getBuildManager().canBuild(player)) {
            event.setCancelled(false);
            return;
        }

        if(event.getAction() == Action.PHYSICAL
                && event.getClickedBlock().getType().equals(Material.SOIL)) {
            event.setCancelled(true);
            return;
        }

        if(TTT.core.getPlayerManager().isSpec(player)
                && (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || event.getAction().equals(Action.PHYSICAL))) {
            event.setCancelled(true);
            return;
        }

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)
                || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if(TTT.core.getGameManager().inLobby()) {

                if(event.getItem() == null) return;
                if(event.getItem().getItemMeta() == null) return;
                if(event.getItem().getType().equals(Material.BOOK)) return;
                if(event.getItem().getItemMeta().getDisplayName() == null) return;
                if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("")) return;

                String display = event.getItem().getItemMeta().getDisplayName();

                if(display.equals("§eErfolge")) {
                    TTT.core.getInvManager().openAchievementsInv(player);
                    return;
                }

                if(display.equals("§4Einstellungen")) {
                    TTT.core.getInvManager().openSettingsInv(player);
                    return;
                }

                if(display.equals("§bMap voting")) {

                    if(TTT.core.getVoteManager().isVotePeriodActive()) {
                        TTT.core.getInvManager().openVotingInv(player);
                    } else {
                        player.sendMessage(Messages.VOTE_PERIOD_NOT_ACTIVE);
                    }
                    return;
                }

                if(display.equals("§8Spiel verlassen")) {
                    player.kickPlayer(Messages.PREFIX + "§cDu hast das Spiel verlassen!");
                    return;
                }

                return;
            }

        }

    }

}
