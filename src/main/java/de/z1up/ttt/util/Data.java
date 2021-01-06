package de.z1up.ttt.util;

import de.z1up.ttt.TTT;
import de.z1up.ttt.book.RuleBook;
import de.z1up.ttt.manager.*;
import de.z1up.ttt.mysql.wrapper.WrapperAchievements;
import de.z1up.ttt.mysql.wrapper.WrapperPlayer;
import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.mysql.SQLConfig;
import de.z1up.ttt.replay.ReplayFile;

public class Data {

    private final static String prefix = "§cTTT §8× §7";
    private final static String noPerm = prefix + "§7Du hast keine Rechte dazu§8.";

    public static SQL sql;

    public static ManagerMap mapManager;
    public static GameManager gameManager;
    public static PlayerManager playerManager;
    public static BuildManager buildManager;
    public static ScoreboardManager sbManager;
    public static InventoryManager invManager;
    public static TimerManager timerManager;
    public static VoteManager voteManager;
    public static ManagerSpawn spawnManager;

    public static ReplayFile replayFile;
    public static RuleBook ruleBook;

    public static WrapperPlayer wrapperPlayer;
    public static WrapperAchievements wrapperAchievements;

    public void init() {

        SQLConfig config = new SQLConfig(TTT.getInstance().getDataFolder().getPath(), "mysql.yml");
        sql = new SQL(config.readData());
        sql.connect();

        wrapperPlayer = new WrapperPlayer(sql);
        wrapperAchievements = new WrapperAchievements(sql);

        mapManager = new ManagerMap();
        gameManager = new GameManager();
        playerManager = new PlayerManager();
        buildManager = new BuildManager();
        sbManager = new ScoreboardManager();
        invManager = new InventoryManager();
        timerManager = new TimerManager();
        voteManager = new VoteManager();

        replayFile = new ReplayFile();
        ruleBook = new RuleBook();

        ClassLoader loader = new ClassLoader();
        loader.load();

    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getNoperm() {
        return noPerm;
    }

}
