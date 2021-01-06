package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

        ItemStack itemStack = player.getItemInHand();

        if(itemStack == null || itemStack.getType() == Material.AIR) {
            player.sendMessage(Data.getPrefix() + "§7Du musst ein Item in deiner Hand halten.");
            return true;
        }

        if(itemStack.getType() == null) {
            player.sendMessage(Data.getPrefix() + "§7Material ist invalide.");
            return true;
        }

        String mapName = args[0];

        if(Data.mapManager.existsName(mapName)) {
            player.sendMessage(Data.getPrefix() + "§cEs exestiert bereits eine Map mit diesem Namen!");
            return true;
        }

        Material material = itemStack.getType();
        int id = Data.mapManager.createID();
        Map map = new Map(id, mapName, material, null, null);

        Data.mapManager.registerMap(map);
        player.sendMessage(Data.getPrefix() + "§7Map §e" + mapName + " §7wurde erstellt.");

        return false;
    }
}
