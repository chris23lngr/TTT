package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerBecomeSpecEvent;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.UserAPI;
import de.z1up.ttt.util.o.DBPlayer;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

        if(!TTT.core.getPlayerManager().exists(player)) {
            register(player);
        }

        setPlayerSettings(player);

        teleport(player);

        if(TTT.core.getGameManager().inLobby()) {
            onPlayerJoin(event);
        } else {
            onSpecJoin(event);
        }
    }

    private void setPlayerSettings(Player player) {

        UserAPI.resetPlayer(player);
        UserAPI.setScoreboard(player);
        UserAPI.setHolo(player);
        UserAPI.setTablistName(player);
        UserAPI.updatePlayerteams();

    }

    private void teleport(final Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {

                Location location = TTT.core.getLocationManager().getLobbyLocation();

                if(TTT.core.getGameManager().inGame()
                        || TTT.core.getGameManager().inPreSavePhase()
                        || TTT.core.getGameManager().inSavePhase()) {

                    if(TTT.core.getMapManager().isMapSet()) {

                        Map map = TTT.core.getMapManager().getMapToPlay();
                        Spawn specSpawn = TTT.core.getSpawnManager().getSpecSpawnFor(map);
                        location = specSpawn.getLocation();

                    }
                }

                player.teleport(location);

            }
        }.runTaskLater(TTT.getInstance(), 10);

    }

    private void onPlayerJoin(final PlayerJoinEvent context) {

        final Player player = context.getPlayer();

        TTT.core.getInvManager().setLobbyItems(player);

        UserAPI.playJoinEffects(player);

        context.setJoinMessage(Messages.PREFIX + "§a" + player.getName()
                + " §7ist dem Spiel beigetreten§8!");

    }

    private void onSpecJoin(final PlayerJoinEvent context) {

        final Player player = context.getPlayer();

        context.setJoinMessage(null);

        PlayerBecomeSpecEvent specEvent = new PlayerBecomeSpecEvent(player, false);
        Bukkit.getPluginManager().callEvent(specEvent);

    }

    private void register(Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {

                DBPlayer dbPlayer = new DBPlayer(player.getUniqueId(), 0,
                        0, 5, 5, 0, 1000, 0,
                        0, null, 0, 0);
                dbPlayer.insert();

            }
        }.runTaskAsynchronously(TTT.getInstance());

    }

}
