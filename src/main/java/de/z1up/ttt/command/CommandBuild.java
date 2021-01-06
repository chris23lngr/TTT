package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.print.attribute.standard.MediaSize;

public class CommandBuild implements CommandExecutor {

    private final String NAME = "build";

    public CommandBuild() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
        TTT.getInstance().getCommand(NAME).setPermission("ttt." + NAME);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if(TTT.core.buildManager.canBuild(player)) {
            TTT.core.buildManager.disallowBuilding(player);
        } else {
            TTT.core.buildManager.allowBuilding(player);
        }

        return false;
    }
}
