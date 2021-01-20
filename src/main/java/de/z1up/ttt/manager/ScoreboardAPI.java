package de.z1up.ttt.manager;


import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import de.dytanic.cloudnet.lib.player.permission.PermissionPool;
import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardAPI implements Manager {

    private ScoreboardManager manager;

    private final String TEAM_PLAYER = "teamPlayer";
    private final String TEAM_MAP = "teamMap";
    private final String TEAM_TEAM = "teamTeam";
    private final String TEAM_REPLAY = "teamReplay";

    private final String TEAM_DETECTIVE = "001DET";
    private final String TEAM_TRAITOR = "002TRAITOR";
    private final String TEAM_INNOCENT = "003INNO";

    public ScoreboardAPI() {
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
        manager = Bukkit.getScoreboardManager();
    }

    public void createScoreboard(Player player) {
        
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(player.getName(), "dummy");
        objective.setDisplayName(Messages.Scoreboards.SB_DISPLAY);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        addFiller(player, scoreboard, "§e", 11);

        addPlayerScore(player, scoreboard);
        addPlayerCounter(player, scoreboard);

        addFiller(player, scoreboard, "§f", 8);

        addMapScore(player, scoreboard);
        addMapCounter(player, scoreboard);

        addFiller(player, scoreboard, "§c", 5);

        addTeamScore(player, scoreboard);
        addTeamCounter(player, scoreboard);

        addFiller(player, scoreboard, "§2", 2);

        addReplayScore(player, scoreboard);
        addReplayCounter(player, scoreboard);

        addPlayerTeam(player, scoreboard);

        player.setScoreboard(scoreboard);


    }

    // ----------------- FILLER -----------------------

    private void addFiller(Player player, Scoreboard scoreboard, String placeholder, int score) {

        Objective objective = scoreboard.getObjective(player.getName());

        Score s = objective.getScore(placeholder);
        s.setScore(score);

    }

    // ----------------- PLAYER -----------------------

    private void addPlayerScore(Player player, Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(player.getName());

        Score players = objective.getScore("§8● §cSpieler");

        if(TTT.core.getGameManager().inGame()) {
            players = objective.getScore("§8● §cÜberlebend");
        }

        players.setScore(10);

    }

    private void addPlayerCounter(Player player, Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(player.getName());

        Team players = scoreboard.registerNewTeam(TEAM_PLAYER);
        players.addEntry(ChatColor.RED + "" + ChatColor.GRAY);

        if(TTT.core.getGameManager().inGame()) {

            players.setPrefix("§8➟ §7" + TTT.core.getPlayerManager().getAlives().size());

        } else {

            if(Bukkit.getOnlinePlayers().size() != 0) {

                players.setPrefix("§8➟ §7" + Bukkit.getOnlinePlayers().size() + "§8/§7" + Bukkit.getMaxPlayers());

            } else {

                players.setPrefix("§8➟ §40§8/§7" + Bukkit.getMaxPlayers());

            }

        }

        objective.getScore(ChatColor.RED + "" + ChatColor.GRAY).setScore(9);

    }

    public void updatePlayerScore(Player player) {

        Objective objective = player.getScoreboard().getObjective(player.getName());

        Score players = objective.getScore("§8● §cSpieler");

        if(TTT.core.getGameManager().inGame()) {
            players = objective.getScore("§8● §cÜberlebend");
        }

        players.setScore(10);

    }

    public void updatePlayerCounter(Player player) {

        Scoreboard scoreboard = player.getScoreboard();

        Team team = scoreboard.getTeam(TEAM_PLAYER);

        if(TTT.core.getGameManager().inGame()) {
            team.setPrefix("§8➟ §7" + TTT.core.getPlayerManager().getAlives().size());
        } else {
            if(Bukkit.getOnlinePlayers().size() != 0) {
                team.setPrefix("§8➟ §7" + Bukkit.getOnlinePlayers().size() + "§8/§7" + Bukkit.getMaxPlayers());
            } else {
                team.setPrefix("§8➟ §40§8/§7" + Bukkit.getMaxPlayers());
            }
        }

    }

    // ----------------- MAP -----------------------

    private void addMapScore(Player player, Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(player.getName());

        Score map = objective.getScore("§8● §cMap");
        map.setScore(7);

    }

    private void addMapCounter(Player player, Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(player.getName());

        Team mapName = scoreboard.registerNewTeam(TEAM_MAP);
        mapName.addEntry(ChatColor.BLACK + "" + ChatColor.RED);

        if(TTT.core.getMapManager().isMapSet()) {
            mapName.setPrefix("§8➟ §a" + TTT.core.getMapManager().getMapToPlay().getName());
        } else {
            mapName.setPrefix("§8➟ §7Warten...");
        }

        objective.getScore(ChatColor.BLACK + "" + ChatColor.RED).setScore(6);

    }

    public void updateMapCounter(Player player) {

        Scoreboard scoreboard = player.getScoreboard();

        if(TTT.core.getMapManager().isMapSet()) {
            scoreboard.getTeam(TEAM_MAP).setPrefix("§8➟ §a" + TTT.core.getMapManager().getMapToPlay().getName());
        } else {
            scoreboard.getTeam(TEAM_MAP).setPrefix("§8➟ §7Warten...");
        }

    }

    // ----------------- TEAM -----------------------

    private void addTeamScore(Player player, Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(player.getName());

        Score map = objective.getScore("§8● §cTeam");
        map.setScore(4);

    }

    private void addTeamCounter(Player player, Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(player.getName());

        Team teamName = scoreboard.registerNewTeam(TEAM_TEAM);
        teamName.addEntry(ChatColor.YELLOW + "" + ChatColor.GRAY);

        if(TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {

            if (TTT.core.getPlayerManager().getTTTPlayer(player).hasTeam()) {
                teamName.setPrefix("§8➟ " + formatTeamName(TTT.core.getPlayerManager().getTTTPlayer(player).getTeam()));
                return;
            }
        }

        teamName.setPrefix("§8➟ §7Warten...");

        objective.getScore(ChatColor.YELLOW + "" + ChatColor.GRAY).setScore(3);

    }

    public void updateTeamCounter(Player player) {

        Scoreboard scoreboard = player.getScoreboard();

        if(TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {

            if(TTT.core.getPlayerManager().getTTTPlayer(player).hasTeam()) {
                scoreboard.getTeam(TEAM_TEAM).setPrefix("§8➟ " + formatTeamName(TTT.core.getPlayerManager().getTTTPlayer(player).getTeam()));
                return;
            }

        }

        scoreboard.getTeam(TEAM_TEAM).setPrefix("§8➟ §7Warten...");

    }

    private String formatTeamName(ManagerTeam.Team team) {

        if(team == ManagerTeam.Team.DETECTIVE) {
            return "§9Detective";
        }
        if(team == ManagerTeam.Team.TRAITOR) {
            return "§4Traitor";
        }
        if(team == ManagerTeam.Team.INNOCENT) {
            return "§aInnocent";
        }
        return "§cNot set...";
    }

    // ----------------- REPLAY -----------------------

    private void addReplayScore(Player player, Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(player.getName());

        Score map = objective.getScore("§8● §cReplay ID");
        map.setScore(1);

    }

    private void addReplayCounter(Player player, Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(player.getName());

        Team teamName = scoreboard.registerNewTeam(TEAM_REPLAY);
        teamName.addEntry(ChatColor.GREEN + "" + ChatColor.YELLOW);

        teamName.setPrefix("§8➟ ");
        teamName.setSuffix("§7Kommt noch...");

        objective.getScore(ChatColor.GREEN + "" + ChatColor.YELLOW).setScore(0);

    }

    // ----------------- PLAYER TEAM -----------------------

    private void addPlayerTeam(Player player, Scoreboard scoreboard) {

        CloudPlayer cloudPlayer = CloudAPI.getInstance().getOnlinePlayer(player.getUniqueId());
        PermissionPool pool = CloudAPI.getInstance().getPermissionPool();
        PermissionGroup group = cloudPlayer.getPermissionEntity().getHighestPermissionGroup(pool);
        String groupName = group.getName();

        Team team;
        if(scoreboard.getTeam(groupName) == null) {
            team = scoreboard.registerNewTeam(groupName);
        } else {
            team = scoreboard.getTeam(groupName);
        }

        String cc = ChatColor.translateAlternateColorCodes('&', group.getColor());
        String prefix = group.getPrefix();
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);

        team.setPrefix(prefix + cc);

        team.addPlayer(player);
    }

    public void updatePlayerTeams() {

        for(Player player : Bukkit.getOnlinePlayers()) {

            Scoreboard scoreboard = player.getScoreboard();

            for (Player target : Bukkit.getOnlinePlayers()) {
                addPlayerTeam(target, scoreboard);
            }

            player.setScoreboard(scoreboard);

        }

    }

    public void removePlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        scoreboard.getTeams().forEach(team -> {
            if(!team.getName().equals(TEAM_MAP)
                    && !team.getName().equals(TEAM_PLAYER)
                    && !team.getName().equals(TEAM_REPLAY)
                    && !team.getName().equals(TEAM_TEAM) ) {
                team.unregister();
            }
        });
    }

    public void setTeamTeams(Player player) {

        Scoreboard scoreboard = player.getScoreboard();

        Team detective;

        if(scoreboard.getTeam(TEAM_DETECTIVE) == null) {
            detective = scoreboard.registerNewTeam(TEAM_DETECTIVE);
            detective.setPrefix("§9");
        } else {
            detective = scoreboard.getTeam(TEAM_DETECTIVE);
        }

        Team traitor;

        if(scoreboard.getTeam(TEAM_TRAITOR) == null) {
            traitor = scoreboard.registerNewTeam(TEAM_TRAITOR);
            traitor.setPrefix("§4");
        } else {
            traitor = scoreboard.getTeam(TEAM_TRAITOR);
        }

        Team innocent;

        if(scoreboard.getTeam(TEAM_INNOCENT) == null) {
            innocent = scoreboard.registerNewTeam(TEAM_INNOCENT);
            innocent.setPrefix("§a");
        } else {
            innocent = scoreboard.getTeam(TEAM_INNOCENT);
        }

        for(Player target : Bukkit.getOnlinePlayers()) {

            if(TTT.core.getPlayerManager().existsPlayer(target.getUniqueId())) {

                TTTPlayer tttTarget = TTT.core.getPlayerManager().getTTTPlayer(target);
                ManagerTeam.Team targetTeam = tttTarget.getTeam();

                if(targetTeam == ManagerTeam.Team.DETECTIVE) {
                    detective.addPlayer(target);
                } else if(targetTeam == ManagerTeam.Team.TRAITOR) {

                    if(TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {

                        if(TTT.core.getPlayerManager().getTTTPlayer(player).getTeam() == ManagerTeam.Team.TRAITOR) {
                            traitor.addPlayer(target);
                        }else {
                            innocent.addPlayer(target);
                        }

                    }else {
                        innocent.addPlayer(target);
                    }

                } else {
                    innocent.addPlayer(target);
                }
            }else {
                innocent.addPlayer(target);
            }

        }

        player.setScoreboard(scoreboard);

    }

}
