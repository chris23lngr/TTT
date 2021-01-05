package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Map;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandForceMap implements CommandExecutor {

    public CommandForceMap() {
        TTT.getInstance().getCommand("forcemap").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("ttt.forcemap")) {
            player.sendMessage(Data.getNoperm());
            return true;
        }

        if(args.length < 1) {
            player.sendMessage(Data.getPrefix() + "§7Bitte nutze §c/forcemap <Map>");
            return true;
        }

        String mapName = args[0];

        if(!Data.mapWrapper.existsName(mapName)) {
            player.sendMessage(Data.getPrefix() + "§cDiese Map exestiert nicht.");
            return true;
        }

        if (!Data.gameManager.inLobby()) {
            player.sendMessage(Data.getPrefix() + "§cDas Spiel hat bereits gestartet!");
            return true;
        }

        if(Data.mapManager.isMapSet()) {
            player.sendMessage(Data.getPrefix() + "§cDie Map wurde bereits gesetzt!");
            return true;
        }

        Map map = (Map) Data.mapWrapper.get(mapName);
        Data.mapManager.setMapToPlay(map);
        player.sendMessage(Data.getPrefix() + "§aDie Map wurde erfolgreich auf §b" + mapName + " §agesetzt.");

        return false;
    }
}
