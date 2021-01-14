package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetSpecSpawn implements CommandExecutor {

    private final String NAME = "setspecspawn";

    public CommandSetSpecSpawn() {
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

        if (args.length < 1) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        String mapName = args[0];

        if(!TTT.core.getMapManager().existsName(mapName)) {
            player.sendMessage(Messages.MAP_NOT_EXIST);
            return true;
        }

        Map map = TTT.core.getMapManager().getMap(mapName);
        int id = map.getId();

        Spawn specSpawn = new Spawn(id, player.getLocation(), map);
        TTT.core.getSpawnManager().insertSpecSpawn(specSpawn);

        player.sendMessage(Messages.PREFIX + "§aSpawn für Spectator auf Map §b" + map.getName() + " §agesetzt§8!");


        return true;
    }

}
