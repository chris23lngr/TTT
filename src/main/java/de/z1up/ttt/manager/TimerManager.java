package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.scheduler.TTTRunnable;
import de.z1up.ttt.util.MessageAPI;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager implements Manager {

    private TTTRunnable lobbyTimer;
    private TTTRunnable preSavePhaseTimer;
    private TTTRunnable savePhaseTimer;
    private TTTRunnable gameTimer;
    private TTTRunnable restartTimer;

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
        initLobbyTimer();
        initPreSavePhaseTimer();
        initSavePhaseTimer();
        initGameTimer();
        initRestartTimer();
    }

    public TTTRunnable getLobbyTimer() {
        return lobbyTimer;
    }

    public TTTRunnable getSavePhaseTimer() {
        return savePhaseTimer;
    }

    public TTTRunnable getPreSavePhaseTimer() {
        return preSavePhaseTimer;
    }

    public TTTRunnable getGameTimer() {
        return gameTimer;
    }

    public TTTRunnable getRestartTimer() {
        return restartTimer;
    }

    public void checkPossibleGameStart() {

        if(!lobbyTimer.isForced()) {
            if(Bukkit.getOnlinePlayers().size() < 2) {
                return;
            }
        }

        if (TTT.core.getGameManager().inLobby()) {
            if(!lobbyTimer.isActive()) {
                if((lobbyTimer.getTime() != 0) && (lobbyTimer.getTime() != -1)) {
                    lobbyTimer.runAsync();
                }
            }
        }
    }

    public void startTimerWaitingForPlayers() {

        if(!TTT.core.getGameManager().inLobby()) {
            return;
        }

        if(!lobbyTimer.isForced()) {
            if(lobbyTimer.isActive()) {
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

                if(lobbyTimer.isActive()) {
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

    public void initLobbyTimer() {
        this.lobbyTimer = new TTTRunnable(60, false, GameManager.GameState.LOBBYPHASE,
                Messages.PREFIX + "§7Das Spiel startet in §a"
                        + (Integer.parseInt("2") == 1 ? "einer Sekunde" : "%time% Sekunden") + "§8!");
    }

    public void initPreSavePhaseTimer() {
        this.preSavePhaseTimer = new TTTRunnable(5, false, GameManager.GameState.PRE_SAVEPHASE,
                null);
    }

    public void initSavePhaseTimer() {
        this.savePhaseTimer = new TTTRunnable(20, false, GameManager.GameState.SAVEPHASE,
                null);
    }

    public void initGameTimer() {
        this.gameTimer = new TTTRunnable(60 * 5, false, GameManager.GameState.INGAME,
                Messages.PREFIX + "§7Die Spiel wird in §a"
                        + (Integer.parseInt("2") == 1 ? "einer Sekunde" : "%time% Sekunden") + " gestoppt§8!");
    }

    public void initRestartTimer() {
        this.restartTimer = new TTTRunnable(30, false, GameManager.GameState.RESTART,
                Messages.PREFIX + "§cDer Server wird in §4"
                        + (Integer.parseInt("2") == 1 ? "einer Sekunde" : "%time% Sekunden") + " §cgestoppt§8!");
    }

}
