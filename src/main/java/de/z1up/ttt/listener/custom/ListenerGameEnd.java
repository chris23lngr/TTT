package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.GameEndEvent;
import de.z1up.ttt.manager.GameManager;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.MessageAPI;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.UserAPI;
import de.z1up.ttt.util.o.TTTPlayer;
import de.z1up.ttt.util.uuid.UUIDFetcher;
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

        String title = "§7Die ";
        String subtitle = "§7haben gewonnen§8!";

        if(result.equals(GameManager.GameResult.TRAITOR_WIN)) {

            title = title + "§4Traitor";

        } else if(result.equals(GameManager.GameResult.INNOCENT_WIN)) {

            title = title + "§aInnocents";

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
            player.teleport(TTT.core.getLocationManager().getLobbyLocation());
            UserAPI.resetPlayer(player);

        }

        sendTraitorsMessage();

        for(Player player : Bukkit.getOnlinePlayers()) {

            if(TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {

                TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(player);

                boolean won = false;

                if(result == GameManager.GameResult.INNOCENT_WIN) {

                    if(tttPlayer.getTeam() == ManagerTeam.Team.TRAITOR) {
                        won = false;
                    } else {
                        won = true;
                    }

                } else if(result == GameManager.GameResult.TRAITOR_WIN) {

                    if(tttPlayer.getTeam() == ManagerTeam.Team.TRAITOR) {
                        won = true;
                    } else {
                        won = false;
                    }

                }

                if(won) {
                    tttPlayer.addKarma(20);
                    tttPlayer.addWin();
                    tttPlayer.update();
                } else {
                    tttPlayer.removeKarma(20);
                    tttPlayer.addLoose();
                    tttPlayer.update();
                }

                player.sendMessage(Messages.PREFIX + "§8§m--------- §eRunden Stats §8§m---------");
                player.sendMessage(Messages.PREFIX + "§7Kills: §c" + tttPlayer.getGameKills());
                player.sendMessage(Messages.PREFIX + "§7Karma: §c" + tttPlayer.getGameKarma());
                player.sendMessage(Messages.PREFIX + "§7Du hast " + (won ? "§aGewonnen§8!" : "§cVerloren§8!"));
                player.sendMessage(Messages.PREFIX + "§8§m------------------------------------------");



            }

        }

    }

    private void sendTraitorsMessage() {

        String message = Messages.PREFIX + "§7Die §4Traitor §7waren§8: ";

        for(TTTPlayer player : TTT.core.getPlayerManager().getTTTPlayers().values()) {

            if(player.getTeam() == ManagerTeam.Team.TRAITOR) {

                message = message + "§4" + UUIDFetcher.getName(player.getUuid()) + "§8, ";

            }

        }

        Bukkit.broadcastMessage(message);

    }

}
