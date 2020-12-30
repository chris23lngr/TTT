package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBuild implements CommandExecutor {

    public CommandBuild() {
        TTT.getInstance().getCommand("build").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("ttt.build")) {
            player.sendMessage(Data.getNoperm());
            return true;
        }

        if(Data.buildManager.canBuild(player)) {
            player.sendMessage(Data.getPrefix() + "§cDu befindest dich jetzt nicht mehr im Baumodus!");
            Data.buildManager.disallowBuilding(player);
            player.setGameMode(GameMode.SURVIVAL);
        } else {
            player.sendMessage(Data.getPrefix() + "§aDu befindest dich jetzt im Baumodus!");
            Data.buildManager.allowBuilding(player);
            player.setGameMode(GameMode.CREATIVE);
        }

        return false;
    }
}
