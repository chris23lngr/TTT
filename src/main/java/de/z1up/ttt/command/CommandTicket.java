package de.z1up.ttt.command;

import de.z1up.ttt.TTT;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandTicket implements CommandExecutor {

    private final String NAME = "ticket";

    public CommandTicket() {
        TTT.getInstance().getCommand(NAME).setExecutor(this::onCommand);
        TTT.getInstance().getCommand(NAME).setPermissionMessage(Messages.NO_PERM);
        TTT.getInstance().getCommand(NAME).setUsage("/" + NAME + " <traitor | innocent | detective>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(!TTT.core.getPlayerManager().exists(player)) {
            player.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        if(!TTT.core.getGameManager().inLobby()) {
            player.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        if(!TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {
            player.sendMessage(Messages.CMD_NOT_EXECUTABLE_ATM);
            return true;
        }

        if(args.length != 1) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        String team = args[0];

        if(!team.equalsIgnoreCase("traitor")
                && !team.equalsIgnoreCase("innocent")
                && !team.equalsIgnoreCase("detective")) {
            player.sendMessage(Messages.WRONG_USAGE + command.getUsage());
            return true;
        }

        TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(player);

        if(tttPlayer.hasTeam()) {
            player.sendMessage(Messages.PREFIX + "§cDu bist bereits in einem Team§8!");
            return true;
        }

        int tPasses = tttPlayer.getTPasses();
        int dPasses = tttPlayer.getDPasses();
        int iPasses = tttPlayer.getIPasses();

        if(team.equalsIgnoreCase("traitor")) {

            if(!(tPasses > 0)) {
                player.sendMessage(Messages.PREFIX + "§cDu hast nicht genügend Pässe§8!");
                return true;
            }

            if(TTT.core.getTeamManager().getTraitorCounter() == TTT.core.getTeamManager().getTraitorsNeeded()) {
                player.sendMessage(Messages.PREFIX + "§cEs wurden bereits alle Pässe eingelöst§8!");
                return true;

            }

            tttPlayer.removeTPass();
            tttPlayer.setTeam(ManagerTeam.Team.TRAITOR);
            TTT.core.getTeamManager().addTraitor();
            tttPlayer.update();

            player.sendMessage(Messages.PREFIX + "§aDu wirst als §4Traitor §aspielen§8!");
            return true;

        }

        if(team.equalsIgnoreCase("innocent")) {

            if(!(iPasses > 0)) {
                player.sendMessage(Messages.PREFIX + "§cDu hast nicht genügend Pässe§8!");
                return true;
            }

            if(TTT.core.getTeamManager().getInnocentCounter() == TTT.core.getTeamManager().getInnocentsNeeded()) {
                player.sendMessage(Messages.PREFIX + "§cEs wurden bereits alle Pässe eingelöst§8!");
                return true;

            }

            tttPlayer.removeIPass();
            tttPlayer.setTeam(ManagerTeam.Team.INNOCENT);
            TTT.core.getTeamManager().addInnocent();
            tttPlayer.update();

            player.sendMessage(Messages.PREFIX + "§aDu wirst als Innocent spielen§8!");
            return true;

        }

        if(team.equalsIgnoreCase("detective")) {

            if(!(dPasses > 0)) {
                player.sendMessage(Messages.PREFIX + "§cDu hast nicht genügend Pässe§8!");
                return true;
            }

            if(TTT.core.getTeamManager().getDetectiveCounter() == TTT.core.getTeamManager().getDetectivesNeeded()) {
                player.sendMessage(Messages.PREFIX + "§cEs wurden bereits alle Pässe eingelöst§8!");
                return true;

            }

            tttPlayer.removeDPass();
            tttPlayer.setTeam(ManagerTeam.Team.DETECTIVE);
            TTT.core.getTeamManager().addDetective();
            tttPlayer.update();

            player.sendMessage(Messages.PREFIX + "§aDu wirst als §9Detective §aspielen§8!");
            return true;

        }

        player.sendMessage(Messages.PREFIX + "§4Invalides Team§8!");

        return false;
    }
}
