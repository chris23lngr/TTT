package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerTeamSetEvent;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.FakeEquipment;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ListenerPlayerTeamSet implements Listener {

    public ListenerPlayerTeamSet() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerTeamSetEvent event) {

        TTTPlayer tttPlayer = event.getTttPlayer();
        UUID uuid = tttPlayer.getUuid();

        ManagerTeam.Team team = event.getTeam();

        if(Bukkit.getPlayer(uuid) != null) {

            Player player = Bukkit.getPlayer(uuid);

            String msg = "";

            if(team == ManagerTeam.Team.TRAITOR) {
                msg = Messages.TEAM_PLAYING_AS_TRAITOR;
            } else if(team == ManagerTeam.Team.DETECTIVE) {
                msg = Messages.TEAM_PLAYING_AS_DETECTIVE;
            } else if(team == ManagerTeam.Team.INNOCENT) {
                msg = Messages.TEAM_PLAYING_AS_INNOCENT;
            }

            player.sendMessage(msg);

            TTT.core.getScoreboardAPI().updateTeamCounter(player);

        }

        Player player = Bukkit.getPlayer(tttPlayer.getUuid());

        TTT.core.getInvManager().setGameItems(Bukkit.getPlayer(tttPlayer.getUuid()));

        if(team == ManagerTeam.Team.INNOCENT) {
            player.setPlayerListName("§a" + player.getName());
        }

        if(team != null) {

            if(team == ManagerTeam.Team.DETECTIVE) {
                TTT.core.getInvManager().setGameItemsShop(player);
                player.setPlayerListName("§9" + player.getName());
            } else if(team == ManagerTeam.Team.TRAITOR) {
                TTT.core.getInvManager().setGameItemsShop(player);
                player.setPlayerListName("§a" + player.getName());
            }
        }

    }

}
