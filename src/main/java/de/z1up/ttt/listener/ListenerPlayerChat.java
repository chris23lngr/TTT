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


        if(TTT.core.getPlayerManager().isSpec(player)) {
            if(TTT.core.getGameManager().inRestart()) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
            return;
        }

        String format = "§7█ §8● §7" + player.getName() + " §8» §7" + message;

        if(TTT.core.getGameManager().inSavePhase()
                || TTT.core.getGameManager().inLobby()
                || TTT.core.getGameManager().inRestart()) {
            event.setFormat(format);
            return;
        }

        message = event.getMessage().replaceAll("%", "%%");

        if(TTT.core.getGameManager().inGame()) {

            if(TTT.core.getPlayerManager().isInno(player)) {
                format = "§2█ §8● §7" + player.getName() + " §8» §7" + message;
                event.setFormat(format);
                return;
            }

            if(TTT.core.getPlayerManager().isDetective(player)) {
                format = "§9█ §8● §7" + player.getName() + " §8» §7" + message;
                event.setFormat(format);
                return;
            }


            if(TTT.core.getPlayerManager().isTraitor(player)) {

                event.setCancelled(true);

                Iterator targets = Bukkit.getOnlinePlayers().iterator();

                if(message.startsWith("@t")) {
                    while (targets.hasNext()) {
                        Player target = (Player) targets;
                        if(TTT.core.getPlayerManager().isTraitor(target)) {
                            format = "§4█ Traitor §8● §7" + player.getName() + " §8» §7" + message;
                            target.sendMessage(format);
                        }
                    }
                    return;
                }

                while (targets.hasNext()) {
                    Player target = (Player) targets;
                    if(!TTT.core.getPlayerManager().isTraitor(target)) {
                        format = "§2█ §8● §7" + player.getName() + " §8» §7" + message;
                        target.sendMessage(format);
                    } else {
                        format = "§4█ §8● §7" + player.getName() + " §8» §7" + message;
                        target.sendMessage(format);
                    }
                }

            }
            return;
        }

    }

}
