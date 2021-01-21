package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import javax.print.attribute.standard.MediaSize;

public class CommandSetStatsWand implements CommandExecutor {

    private final String NAME = "setstatswand";

    public CommandSetStatsWand() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
        TTT.getInstance().getCommand(NAME).setPermission("ttt." + NAME);
        TTT.getInstance().getCommand(NAME).setUsage("/" + NAME + " <ID>");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        String arg = args[0];

        if (!isNumeric(arg)) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        Block block = getBlockLookingAt(player);

        if(block == null ) {
            player.sendMessage(Messages.PREFIX + "§7Es konnte kein Kopf gefunden werden§8!");
            return true;
        }


        if(block.getType() != Material.SKULL) {
            player.sendMessage(Messages.PREFIX + "§cBlock muss ein Kopf sein§8!");
            return true;
        }

        /*
        Block sign = block.getLocation().subtract(0, 1, 0).getBlock();

        if(sign.getType() != Material.SIGN) {
            player.sendMessage(Messages.PREFIX + "§cBlock unter Kopf muss ein Schild sein§8!");
            return true;
        }

         */


        int id = Integer.parseInt(arg);
        Location location = block.getLocation();

        TTT.core.getLocationManager().setStatsLocation(location, id);
        player.sendMessage(Messages.PREFIX + "§7Stats Wand mit id §c" + id + " §7wurde gesetzt§8!");

        return true;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private Block getBlockLookingAt(Player player) {
        BlockIterator blocks = new BlockIterator(player, 10);

        Block target = null;

        while (blocks.hasNext()) {

            Block block = blocks.next();

            if (block.getType() != Material.AIR) {
                target = block;
                break;
            }

        }
        return target;
    }

}