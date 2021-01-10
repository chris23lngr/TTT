package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.GameEndEvent;
import de.z1up.ttt.interfaces.Manager;
import org.bukkit.Bukkit;

public class GameManager implements Manager {

    private GameState gameState;
    private GameResult gameResult;

    public GameManager() {
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {
        gameState = GameState.LOBBYPHASE;
    }

    public enum GameState {
        LOBBYPHASE,
        PRE_SAVEPHASE,
        SAVEPHASE,
        INGAME,
        RESTART
    }

    public enum GameResult {
        TRAITOR_WIN,
        INNOCENT_WIN,
        DRAW,
        NOT_SET
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean inLobby() {
        return (gameState == GameState.LOBBYPHASE);
    }

    public boolean inGame() {
        return (gameState == GameState.INGAME);
    }

    public boolean inSavePhase() {
        return (gameState == GameState.SAVEPHASE);
    }

    public boolean inRestart() {
        return (gameState == GameState.RESTART);
    }

    public boolean notSet() {
        return (gameState == null);
    }

    public boolean checkPossibleGameEnding() {

        int specSize = TTT.core.getPlayerManager().getSpecs().size();
        int onlineSize = Bukkit.getOnlinePlayers().size();
        int actualSize = onlineSize - specSize;


        if(TTT.core.getTeamManager().getTraitorCounter() == actualSize
                && TTT.core.getTeamManager().getInnocentCounter() == 0
                && TTT.core.getTeamManager().getDetectiveCounter() == 0) {

            this.gameResult = GameResult.TRAITOR_WIN;

            return true;
        }

        int innoSize = TTT.core.getTeamManager().getInnocentCounter();
        int detSize = TTT.core.getTeamManager().getDetectiveCounter();

        if(TTT.core.getTeamManager().getTraitorCounter() == 0
                && actualSize == (innoSize + detSize)) {

            this.gameResult = GameResult.INNOCENT_WIN;

            return true;
        }

        return false;
    }
}
