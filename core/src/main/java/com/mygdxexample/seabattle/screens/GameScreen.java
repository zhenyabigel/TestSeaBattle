package com.mygdxexample.seabattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdxexample.seabattle.SeaBattleGame;
import com.mygdxexample.seabattle.model.Ship;
import com.mygdxexample.seabattle.resources.Assets;
import com.mygdxexample.seabattle.utils.PlaygroundService;
import com.mygdxexample.seabattle.utils.ShipService;

import static com.mygdxexample.seabattle.resources.GlobalVariables.*;

public class GameScreen implements Screen {

    private final SeaBattleGame game;
    private final ExtendViewport viewport;
    private Stage stage;
    private ImageButton generateButton;

    private Texture backgroundWoodTexture;
    private Texture squareTexture;
    private Texture playableBackround;
    private Texture[] cellsLettersTextures;
    private Texture[] cellsNumbersTextures;

    private PlaygroundService playgroundService;
    private ShipService shipService;


    private int gridWidth, gridHeight;

    private Ship[] ships;


    public GameScreen(SeaBattleGame game) {
        this.game = game;

        viewport = new ExtendViewport(WINDOW_WIDTH, MIN_WORLD_HEIGHT);

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
        createButton();

        shipService = new ShipService(game);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Устанавливаем Stage как обработчик ввода

        createButton();
        stage.addActor(generateButton); // Добавляем кнопку на Stage
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(1, 1, 1, 1);

        game.batch.setProjectionMatrix(viewport.getCamera().combined);

        // Отрисовка игрового поля
        playgroundService.drawCells();
        playgroundService.drawPlaygroundBackground();

        game.batch.begin();
        game.batch.draw(backgroundWoodTexture, 0, 0, backgroundWoodTexture.getWidth(), backgroundWoodTexture.getHeight() * WORLD_SCALE);
        game.batch.end();

        // Отрисовка кнопки и других UI-элементов
        stage.act(v);
        stage.draw();
        shipService.drawShips();
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
        cellsLettersTextures = new Texture[PLAYGROUND_NUM_CELLS_Y];
        for (int i = 0; i < cellsLettersTextures.length; i++) {

            cellsLettersTextures[i] = new Texture(Gdx.files.internal("textures/cellLetter" + i + ".png"));
            // Замените на ваши текстуры
        }
    }

    public void initTextureNumbersArray() {
        cellsNumbersTextures = new Texture[PLAYGROUND_NUM_CELLS_X];
        for (int i = 0; i < cellsLettersTextures.length; i++) {
            cellsNumbersTextures[i] = new Texture(Gdx.files.internal("textures/cellNumber" + i + ".png")); // Замените на ваши текстуры
        }
    }

    private void createButton() {
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/wood_background.jpg"))));

        generateButton = new ImageButton(buttonDrawable);

        generateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked!"); // Для проверки
                shipService.generateRandomShips();
            }
        });

        generateButton.setPosition(400, 100); // Установка позиции кнопки
        generateButton.setWidth(100);
        generateButton.setHeight(100);
    }


}
