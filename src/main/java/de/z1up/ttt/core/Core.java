package de.z1up.ttt.core;

import de.z1up.ttt.TTT;
import de.z1up.ttt.book.RuleBook;
import de.z1up.ttt.manager.*;
import de.z1up.ttt.mysql.wrapper.WrapperAchievements;
import de.z1up.ttt.mysql.wrapper.WrapperPlayer;
import de.z1up.ttt.mysql.SQL;
import de.z1up.ttt.mysql.SQLConfig;
import de.z1up.ttt.replay.ReplayFile;

/**
 * The Core class is used to access the
 * individual managers and modules. Furthermore,
 * the essential SQL connection is established here.
 */
public class Core {

    public static SQL sql;

    private ManagerMap mapManager;
    private GameManager gameManager;
    private PlayerManager playerManager;
    private BuildManager buildManager;
    private ScoreboardManager sbManager;
    private InventoryManager invManager;
    private TimerManager timerManager;
    private VoteManager voteManager;
    private ManagerSpawn spawnManager;

    public ReplayFile replayFile;
    public RuleBook ruleBook;

    public WrapperPlayer wrapperPlayer;
    public WrapperAchievements wrapperAchievements;

    /**
     * The load method calls various other methods that
     * initialise attributes or load other classes.
     */
    public void load() {

        // Create the SQL connection
        createSQLCon();

        wrapperPlayer = new WrapperPlayer(sql);
        wrapperAchievements = new WrapperAchievements(sql);

        // initialize the manager
        initManager();

        replayFile = new ReplayFile();
        ruleBook = new RuleBook();

        loadClassLoader();
    }

    /**
     * The individual managers are initialised in the initManager()
     * method. If necessary, the SQL connection is passed on as an
     * attribute. In most cases, this is important for the wrappers
     * on which the managers are based.
     */
    void initManager() {
        mapManager = new ManagerMap();
        gameManager = new GameManager();
        playerManager = new PlayerManager();
        buildManager = new BuildManager();
        sbManager = new ScoreboardManager();
        invManager = new InventoryManager();
        timerManager = new TimerManager();
        voteManager = new VoteManager();
        spawnManager = new ManagerSpawn();
    }


    /**
     * The sql attribute is initialised with the createSQLCon() method.
     * To do this, a {@link SQLConfig} must be created and passed on to the SQL
     * class. The access data to the database are then read from this.
     * If the attribute is not null and not connected, a new connection
     * is established.
     */
    void createSQLCon() {
        SQLConfig config = new SQLConfig(TTT.getInstance().getDataFolder().getPath(), "mysql.yml");
        if(sql == null) {
            sql = new SQL(config.readData());
        }
        if(sql != null && !sql.isConnected()) {
            sql.connect();
        }
    }

    /**
     * This method calls the {@link ClassLoader}, which registers all
     * commands and listeners on the server.
     */
    void loadClassLoader() {
        ClassLoader loader = new ClassLoader();
        loader.load();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ManagerMap getMapManager() {
        return mapManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public BuildManager getBuildManager() {
        return buildManager;
    }

    public ScoreboardManager getSbManager() {
        return sbManager;
    }

    public InventoryManager getInvManager() {
        return invManager;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }

    public ManagerSpawn getSpawnManager() {
        return spawnManager;
    }

    public RuleBook getRuleBook() {
        return ruleBook;
    }
}

