package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
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

        /*
            String message = e.getMessage();
            String text = "[%time%] %playername% >> %message%";
            text = text.replaceAll("%time%", ReplayFile.getTime());
            text = text.replaceAll("%playername%", player.getName());
            text = text.replaceAll("%message%", message);
            ReplayFile.addLine(text);
         */

        if(Data.playerManager.isSpec(player)) {
            if(Data.gameManager.inRestart()) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
            return;
        }

        String message = event.getMessage().replaceAll("%", "%%");

        if(Data.gameManager.inGame()) {

            if(Data.playerManager.isInno(player)) {
                event.setFormat("§2█ §8● §7" + player.getName() + " §8» §7" + message);
                return;
            }

            if(Data.playerManager.isDetective(player)) {
                event.setFormat("§9█ §8● §7" + player.getName() + " §8» §7" + message);
                return;
            }

            if(Data.playerManager.isTraitor(player)) {

                event.setCancelled(true);

                Iterator targets = Bukkit.getOnlinePlayers().iterator();

                if(message.startsWith("@t")) {
                    while (targets.hasNext()) {
                        Player target = (Player) targets;
                        if(Data.playerManager.isTraitor(target)) {
                            target.sendMessage("§4█ Traitor §8● §7"
                                    + player.getName() + " §8» §7" + message);
                        }
                    }
                    return;
                }

                while (targets.hasNext()) {
                    Player target = (Player) targets;
                    if(!Data.playerManager.isTraitor(target)) {
                        target.sendMessage("§2█ §8● §7" + player.getName() + " §8» §7" + message);
                    } else {
                        target.sendMessage("§4█ §8● §7" + player.getName() + " §8» §7" + message);
                    }
                }

            }
            return;
        }

        if(Data.gameManager.inSavePhase() || Data.gameManager.inLobby() || Data.gameManager.inRestart()) {
            event.setFormat("§7█ §8● §7" + player.getName() + " §8» §7" + message);
            return;
        }

    }

}
