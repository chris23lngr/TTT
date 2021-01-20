package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.GameStateChangeEvent;
import de.z1up.ttt.event.PlayerBecomeSpecEvent;
import de.z1up.ttt.event.PlayerKillEnemyEvent;
import de.z1up.ttt.event.PlayerKillMateEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.DeadBody;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerPlayerDeath implements Listener {

    public ListenerPlayerDeath() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerDeathEvent event) {

        // set death message to null so
        // that no one sees that a player
        // died
        event.setDeathMessage(null);

        Player target = event.getEntity();

        // clear the drops
        event.getDrops().clear();

        // Respawn the player after he died
        /*new BukkitRunnable() {
            @Override
            public void run() {
                target.spigot().respawn();
            }
        }.runTaskLater(TTT.getInstance(), 10);
         */

        // spawn a dead body for the target
        spawnDeadBody(event);

        // send a death message to the
        // target
        sendDeathMessage(event);

        // if there is a killer, call the
        // PlayerKillEnemy- or the Player-
        // KillMateEvent
        if(target.getKiller() != null) {

            // call method
            callKillEvent(event);

        }

        TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(target);

        if(tttPlayer.getTeam() == ManagerTeam.Team.TRAITOR) {
            TTT.core.getTeamManager().removeTraitor();
        } else if(tttPlayer.getTeam() == ManagerTeam.Team.INNOCENT) {
            TTT.core.getTeamManager().removeInnocent();
        } else if(tttPlayer.getTeam() == ManagerTeam.Team.DETECTIVE) {
            TTT.core.getTeamManager().removeDetective();
        }

        target.spigot().respawn();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (TTT.core.getGameManager().inGame()) {
                    target.teleport(
                            TTT.core.getSpawnManager().getSpecSpawnFor(TTT.core.getMapManager().getMapToPlay()).getLocation());
                } else {
                    target.teleport(TTT.core.getLocationManager().getLobbyLocation());
                }
            }
        }.runTaskLater(TTT.getInstance(), 15);

        PlayerBecomeSpecEvent specEvent = new PlayerBecomeSpecEvent(target, false);
        Bukkit.getPluginManager().callEvent(specEvent);

        if(TTT.core.getGameManager().checkPossibleGameEnding()) {
            TTT.core.getGameManager().setGameState(GameManager.GameState.RESTART);
            GameStateChangeEvent changeEvent = new GameStateChangeEvent(GameManager.GameState.INGAME, GameManager.GameState.RESTART, false);
            Bukkit.getPluginManager().callEvent(changeEvent);
        }

    }

    private void callKillEvent(PlayerDeathEvent context) {

        Player target = context.getEntity();

        Player killer = target.getKiller();
        TTTPlayer tttKiller = TTT.core.getPlayerManager().getTTTPlayer(killer);

        ManagerTeam.Team killerTeam = tttKiller.getTeam();

        TTTPlayer tttTarget = TTT.core.getPlayerManager().getTTTPlayer(target);
        ManagerTeam.Team targetTeam = tttTarget.getTeam();

        PlayerKillMateEvent killMateEvent = new PlayerKillMateEvent(killer, target, false);
        PlayerKillEnemyEvent killEnemyEvent = new PlayerKillEnemyEvent(killer, target, false);

        if(killerTeam == targetTeam) {
            Bukkit.getPluginManager().callEvent(killMateEvent);
        } else if((killerTeam == ManagerTeam.Team.DETECTIVE) && (targetTeam == ManagerTeam.Team.INNOCENT)) {
            Bukkit.getPluginManager().callEvent(killMateEvent);
        } else if((killerTeam == ManagerTeam.Team.INNOCENT) && (targetTeam == ManagerTeam.Team.DETECTIVE)) {
            Bukkit.getPluginManager().callEvent(killMateEvent);
        } else if((killerTeam == ManagerTeam.Team.TRAITOR) && (targetTeam == ManagerTeam.Team.INNOCENT || targetTeam == ManagerTeam.Team.DETECTIVE)) {
            Bukkit.getPluginManager().callEvent(killEnemyEvent);
        }  else if((killerTeam == ManagerTeam.Team.INNOCENT || killerTeam == ManagerTeam.Team.DETECTIVE) && (targetTeam == ManagerTeam.Team.TRAITOR)) {
            Bukkit.getPluginManager().callEvent(killEnemyEvent);
        }

    }

    private void spawnDeadBody(PlayerDeathEvent context) {

        Player target = context.getEntity();
        TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(target);

        DeadBody deadBody = new DeadBody(target.getUniqueId(), tttPlayer.getTeam(), null, target.getLocation(), false);

        if(target.getKiller() != null) {
            if (target.getKiller() instanceof Player) {
                deadBody = new DeadBody(target.getUniqueId(), tttPlayer.getTeam(), target.getKiller().getUniqueId(), target.getLocation(), false);
            }
        }

        deadBody.spawn();
        TTT.core.getPlayerManager().addDeadBody(deadBody);

    }

    private void sendDeathMessage(PlayerDeathEvent context) {

        Player target = context.getEntity();

        String deathsMsg = "";

        if(target.getKiller() != null) {
            deathsMsg = Messages.PREFIX + "§cDu wurdest von §4" + target.getKiller().getName() + " §cgetötet§8!";
        } else {
            deathsMsg = Messages.PREFIX + "§cDu bist gestorben§8!";
        }

        target.sendMessage(deathsMsg);

    }

}
