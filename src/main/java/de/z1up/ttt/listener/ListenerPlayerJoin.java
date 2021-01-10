package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
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

        if(!TTT.core.getPlayerManager().exists(player)) {
            DBPlayer dbPlayer = new DBPlayer(player.getUniqueId(), 0,
                    0, 5, 5, 0, 1000, 0,
                    0, null, 0, 0);
            dbPlayer.insert();
        }

        if(TTT.core.getGameManager().inLobby()) {

            event.setJoinMessage(Messages.PREFIX + "§c" + player.getName()
                    + " §7ist dem Spiel beigetreten§8.");


            TTT.core.getInvManager().setLobbyItems(player);

            UserAPI.playJoinEffects(player);

            //Data.sbManager.setScoreBoard(player, Data.gameManager.getGameState());

            TTT.core.getTimerManager().checkPlayersForStart();

            return;
        }

        if(TTT.core.getGameManager().inGame() || TTT.core.getGameManager().inSavePhase()) {

            event.setJoinMessage(null);

            TTT.core.getPlayerManager().addSpectator(player);
            TTT.core.getPlayerManager().enterSpecMode(player);

            return;
        }

    }

}
