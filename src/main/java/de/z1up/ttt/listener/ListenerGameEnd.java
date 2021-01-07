package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.GameEndEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerGameEnd implements Listener {

    public ListenerGameEnd() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final GameEndEvent event) {

        GameManager.GameResult result = event.getGameResult();

        if(result.equals(GameManager.GameResult.TRAITOR_WIN)) {

            Bukkit.broadcastMessage(Messages.WIN_TRAITOR);

        } else if(result.equals(GameManager.GameResult.INNOCENT_WIN)) {

            Bukkit.broadcastMessage(Messages.WIN_INNO);

        } else {

            Bukkit.broadcastMessage(Messages.WIN_DRAW);

        }

    }

}
