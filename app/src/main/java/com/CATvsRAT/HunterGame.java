package com.CATvsRAT;

public class HunterGame {
    private static GameGrid gameGrid;

    public HunterGame(int size_x, int size_y) {//start the game.
        gameGrid = new GameGrid(size_x,size_y);
    }

    public static GameGrid getGameGrid() {
        return gameGrid;
    }

}
