package de.kenjih.manhunt.utils;

public class GameUtils {

    public static GameState gameState;

    public enum GameState{
        LOBBY_STATE,
        INGAME_STATE,
        ENDING_STATE;
    }
}