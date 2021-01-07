package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class ListenerPlayerLogin implements Listener {

    public ListenerPlayerLogin() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerLoginEvent event) {

        if(TTT.core.getGameManager().notSet()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                    "§cEs ist ein Fehler aufgetreten!");
            return;
        }

        if(TTT.core.getGameManager().inRestart()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                    "§7Der Server restartet.");
            return;
        }

        if(TTT.core.getGameManager().inGame() || TTT.core.getGameManager().inSavePhase()) {
            event.allow();
            return;
        }

        if(TTT.core.getGameManager().inLobby()) {

            final Player player = event.getPlayer();

            int currentPlayerSize = Bukkit.getOnlinePlayers().size();
            int maxPlayerSize = Bukkit.getMaxPlayers();

            if(currentPlayerSize < maxPlayerSize) {
                event.allow();
                return;
            }

            if(currentPlayerSize == maxPlayerSize) {

                if(!player.hasPermission("ttt.premium")) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                            Messages.PREMIUM_NEEDED);
                    return;
                }

                AtomicInteger premiumCount = new AtomicInteger();

                Bukkit.getOnlinePlayers().forEach(selected -> {
                    if(selected.hasPermission("ttt.premium")) {
                        premiumCount.getAndIncrement();
                    }
                });

                if(premiumCount.get() >= maxPlayerSize) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                            Messages.NO_KICK_PLAYER_FOUND);
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(selected -> {
                    if(!selected.hasPermission("ttt.premium")) {
                        selected.kickPlayer(Messages.KICKED_BY_PREMIUM);
                    }
                });

                event.allow();

            }
            return;
        }

    }

}
