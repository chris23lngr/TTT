package de.z1up.ttt.scheduler;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.TimerFinishEvent;
import de.z1up.ttt.event.TimerStartEvent;
import de.z1up.ttt.event.TimerTimeChangeEvent;
import de.z1up.ttt.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;

public class TTTRunnable extends BukkitRunnable {

    private int time;
    private boolean forced;
    private GameManager.GameState gameState;
    private String timerMessage;
    private boolean active;

    public TTTRunnable(int time, boolean forced, GameManager.GameState gameState, String timerMessage) {
        this.time = time;
        this.forced = forced;
        this.gameState = gameState;
        this.timerMessage = timerMessage;
        this.active = false;
    }

    public void runAsync() {
        active = true;

        // call the TimerStartEvent
        // when the timer is started
        TimerStartEvent event = new TimerStartEvent(this, false);
        Bukkit.getPluginManager().callEvent(event);

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

            if(timerMessage != null && !timerMessage.equalsIgnoreCase(" ")) {
                sendTimerMessage();
            }

          if(time == 0) {

              // call the TimerFinishEvent
              TimerFinishEvent event = new TimerFinishEvent(this, false);
              Bukkit.getPluginManager().callEvent(event);

              cancel();
            }

        } else {

            // call the TimerFinishEvent
            TimerFinishEvent event = new TimerFinishEvent(this, false);
            Bukkit.getPluginManager().callEvent(event);

            cancel();
        }

        // lower the time and call the
        // CountDownTimeChangeEvent
        Event event = new TimerTimeChangeEvent(this, time, false);
        Bukkit.getPluginManager().callEvent(event);

        time--;

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

        String msg = timerMessage.replaceAll("%time%", String.valueOf(time));

        if(time == 60) {
            Bukkit.broadcastMessage(msg);
        } else if(time == 30) {
            Bukkit.broadcastMessage(msg);
        } else if(time == 15) {
            Bukkit.broadcastMessage(msg);
        } else if(time == 10) {
            Bukkit.broadcastMessage(msg);
        } else if((time < 6) && (time != 0)) {
            Bukkit.broadcastMessage(msg);
        }

    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
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
