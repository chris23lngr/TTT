package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSetSpawn implements CommandExecutor, TabCompleter {

    private final String NAME = "setspawn";

    public CommandSetSpawn() {
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

        if (!TTT.core.gameManager.inLobby()) {
            player.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        if(args.length < 1) {
            player.sendMessage(Messages.WRON_USAGE + command.getUsage());
            return true;
        }

        String mapName = args[0];

        if(!TTT.core.mapManager.existsName(mapName)) {
            player.sendMessage(Messages.MAP_NOT_EXIST);
            return true;
        }

        Location location = player.getLocation();
        int id = TTT.core.spawnManager.createID();
        Map map = TTT.core.mapManager.getMap(mapName);

        Spawn spawn = new Spawn(id, location, map);
        TTT.core.spawnManager.insert(spawn);

        player.sendMessage(Messages.SPAWN_SET + map.getName());

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<Map> playableMaps = TTT.core.mapManager.getPossibleMaps();

        List<String> completes = new ArrayList<>();
        playableMaps.forEach(map -> completes.add(map.getName()));

        return completes;
    }
}
