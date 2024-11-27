package com.mygdxexample.seabattle.screens;

import static com.mygdxexample.seabattle.resources.GlobalVariables.WINDOW_HEIGHT;
import static com.mygdxexample.seabattle.resources.GlobalVariables.WINDOW_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdxexample.seabattle.SeaBattleGame;
import com.mygdxexample.seabattle.resources.Assets;

public class MainScreen implements Screen {
    private final SeaBattleGame game;
    private Stage stage;
    private Texture startTexture;
    private Texture exitTexture;
    private Texture shadersTexture;
    private Image startButton;
    private Image exitButton;
    private Image shadersButton;
    private SpriteBatch batch; // Спрайт-батч для отрисовки текста
    private Texture backgroundTexture; // Текстура для фона

    public MainScreen(SeaBattleGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        startTexture = new Texture(Gdx.files.internal(Assets.START_BUTTON));
        exitTexture = new Texture(Gdx.files.internal(Assets.EXIT_BUTTON));
        shadersTexture = new Texture(Gdx.files.internal(Assets.SHADERS_TEXTURE));
        backgroundTexture = new Texture(Gdx.files.internal(Assets.MENU_BACKGROUND));

        shadersButton = new Image(new TextureRegionDrawable(shadersTexture));
        shadersButton.setPosition((float) WINDOW_WIDTH / 4*3, (float) WINDOW_HEIGHT / 5 * 3);
        shadersButton.setSize((float) WINDOW_WIDTH / 4, (float) WINDOW_HEIGHT /3);
        shadersButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ShadersScreen(game));
            }
        });

        startButton = new Image(new TextureRegionDrawable(startTexture));
        startButton.setPosition((float) WINDOW_WIDTH / 3, (float) WINDOW_HEIGHT / 5 * 3);
        startButton.setSize((float) WINDOW_WIDTH / 4, (float) WINDOW_HEIGHT /3);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        exitButton = new Image(new TextureRegionDrawable(exitTexture));
        exitButton.setPosition((float) WINDOW_WIDTH / 3, (float) WINDOW_HEIGHT / 6);
        exitButton.setSize((float) WINDOW_WIDTH / 4, (float) WINDOW_HEIGHT /3);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(startButton);
        stage.addActor(exitButton);
        stage.addActor(shadersButton);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Рисуем фон на весь экран
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        startTexture.dispose();
        exitTexture.dispose();
    }
}
