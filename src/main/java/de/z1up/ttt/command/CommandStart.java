package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandStart implements CommandExecutor {

    private final String NAME = "start";

    public CommandStart() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
        TTT.getInstance().getCommand(NAME).setPermission("ttt." + NAME);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!TTT.core.getGameManager().inLobby() ||
                TTT.core.getTimerManager().getLobbyTimer().getTime() <= 10) {
            sender.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        if(TTT.core.getTimerManager().getLobbyTimer().isForced()) {
            sender.sendMessage(Messages.FS_ALREADY_ACTIVATED);
            return true;
        }

        TTT.core.getTimerManager().getLobbyTimer().runForcedStart();

        sender.sendMessage(Messages.FS_SUCCESS);

        return false;
    }
}