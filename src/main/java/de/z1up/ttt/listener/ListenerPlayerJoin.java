package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.MessageAPI;
import de.z1up.ttt.util.UserAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.internal.runners.statements.RunAfters;

public class ListenerPlayerJoin implements Listener {

    public ListenerPlayerJoin() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        UserAPI.resetPlayer(player);

        if(Data.gameManager.inLobby()) {

            event.setJoinMessage(Data.getPrefix() + "§c" + player.getName()
                    + " §7ist dem Spiel beigetreten§8.");


            Data.invManager.setLobbyItems(player);

            UserAPI.playJoinEffects(player);

            Data.sbManager.setScoreBoard(player, Data.gameManager.getGameState());

            return;
        }

        if(Data.gameManager.inGame() || Data.gameManager.inSavePhase()) {

            event.setJoinMessage(null);

            Data.playerManager.addSpectator(player);
            Data.playerManager.enterSpecMode(player);

            return;
        }

    }

}
