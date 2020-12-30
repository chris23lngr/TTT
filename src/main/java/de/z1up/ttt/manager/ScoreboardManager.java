package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.security.SecureRandom;
import java.util.Iterator;

public class ScoreboardManager {

    public Scoreboard board;
    String rnd;
    SecureRandom random;
    public String replayId;

    public ScoreboardManager() {
        init();
    }

    void init() {
        rnd = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz";
        random = new SecureRandom();
        replayId = createNewReplayID(5);
    }

    public void setScoreBoard(Player player, GameManager.GameState gameState) {
        if(gameState == GameManager.GameState.LOBBYPHASE) {
            setLobbySB(player);
            return;
        }
    }

    private void setLobbySB(Player player) {
            board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("aaa", "Scoreboard");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName("   §8« §arenixinside.de §8»   ");
            obj.getScore("§7§8 ").setScore(9);
            obj.getScore("§6§lKarma").setScore(8);
            //obj.getScore("§9§8➟ §1§7" + MySQLStats.getKarma(p)).setScore(7);
            obj.getScore("§9§8➟ §1§7" + "NOT SET").setScore(7);
            obj.getScore("§7§9 ").setScore(5);
            obj.getScore("§b§lMap").setScore(4);
            //obj.getScore("§8➟ §2§7" + (String)Data.getMap.get("MAP")).setScore(3);
            obj.getScore("§8➟ §2§7" + "NOT SET").setScore(3);
            obj.getScore("§4§9§l ").setScore(2);
            obj.getScore("§e§lReplayID").setScore(1);
            obj.getScore("§8➟ §5§7" + replayId).setScore(0);
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            player.setScoreboard(board);
    }


    private String createNewReplayID(int length) {
        StringBuilder sb = new StringBuilder(length);

        for(int i = 0; i < length; ++i) {
            sb.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz".charAt(random.nextInt("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz".length())));
        }

        return sb.toString();
    }
}
