package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemoveSpawn implements CommandExecutor {

    public CommandRemoveSpawn() {
        TTT.getInstance().getCommand("removespawn").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("ttt.removespawn")) {
            sender.sendMessage(Data.getNoperm());
            return true;
        }

        if(!(sender instanceof Player)) {
            sender.sendMessage(Data.getPrefix() + "§cKommmand kann nur von Spielern genutzt werden.");
            return true;
        }

        Player player = (Player) sender;

        if (!Data.gameManager.inLobby()) {
            player.sendMessage(Data.getPrefix() + "§cKommmand kann nur währen Lobbyphase genutzt werden.");
            return true;
        }

        if(!Data.buildManager.canBuild(player)) {
            player.sendMessage(Data.getPrefix() + "§cKommmand kann nur im Baumodus genutzt werden.");
            return true;
        }

        if(Data.timerManager.getLobbyCountdown().isActive()) {
            player.sendMessage(Data.getPrefix() + "§cKommmand kann nicht genutzt werden, während ein Countdown läuft.");
            return true;
        }

        if(args.length < 1) {
            player.sendMessage(Data.getPrefix() + "§7Bitte nutze §c/removespawn <id>");
            return true;
        }

        String idArg = args[0];

        if(!isNumeric(idArg)) {
            player.sendMessage(Data.getPrefix() + "§cDie Spawn ID muss eine nummer sein.");
            return true;
        }

        int id = Integer.parseInt(idArg);

        if(!Data.spawnManager.existsID(id)) {
            player.sendMessage(Data.getPrefix() + "§cDiese Spawn ID exestiert nicht.");
            return true;
        }

        Spawn spawn = Data.spawnManager.getSpawn(id);
        Data.spawnManager.delete(spawn);

        player.sendMessage(Data.getPrefix() + "§7Der Spawn mit der id §c" + id + " §7wurde gelöscht§8.");

        return false;
    }

    public static boolean isNumeric(String strNum) {
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
}
