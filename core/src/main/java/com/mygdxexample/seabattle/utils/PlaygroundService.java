package com.mygdxexample.seabattle.utils;

import com.badlogic.gdx.graphics.Texture;
import com.mygdxexample.seabattle.SeaBattleGame;

import static com.mygdxexample.seabattle.resources.GlobalVariables.*;
import static com.mygdxexample.seabattle.resources.GlobalVariables.SQUARE_SIZE;

public class PlaygroundService {
    private final SeaBattleGame game;
    private final int gridWidth;
    private final int gridHeight;
    private final Texture[] cellsLettersTextures;
    private final Texture[] cellsNumbersTextures;
    private final Texture playableBackground;
    private final Texture squareTexture;


    public PlaygroundService(SeaBattleGame game, int gridWidth, int gridHeight, Texture[] cellsLettersTextures, Texture[] cellsNumbersTextures, Texture playableBackground, Texture squareTexture) {
        this.game = game;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellsLettersTextures = cellsLettersTextures;
        this.cellsNumbersTextures = cellsNumbersTextures;
        this.playableBackground = playableBackground;
        this.squareTexture = squareTexture;
    }

    public void drawCells() {

        game.batch.begin();

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (x == 1 && y >= 1 && y <= 10) {
                    game.batch.draw(cellsLettersTextures[y - 1], x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
                else if (y == 11 && x >= 2 && x <= 11) {
                    game.batch.draw(cellsNumbersTextures[x - 2], x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
                else {
                    game.batch.draw(squareTexture, x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }

        game.batch.end();
    }

    public void drawPlaygroundBackground(){
        float x = COORDINATE_X_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;
        float y = COORDINATE_Y_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;

        float width = 10 * SQUARE_SIZE + 2 * PADDING_FOR_PLAYGROUND_BACK;
        float height = 10 * SQUARE_SIZE + 2 * PADDING_FOR_PLAYGROUND_BACK;

        game.batch.begin();
        game.batch.draw(playableBackground, x, y, width, height);
        game.batch.end();
    }
}
