package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.TimerTimeChangeEvent;
import de.z1up.ttt.event.MapSetEvent;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.scheduler.TTTRunnable;
import de.z1up.ttt.util.MessageAPI;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerTimerTimeChange implements Listener {

    public ListenerTimerTimeChange() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final TimerTimeChangeEvent event) {

        TTTRunnable task = event.getRunnable();

        if(task.getGameState() == GameManager.GameState.LOBBYPHASE) {

            Bukkit.getOnlinePlayers().forEach(player -> {
                player.setLevel(task.getTime());
            });

            if(task.getTime() == 59) {

                // activate everyones
                TTT.core.getVoteManager().setVotePeriodActive(true);
            } else if(task.getTime() == 5) {
                TTT.core.getVoteManager().setVotePeriodActive(false);

                // close everyones inventory so that
                // they can't vote anymore
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.closeInventory();
                });

                Map map;

                if(!TTT.core.getMapManager().isMapSet()) {
                    map = TTT.core.getMapManager().getVotedMap();
                } else {
                    map = TTT.core.getMapManager().getMapToPlay();
                }

                Event mapSetEvent = new MapSetEvent(map, false);
                Bukkit.getPluginManager().callEvent(mapSetEvent);
            }

            return;
        }

        if(task.getGameState() == GameManager.GameState.PRE_SAVEPHASE) {

            String msg = "§6Go!";

            if(task.getTime() == 5) {
                msg = "§45";
            } else if(task.getTime() == 4) {
                msg = "§c4";
            } else if(task.getTime() == 3) {
                msg = "§e3";
            } else if(task.getTime() == 2) {
                msg = "§a2";
            } else if(task.getTime() == 1) {
                msg = "§21";
            }

            String finalMsg = msg;
            Bukkit.getOnlinePlayers().forEach(player -> {
                MessageAPI.sendTitle(player, 2, 16, 2, finalMsg, "");
            });

            return;
        }

        if(task.getGameState() == GameManager.GameState.SAVEPHASE) {

            String msg = Messages.PREFIX + "§7Shutzphase endet in §c" + task.getTime() + " §7Sekunden...";
            Bukkit.getOnlinePlayers().forEach(player -> {
                MessageAPI.sendActionBar(player, msg);
            });

            return;
        }

    }

}
