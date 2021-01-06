package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import org.bukkit.Bukkit;
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

        /*
        String text = "[%time%] %playername% >> %message%";
        text = text.replaceAll("%time%", Data.replayFile.getTime());
        text = text.replaceAll("%playername%", player.getName());
        text = text.replaceAll("%message%", message);
        Data.replayFile.addLine(text);

         */


        if(Core.playerManager.isSpec(player)) {
            if(Core.gameManager.inRestart()) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
            return;
        }

        message = event.getMessage().replaceAll("%", "%%");

        if(Core.gameManager.inGame()) {

            if(Core.playerManager.isInno(player)) {
                event.setFormat("§2█ §8● §7" + player.getName() + " §8» §7" + message);
                return;
            }

            if(Core.playerManager.isDetective(player)) {
                event.setFormat("§9█ §8● §7" + player.getName() + " §8» §7" + message);
                return;
            }

            if(Core.playerManager.isTraitor(player)) {

                event.setCancelled(true);

                Iterator targets = Bukkit.getOnlinePlayers().iterator();

                if(message.startsWith("@t")) {
                    while (targets.hasNext()) {
                        Player target = (Player) targets;
                        if(Core.playerManager.isTraitor(target)) {
                            target.sendMessage("§4█ Traitor §8● §7"
                                    + player.getName() + " §8» §7" + message);
                        }
                    }
                    return;
                }

                while (targets.hasNext()) {
                    Player target = (Player) targets;
                    if(!Core.playerManager.isTraitor(target)) {
                        target.sendMessage("§2█ §8● §7" + player.getName() + " §8» §7" + message);
                    } else {
                        target.sendMessage("§4█ §8● §7" + player.getName() + " §8» §7" + message);
                    }
                }

            }
            return;
        }

        if(Core.gameManager.inSavePhase() || Core.gameManager.inLobby() || Core.gameManager.inRestart()) {
            event.setFormat("§7█ §8● §7" + player.getName() + " §8» §7" + message);
            return;
        }

    }

}
