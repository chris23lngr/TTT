package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
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

        if(!Data.wrapperPlayer.existsPlayer(player.getUniqueId())) {
            DBPlayer dbPlayer = new DBPlayer(player.getUniqueId(), 0,
                    0, 5, 5, 0, 1000, 0,
                    0, null, 0, 0);
            dbPlayer.insert();
        }

        if(Data.gameManager.inLobby()) {

            event.setJoinMessage(Data.getPrefix() + "§c" + player.getName()
                    + " §7ist dem Spiel beigetreten§8.");


            Data.invManager.setLobbyItems(player);

            UserAPI.playJoinEffects(player);

            //Data.sbManager.setScoreBoard(player, Data.gameManager.getGameState());

            Data.timerManager.checkPlayersForStart();

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
