package de.z1up.ttt.util;

import de.z1up.ttt.book.RuleBook;
import de.z1up.ttt.manager.*;
import net.minecraft.server.v1_8_R3.Scoreboard;

public class Data {

    private final static String prefix = "§cTTT §8× §7";
    private final static String noPerm = prefix + "§7Du hast keine Rechte dazu§8.";


    public static MapManager mapManager;
    public static GameManager gameManager;
    public static PlayerManager playerManager;
    public static BuildManager buildManager;
    public static ScoreboardManager sbManager;

    public static RuleBook ruleBook;

    public void init() {
        mapManager = new MapManager();
        gameManager = new GameManager();
        playerManager = new PlayerManager();
        buildManager = new BuildManager();
        sbManager = new ScoreboardManager();

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
