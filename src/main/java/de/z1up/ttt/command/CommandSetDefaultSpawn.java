package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetDefaultSpawn implements CommandExecutor {

    private final String NAME = "setdefaultspawn";

    public CommandSetDefaultSpawn() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
        TTT.getInstance().getCommand(NAME).setPermission("ttt." + NAME);
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

        if (args.length < 1) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        String spawnName = args[0];

        if (!spawnName.equalsIgnoreCase("lobby") && !spawnName.equalsIgnoreCase("specs")) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        int id = 0;

        if(spawnName.equalsIgnoreCase("specs")) {
            id = 1;
        }

        Location location = player.getLocation();

        Spawn spawn = new Spawn(id, location, null);
        TTT.core.getSpawnManager().insert(spawn);

        player.sendMessage(Messages.SPAWN_SET);

        return false;
    }

}
