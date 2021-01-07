package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.scheduler.TTTRunnable;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.MessageAPI;
import de.z1up.ttt.util.Messages;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager implements Manager {

    private TTTRunnable lobbyCountdown;

    public TimerManager() {
        load();
    }

    @Override
    public void load() {
        init();
        startTimerWaitingForPlayers();
    }

    @Override
    public void init() {
        lobbyCountdown = new TTTRunnable(60, false, GameManager.GameState.LOBBYPHASE,
                Messages.PREFIX + "§7Das Spiel startet in §a"
                        + (Integer.parseInt("2") == 1 ? "einer Sekunde" : "%time% Sekunden") + "§8!");
    }

    public TTTRunnable getLobbyCountdown() {
        return lobbyCountdown;
    }

    public void checkPlayersForStart() {

        if(!lobbyCountdown.isForced()) {
            if(Bukkit.getOnlinePlayers().size() < Bukkit.getMaxPlayers()) {
                return;
            }
        }

        if (TTT.core.getGameManager().inLobby()) {
            if(!lobbyCountdown.isActive()) {
                if((lobbyCountdown.getTime() != 0) && (lobbyCountdown.getTime() != -1)) {
                    lobbyCountdown.runAsync();
                }
            }
        }
    }

    public void startTimerWaitingForPlayers() {

        if(!TTT.core.getGameManager().inLobby()) {
            return;
        }

        if(!lobbyCountdown.isForced()) {
            if(lobbyCountdown.isActive()) {
                return;
            }
        }

        new BukkitRunnable() {

            @Override
            public void run() {

                if(!TTT.core.getGameManager().inLobby()) {
                    cancel();
                    return;
                }

                if(lobbyCountdown.isActive()) {
                    cancel();
                    return;
                }

                String msg = Messages.PREFIX + "§7Warten auf weitere Spieler... " +
                        "§8[§e" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "§8]";

                Bukkit.getOnlinePlayers().forEach(player -> {
                    MessageAPI.sendActionBar(player, msg);
                });

            }
        }.runTaskTimerAsynchronously(TTT.getInstance(), 0, 40);

    }

}
