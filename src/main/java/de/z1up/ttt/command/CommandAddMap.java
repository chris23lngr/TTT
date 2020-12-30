package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.manager.MapManager;
import de.z1up.ttt.util.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddMap implements CommandExecutor {

    public CommandAddMap() {
        TTT.getInstance().getCommand("addmap").setExecutor(this::onCommand);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("ttt.addmap")) {
            player.sendMessage(Data.getNoperm());
            return true;
        }

        if(args.length < 1) {
            player.sendMessage(Data.getPrefix() + "§7Bitte benutze §c/addmap <MapName>");
            return true;
        }

        String mapName = args[0];

        if(Data.mapManager.existsMap(mapName)) {
            player.sendMessage(Data.getPrefix() + "§cDiese Map exestiert bereits!");
            return true;
        }

        Data.mapManager.registerNewMap(mapName);
        player.sendMessage(Data.getPrefix() + "§7Die Map §c" + mapName + " §7wurde erfolgreich regestriert§8.");

        return false;
    }
}
