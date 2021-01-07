package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemoveSpawn implements CommandExecutor {

    private final String NAME = "removespawm";

    public CommandRemoveSpawn() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
        TTT.getInstance().getCommand(NAME).setPermission("ttt." + NAME);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!TTT.core.getGameManager().inLobby() ||
                TTT.core.getTimerManager().getLobbyCountdown().isActive()) {
            player.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        if(args.length < 1) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        String idArg = args[0];

        if(!isNumeric(idArg)) {
            player.sendMessage(Messages.ATTRIBUTE_NOT_NUMERIC);
            return true;
        }

        int id = Integer.parseInt(idArg);

        if(!TTT.core.getSpawnManager().existsID(id)) {
            player.sendMessage(Messages.ID_NOT_EXISTS);
            return true;
        }

        Spawn spawn = TTT.core.getSpawnManager().getSpawn(id);
        TTT.core.getSpawnManager().delete(spawn);

        player.sendMessage(Messages.SPAWN_DELETED + id);

        return false;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
