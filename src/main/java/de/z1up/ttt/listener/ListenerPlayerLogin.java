package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
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

        if(Core.gameManager.notSet()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                    "§cEs ist ein Fehler aufgetreten!");
            return;
        }

        if(Core.gameManager.inRestart()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                    "§7Der Server restartet.");
            return;
        }

        if(Core.gameManager.inGame() || Core.gameManager.inSavePhase()) {
            event.allow();
            return;
        }

        if(Core.gameManager.inLobby()) {

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
                            "§cDu benötigst mindestens den §6Premium §cRang, " +
                                    "um diesen Server betreten zu können!");
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
                            "§cKonnte keinen Spieler zum kicken finden!");
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(selected -> {
                    if(!selected.hasPermission("ttt.premium")) {
                        selected.kickPlayer("§7Du wurdest von einem §6Premium §7gekickt!");
                    }
                });

                event.allow();

            }
            return;
        }

    }

}
