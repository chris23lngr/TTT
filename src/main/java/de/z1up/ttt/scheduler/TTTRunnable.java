package de.z1up.ttt.scheduler;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.CountdownFinishEvent;
import de.z1up.ttt.event.CountdownTimeChangeEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Method;

public class TTTRunnable extends BukkitRunnable {

    private int time;
    private boolean forced;
    private GameManager.GameState gameState;
    private String timerMessage;
    private boolean active;
    Method onStop;

    public TTTRunnable(int time, boolean forced, GameManager.GameState gameState, String timerMessage) {
        this.time = time;
        this.forced = forced;
        this.gameState = gameState;
        this.timerMessage = timerMessage;
        this.active = false;
    }

    public void runAsync() {
        active = true;
        runTaskTimerAsynchronously(TTT.getInstance(), 0, 20);
    }

    @Override
    public void run() {

        if(forced) {
            if(!active) {
                active = true;
            }
            if(time > 10) {
                setTime(10);
            }
        }

        if(active) {

            if(time == 60) {

            } else if(time == 30) {
                sendTimerMessage();
            } else if(time == 15) {
                sendTimerMessage();
            } else if(time == 10) {
                sendTimerMessage();
            } else if((time < 6) && (time != 0)) {
                sendTimerMessage();
            } else if(time == 0) {
                CountdownFinishEvent event = new CountdownFinishEvent(this, false);
                if(!event.isCancelled()) {
                    Bukkit.getPluginManager().callEvent(event);
                }
                cancel();
            }
        } else {
            cancel();
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setLevel(time);
        });
        time--;
        Event event = new CountdownTimeChangeEvent(this, time, false);
        Bukkit.getPluginManager().callEvent(event);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public GameManager.GameState getGameState() {
        return gameState;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    @Override
    public synchronized int getTaskId() throws IllegalStateException {
        return super.getTaskId();
    }

    void sendTimerMessage() {
        Bukkit.getServer().broadcastMessage(timerMessage.replaceAll("%time%", String.valueOf(time)));
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        Bukkit.getServer().broadcastMessage(Messages.PREFIX + "§7Der Countdown wurde beendet!");
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void runForcedStart() {

        if(!forced) {
            forced = true;
        }

        if(!active) {
            active = true;
            runAsync();
        }
    }


}
