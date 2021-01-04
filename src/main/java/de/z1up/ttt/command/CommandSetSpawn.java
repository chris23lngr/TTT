package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Map;
import de.z1up.ttt.util.o.Spawn;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetSpawn implements CommandExecutor {

    public CommandSetSpawn() {
        TTT.getInstance().getCommand("setspawn").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("ttt.setspawn")) {
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
            player.sendMessage(Data.getPrefix() + "§7Bitte nutze §c/setspawn <Map>");
            return true;
        }

        String mapName = args[0];

        if(!Data.mapWrapper.existsName(mapName)) {
            player.sendMessage(Data.getPrefix() + "§cDiese Map exestiert nicht.");
            return true;
        }

        Location location = player.getLocation();
        int id = Data.spawnWrapper.createID();
        Map map = (Map) Data.mapWrapper.get(mapName);

        Spawn spawn = new Spawn(id, location, map);

        player.sendMessage(Data.getPrefix() + "§7Auf der Map §a" + map.getName() + " §7wurde ein Spawn mit der ID §a" + spawn.getId() + " §7gesetzt§8.");

        return false;
    }
}