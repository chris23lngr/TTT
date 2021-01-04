package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.GameStateChangeEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Iterator;

public class ListenerGameStateChange implements Listener {

    public ListenerGameStateChange() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final GameStateChangeEvent event) {

        GameManager.GameState from = event.getFrom();
        GameManager.GameState to = event.getTo();

        if((from == GameManager.GameState.LOBBYPHASE) && (to == GameManager.GameState.SCHUTZPHASE)) {

            Iterator players = Bukkit.getOnlinePlayers().iterator();

            while (players.hasNext()) {
                Player player = (Player) players.next();
                if(!Data.playerManager.isSpec(player)) {

                }
            }
        }

    }

}
