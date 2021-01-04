package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandStart implements CommandExecutor {

    public CommandStart() {
        TTT.getInstance().getCommand("start").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("ttt.start")) {
            sender.sendMessage(Data.getNoperm());
            return true;
        }

        if (!Data.gameManager.inLobby()) {
            sender.sendMessage(Data.getPrefix() + "§cKommmand kann nur währen Lobbyphase genutzt werden.");
            return true;
        }

        if(Data.timerManager.getLobbyCountdown().isForced()) {
            sender.sendMessage(Data.getPrefix() + "§cDer §bForce Start §7wurde bereits aktiviert§8!");
            return true;
        }

        Data.timerManager.getLobbyCountdown().runForcedStart();
        sender.sendMessage(Data.getPrefix() + "§aDer §bForce Start §awurde aktiviert§8!");

        return false;
    }
}