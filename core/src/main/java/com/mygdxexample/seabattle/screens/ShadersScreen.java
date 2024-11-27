package com.mygdxexample.seabattle.screens;

import static com.mygdxexample.seabattle.resources.GlobalVariables.WINDOW_HEIGHT;
import static com.mygdxexample.seabattle.resources.GlobalVariables.WINDOW_WIDTH;
import static com.mygdxexample.seabattle.resources.GlobalVariables.WORLD_HEIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdxexample.seabattle.SeaBattleGame;
import com.mygdxexample.seabattle.resources.Assets;
import com.mygdxexample.seabattle.service.PlaygroundService;
import com.mygdxexample.seabattle.service.ShipService;

public class ShadersScreen implements Screen {
    private final SeaBattleGame game;
    OrthographicCamera camera;
    private Stage stage;
    private ImageButton generateButton;
    private ImageButton backButton;
    private PlaygroundService playgroundService;
    private ShipService shipService;
    private SpriteBatch playgroundBatch;
    private SpriteBatch shipsBatch;

    public ShadersScreen(SeaBattleGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WINDOW_WIDTH, WORLD_HEIGHT);

        createGameArea();
    }

    public void createGameArea() {
        playgroundBatch = new SpriteBatch();
        shipsBatch = new SpriteBatch();

        playgroundService = new PlaygroundService(playgroundBatch);
        shipService = new ShipService(shipsBatch, playgroundService.getCirclePos());
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createAutoButton();
        createBackButton();
        stage.addActor(generateButton);
        stage.addActor(backButton);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.getBatch().setProjectionMatrix(camera.combined);

        playgroundService.drawCells();
        shipService.drawShipsWithShaders();
        shipService.drawHittedShipsWithShaders();

        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        shipService.dispose();
        playgroundService.dispose();
        playgroundBatch.dispose();
        shipsBatch.dispose();
    }


    private void createAutoButton() {
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Assets.AUTO_BUTTON))));
        generateButton = new ImageButton(buttonDrawable);

        generateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shipService.generateRandomShips();
                resetGameState();
            }
        });

        generateButton.setPosition((float) WINDOW_WIDTH / 5 * 3, (float) WINDOW_HEIGHT / 4);
        generateButton.setSize((float) WINDOW_WIDTH / 6, (float) WINDOW_HEIGHT / 6);
    }

    private void createBackButton() {
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Assets.BACK_BUTTON))));
        backButton = new ImageButton(buttonDrawable);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        backButton.setPosition((float) WINDOW_WIDTH / 5 * 3, (float) WINDOW_HEIGHT / 4*2);
        backButton.setSize((float) WINDOW_WIDTH / 6, (float) WINDOW_HEIGHT / 6);
    }

    private void resetGameState() {
        playgroundBatch = new SpriteBatch();
        shipsBatch = new SpriteBatch();
        playgroundService = new PlaygroundService(playgroundBatch);
        shipService = new ShipService(shipsBatch, playgroundService.getCirclePos());
    }
}


