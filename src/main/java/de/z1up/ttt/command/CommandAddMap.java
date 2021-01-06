package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandAddMap implements CommandExecutor {

    private final String NAME = "addmap";

    public CommandAddMap() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
        TTT.getInstance().getCommand(NAME).setPermission("ttt." + NAME);
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if(args.length < 1) {
            player.sendMessage(Messages.WRON_USAGE + command.getUsage());
            return true;
        }

        ItemStack itemStack = player.getItemInHand();

        if(itemStack == null || itemStack.getType() == Material.AIR) {
            player.sendMessage(Messages.AM_NO_ITEM_IN_HAND);
            return true;
        }

        if(itemStack.getType() == null) {
            player.sendMessage(Messages.AM_MAT_INVALID);
            return true;
        }

        String mapName = args[0];

        if(TTT.core.mapManager.existsName(mapName)) {
            player.sendMessage(Messages.AM_MAP_ALREADY_EXIST);
            return true;
        }

        Material material = itemStack.getType();
        int id = TTT.core.mapManager.createID();

        Map map = new Map(id, mapName, material, null, null);
        TTT.core.mapManager.registerMap(map);

        player.sendMessage(Messages.AM_SUCCESS + mapName);

        return false;
    }
}
