package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandForceMap implements CommandExecutor, TabCompleter {

    private final String NAME = "forcemap";

    public CommandForceMap() {
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

        if(args.length < 1) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        String mapName = args[0];

        if(!TTT.core.getMapManager().existsName(mapName)) {
            player.sendMessage(Messages.MAP_NOT_EXIST);
            return true;
        }

        if (!TTT.core.getGameManager().inLobby() || TTT.core.getMapManager().isMapSet()) {
            player.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        // Set the forced map
        Map map = TTT.core.getMapManager().getMap(mapName);
        TTT.core.getMapManager().setMapToPlay(map);

        // Deactivate vote period
        TTT.core.getVoteManager().setVotePeriodActive(false);

        // Send a success message
        player.sendMessage(Messages.FM_SUCCESS + map.getName());

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<Map> playableMaps = TTT.core.getMapManager().getPossibleMaps();

        List<String> completes = new ArrayList<>();
        playableMaps.forEach(map -> completes.add(map.getName()));

        return completes;
    }
}
