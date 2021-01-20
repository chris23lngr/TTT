package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.DBPlayer;
import de.z1up.ttt.util.uuid.UUIDFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.UUID;

public class CommandStats implements CommandExecutor {

    private final String NAME = "stats";

    public CommandStats() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(!TTT.core.getPlayerManager().exists(player)) {
            player.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        UUID targetUUID = player.getUniqueId();

        if(args.length > 0) {

            String targetName = args[0];

            if(!UUIDFetcher.existsPlayer(targetName)) {
                player.sendMessage(Messages.PLAYER_NOT_EXISTS);
                return true;
            }

            if(!TTT.core.getPlayerManager().exists(targetUUID)) {
                player.sendMessage(Messages.PLAYER_NOT_EXISTS);
                return true;
            }

            targetUUID = UUIDFetcher.getUUID(targetName);


        }

        DBPlayer dbTarget = (DBPlayer) TTT.core.getPlayerManager().get(targetUUID);

        int kills = dbTarget.getKills();
        int deaths = dbTarget.getDeaths();
        int wins = dbTarget.getWins();
        int looses = dbTarget.getLooses();
        int karma = dbTarget.getKarma();

        double kd = (double)kills / (double)kills;
        NumberFormat n = NumberFormat.getInstance();
        n.setMaximumFractionDigits(2);


        String prefix = Messages.PREFIX;

        player.sendMessage(prefix + "§8§m------------------§r§8 §8│ §cStats §8│ §8§m------------------");
        player.sendMessage(prefix + "§7Spieler §8■ §c" + UUIDFetcher.getName(targetUUID));
        player.sendMessage(prefix + "§7Ranking §8■ §c" + "§4Feature folgt...");
        player.sendMessage(prefix + "§7Kills §8■ §c" + kills);
        player.sendMessage(prefix + "§7Tode §8■ §c" + deaths);
        player.sendMessage(prefix + "§7K/D §8■ §c" + kd);
        player.sendMessage(prefix + "§7Karma §8■ §c" + karma);
        player.sendMessage(prefix + "§7Spiele gewonnen §8■ §c" + wins);
        player.sendMessage(prefix + "§7Spiele verloren §8■ §c" + looses);
        player.sendMessage(prefix + "§8§m-------------------------------------------");

        return false;
    }
}
