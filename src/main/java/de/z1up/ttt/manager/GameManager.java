package de.z1up.ttt.manager;

import de.z1up.ttt.interfaces.Manager;

public class GameManager implements Manager {

    private GameState gameState;

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
        SCHUTZPHASE,
        INGAME,
        RESTART
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
        return (gameState == GameState.SCHUTZPHASE);
    }

    public boolean inRestart() {
        return (gameState == GameState.RESTART);
    }

    public boolean notSet() {
        return (gameState == null);
    }
}
