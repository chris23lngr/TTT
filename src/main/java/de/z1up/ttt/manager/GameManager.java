package de.z1up.ttt.manager;

public class GameManager {

    private GameState gameState;

    public GameManager() {
        gameState = GameState.LOBBYPHASE;
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

    public enum GameState {
        LOBBYPHASE,
        SCHUTZPHASE,
        INGAME,
        RESTART
    }
}
