package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.DBPlayer;
import de.z1up.ttt.util.uuid.UUIDFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        DBPlayer dbPlayer = (DBPlayer) TTT.core.getPlayerManager().get(targetUUID);

        player.sendMessage(Messages.PREFIX + "§7Stats von §e" + UUIDFetcher.getName(targetUUID));

        int kills = dbPlayer.getKills();
        int wins = dbPlayer.getWins();
        int looses = dbPlayer.getLooses();
        int karma = dbPlayer.getKarma();

        player.sendMessage("§7Kills§8: §e" + kills);
        player.sendMessage("§7Wins§8: §e" + wins);
        player.sendMessage("§7Looses§8: §e" + looses);
        player.sendMessage("§7karma§8: §e" + karma);


        return false;
    }
}
