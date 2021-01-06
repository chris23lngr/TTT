package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Map;
import javafx.scene.media.MediaErrorEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMap implements CommandExecutor {

    private final String NAME = "map";

    public CommandMap() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if(TTT.core.gameManager.inLobby() || !TTT.core.mapManager.isMapSet()) {
            player.sendMessage(Messages.MAP_NOT_SET);
            return true;
        }


        Map map = TTT.core.mapManager.getMapToPlay();
        player.sendMessage(Messages.PLAYING_ON + map.getName());

        return true;
    }
}
