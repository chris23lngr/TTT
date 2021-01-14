package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetLobbySpawn implements CommandExecutor {

    private final String NAME = "setlobbyspawn";

    public CommandSetLobbySpawn() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
        TTT.getInstance().getCommand(NAME).setPermission("ttt." + NAME);
        TTT.getInstance().getCommand(NAME).setUsage("/" + NAME + " <Map>");

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

        Spawn specSpawn = new Spawn(0000, player.getLocation(), null);
        TTT.core.getSpawnManager().insertLobbySpawn(specSpawn);

        player.sendMessage(Messages.PREFIX + "§aLobbyspawn gesetzt§8!");

        return true;
    }

}
