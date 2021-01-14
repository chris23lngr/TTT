package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerBecomeSpecEvent;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.UserAPI;
import de.z1up.ttt.util.o.DBPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

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

            player.teleport(TTT.core.getSpawnManager().getLobbySpawn().getLocation());

            TTT.core.getTimerManager().checkPlayersForStart();

            return;
        }

        if(TTT.core.getGameManager().inGame() || TTT.core.getGameManager().inSavePhase()) {

            event.setJoinMessage(null);

            PlayerBecomeSpecEvent becomeSpecEvent = new PlayerBecomeSpecEvent(player, false);
            Bukkit.getPluginManager().callEvent(becomeSpecEvent);

            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getPlayer().teleport(TTT.core.getSpawnManager().getSpecSpawnFor(TTT.core.getMapManager().getMapToPlay()).getLocation());
                }
            }.runTaskLater(TTT.getInstance(), 15);

            return;
        }

    }

}
