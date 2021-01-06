package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMap implements CommandExecutor {

    public CommandMap() {
        TTT.getInstance().getCommand("map").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("ttt.map")) {
            player.sendMessage(Data.getNoperm());
            return true;
        }

        if(Data.gameManager.inLobby()) {
            player.sendMessage(Data.getPrefix() + "§7");
            return true;
        }

        if(!Data.mapManager.isMapSet()) {
            player.sendMessage(Data.getPrefix() + "§cEs wurde keine Map gefunden.");
            return true;
        }

        Map map = Data.mapManager.getMapToPlay();

        player.sendMessage(Data.getPrefix() + "§7Es wird gespielt auf §c" + map.getName());

        return false;
    }
}
