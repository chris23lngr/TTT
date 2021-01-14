package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.GameEndEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.util.MessageAPI;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.UserAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Iterator;

public class ListenerGameEnd implements Listener {

    public ListenerGameEnd() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final GameEndEvent event) {

        GameManager.GameResult result = event.getGameResult();

        String title = "§eDie ";
        String subtitle = "§a haben gewonnen!";

        if(result.equals(GameManager.GameResult.TRAITOR_WIN)) {

            title = title + "§4Traitor";

        } else if(result.equals(GameManager.GameResult.INNOCENT_WIN)) {

            title = title + "§4Traitor";

        } else {

            title = "§cUnentschieden§8!";
            subtitle = "§eEs gibt keinen Gewinner§8!";

        }

        String finalSubtitle = subtitle;
        String finalTitle = title;

        Iterator players = Bukkit.getOnlinePlayers().iterator();



        while (players.hasNext()) {

            Player player = (Player) players.next();
            MessageAPI.sendTitle(player, 10, 40, 10, finalTitle, finalSubtitle);
            player.teleport(TTT.core.getSpawnManager().getLobbySpawn().getLocation());
            UserAPI.resetPlayer(player);

        }

    }

}
