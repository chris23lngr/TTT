package de.z1up.ttt.listener;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudProxy;
import de.dytanic.cloudnet.bridge.internal.command.proxied.CommandPermissions;
import de.dytanic.cloudnet.lib.CloudNetwork;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import de.dytanic.cloudnet.lib.player.permission.PermissionPool;
import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Iterator;

public class ListenerPlayerChat implements Listener {

    public ListenerPlayerChat() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final AsyncPlayerChatEvent event) {

        final Player player = event.getPlayer();
        String message = event.getMessage();

        if(TTT.core.getPlayerManager().isSpec(player)) {
            if (TTT.core.getGameManager().inRestart()) {
                event.setCancelled(false);
                return;
            }

            event.setCancelled(true);
            return;
        }

        CloudPlayer cloudPlayer = CloudAPI.getInstance().getOnlinePlayer(player.getUniqueId());
        PermissionPool pool = CloudAPI.getInstance().getPermissionPool();
        PermissionGroup group = cloudPlayer.getPermissionEntity().getHighestPermissionGroup(pool);
        String cc = ChatColor.translateAlternateColorCodes('&', group.getColor());

        String format = "§7█ §8● " + cc + player.getName() + " §8» §7" + message;

        if(TTT.core.getGameManager().inSavePhase()
                || TTT.core.getGameManager().inLobby()
                || TTT.core.getGameManager().inRestart()) {
            event.setFormat(format);
            return;
        }

        message = event.getMessage().replaceAll("%", "%%");

        TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(player);

        if(!tttPlayer.hasTeam()) {
            event.setFormat(format);
            return;
        }

        if(TTT.core.getGameManager().inGame()) {

            if(tttPlayer.getTeam() == ManagerTeam.Team.DETECTIVE) {
                format = "§9█ §8● §9" + player.getName() + " §8» §7" + message;
                event.setFormat(format);
                return;
            }

            if(tttPlayer.getTeam() == ManagerTeam.Team.INNOCENT) {
                format = "§a█ §8● §a" + player.getName() + " §8» §7" + message;
                event.setFormat(format);
                return;
            }


            if(tttPlayer.getTeam() == ManagerTeam.Team.TRAITOR) {

                event.setCancelled(true);

                Iterator targets = Bukkit.getOnlinePlayers().iterator();

                if(message.startsWith("@t")) {
                    message = message.replaceAll("@t", "");
                    while (targets.hasNext()) {
                        Player target = (Player) targets.next();
                        TTTPlayer tttTarget = TTT.core.getPlayerManager().getTTTPlayer(target);
                        if(tttTarget.getTeam() == ManagerTeam.Team.TRAITOR) {
                            format = "§4█ Traitor §8● §7" + player.getName() + " §8» §7" + message;
                            target.sendMessage(format);
                        }
                    }
                    return;
                }

                while (targets.hasNext()) {
                    Player target = (Player) targets.next();
                    TTTPlayer tttTarget = TTT.core.getPlayerManager().getTTTPlayer(target);
                        if(tttTarget.hasTeam()) {
                            if(tttTarget.getTeam() == ManagerTeam.Team.TRAITOR) {
                                format = "§4█ §8● §4" + player.getName() + " §8» §7" + message;
                                target.sendMessage(format);
                            } else {
                                format = "§a█ §8● §a" + player.getName() + " §8» §7" + message;
                                target.sendMessage(format);
                            }
                        } else {
                            format = "§a█ §8● §a" + player.getName() + " §8» §7" + message;
                            target.sendMessage(format);
                        }
                    }
                }

            return;
        }

    }

}
