package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHolo implements CommandExecutor {

    private final String NAME = "setholo";

    public CommandSetHolo() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!TTT.core.getGameManager().inLobby()) {
            player.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        Location location = player.getLocation();
        TTT.core.getLocationManager().setHoloLocation(location);

        player.sendMessage(Messages.HOLO_SET);

        return true;
    }
}
