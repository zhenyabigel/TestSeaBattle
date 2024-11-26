package com.mygdxexample.seabattle.screens;

import static com.mygdxexample.seabattle.resources.GlobalVariables.MIN_WORLD_HEIGHT;
import static com.mygdxexample.seabattle.resources.GlobalVariables.WORLD_SCALE;
import static com.mygdxexample.seabattle.resources.GlobalVariables.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mygdxexample.seabattle.resources.Assets;
import com.mygdxexample.seabattle.utils.PlaygroundRepository;
import com.mygdxexample.seabattle.utils.ShipRepository;

public class GameScreen implements Screen {

    private final SeaBattleGame game;
    private final ExtendViewport viewport;
    private Stage stage;
    private ImageButton generateButton;
    private Texture backgroundWoodTexture;
    private PlaygroundRepository playgroundRepository;
    private ShipRepository shipRepository;
    private SpriteBatch playgroundBatch;
    private SpriteBatch shipsBatch;

    public GameScreen(SeaBattleGame game) {
        this.game = game;
        viewport = new ExtendViewport(WORLD_WIDTH, MIN_WORLD_HEIGHT);
        createGameArea();
    }

    public void createGameArea() {
        playgroundBatch = new SpriteBatch();
        shipsBatch = new SpriteBatch();

        backgroundWoodTexture = game.assets.manager.get(Assets.BACKGROUND_WOOD);
        playgroundRepository = new PlaygroundRepository(playgroundBatch, game.assets.manager);
        createButton();
        shipRepository = new ShipRepository(shipsBatch,playgroundRepository.getCirclePos());
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createButton();
        stage.addActor(generateButton);
    }

    @Override
    public void render(float v) {

        ScreenUtils.clear(1, 1, 1, 1);

        playgroundRepository.drawUsingShaders();

        game.batch.begin();
        game.batch.draw(backgroundWoodTexture, 0, 0, backgroundWoodTexture.getWidth(), backgroundWoodTexture.getHeight() * WORLD_SCALE);
        game.batch.end();

        stage.act(v);
        stage.draw();
        shipRepository.drawShips();
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


    private void createButton() {

        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(
            new TextureRegion(
                new Texture(Gdx.files.internal("textures/button.png"))));
        generateButton = new ImageButton(buttonDrawable);

        generateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked!");
                shipRepository.generateRandomShips();
                playgroundRepository.generateRandomBallCenter();
            }
        });

        generateButton.setPosition(2000, 100);
        generateButton.setWidth(200);
        generateButton.setHeight(100);
    }


}


