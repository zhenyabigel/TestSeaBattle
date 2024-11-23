package com.mygdxexample.seabattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdxexample.seabattle.SeaBattleGame;
import com.mygdxexample.seabattle.resources.Assets;
import com.mygdxexample.seabattle.utils.PlaygroundService;

import static com.mygdxexample.seabattle.resources.GlobalVariables.*;

public class GameScreen implements Screen {

    private final SeaBattleGame game;
    private final OrthographicCamera camera;
    private final ExtendViewport viewport;
    private Texture backgroundWoodTexture;
    private PlaygroundService playgroundService;
    private Texture squareTexture;
    private Texture playableBackround;
    private int gridWidth, gridHeight;
    private Texture[] cellsLettersTextures;
    private Texture[] cellsNumbersTextures;

    public GameScreen(SeaBattleGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(WINDOW_WIDTH, MIN_WORLD_HEIGHT, camera);

        createGameArea();
    }

    public void createGameArea() {
        backgroundWoodTexture = game.assets.manager.get(Assets.BACKGROUND_WOOD);
        squareTexture = new Texture(Gdx.files.internal(Assets.CELL_TEXTURE));
        playableBackround = new Texture(Gdx.files.internal(Assets.PLAYABLE_BACKGROUND));
        initTextureLettersArray();
        initTextureNumbersArray();
        gridWidth = WINDOW_WIDTH / SQUARE_SIZE;
        gridHeight = WINDOW_HEIGHT / SQUARE_SIZE;
        playgroundService = new PlaygroundService(game, gridWidth, gridHeight, cellsLettersTextures, cellsNumbersTextures, playableBackround, squareTexture);
    }


    @Override
    public void show() {

    }


    @Override
    public void render(float v) {
        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.setProjectionMatrix(camera.combined);

        playgroundService.drawCells();
        playgroundService.drawPlaygroundBackground();

        game.batch.begin();
        game.batch.draw(backgroundWoodTexture, 0, 0, backgroundWoodTexture.getWidth(), backgroundWoodTexture.getHeight() * WORLD_SCALE);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void initTextureLettersArray() {
        cellsLettersTextures = new Texture[10];
        for (int i = 0; i < cellsLettersTextures.length; i++) {
            cellsLettersTextures[i] = new Texture(Gdx.files.internal("textures/cellLetter" + i + ".png")); // Замените на ваши текстуры
        }
    }

    public void initTextureNumbersArray() {
        cellsNumbersTextures = new Texture[10];
        for (int i = 0; i < cellsLettersTextures.length; i++) {
            cellsNumbersTextures[i] = new Texture(Gdx.files.internal("textures/cellNumber" + i + ".png")); // Замените на ваши текстуры
        }
    }
}
