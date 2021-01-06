package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.scheduler.TTTRunnable;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.MessageAPI;
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
                Data.getPrefix() + "§7Das Spiel startet in §a"
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

        if (Data.gameManager.inLobby()) {
            if(!lobbyCountdown.isActive()) {
                if((lobbyCountdown.getTime() != 0) && (lobbyCountdown.getTime() != -1)) {
                    lobbyCountdown.runAsync();
                }
            }
        }
    }

    public void startTimerWaitingForPlayers() {

        if(!Data.gameManager.inLobby()) {
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

                if(!Data.gameManager.inLobby()) {
                    cancel();
                    return;
                }

                if(lobbyCountdown.isActive()) {
                    cancel();
                    return;
                }

                String msg = Data.getPrefix() + "§7Warten auf weitere Spieler... " +
                        "§8[§e" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "§8]";

                Bukkit.getOnlinePlayers().forEach(player -> {
                    MessageAPI.sendActionBar(player, msg);
                });

            }
        }.runTaskTimerAsynchronously(TTT.getInstance(), 0, 40);

    }

}
