package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.UserAPI;
import de.z1up.ttt.util.o.DBPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerPlayerJoin implements Listener {

    public ListenerPlayerJoin() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        UserAPI.resetPlayer(player);

        if(!Core.wrapperPlayer.existsPlayer(player.getUniqueId())) {
            DBPlayer dbPlayer = new DBPlayer(player.getUniqueId(), 0,
                    0, 5, 5, 0, 1000, 0,
                    0, null, 0, 0);
            dbPlayer.insert();
        }

        if(Core.gameManager.inLobby()) {

            event.setJoinMessage(Core.getPrefix() + "§c" + player.getName()
                    + " §7ist dem Spiel beigetreten§8.");


            Core.invManager.setLobbyItems(player);

            UserAPI.playJoinEffects(player);

            //Data.sbManager.setScoreBoard(player, Data.gameManager.getGameState());

            Core.timerManager.checkPlayersForStart();

            return;
        }

        if(Core.gameManager.inGame() || Core.gameManager.inSavePhase()) {

            event.setJoinMessage(null);

            Core.playerManager.addSpectator(player);
            Core.playerManager.enterSpecMode(player);

            return;
        }

    }

}
