//package com.mygdxexample.seabattle.screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.mygdxexample.seabattle.SeaBattleGame;
//
//import java.awt.*;
//
//public class MainScreen implements Screen {
//    private SeaBattleGame seaBattleGame;
//    private Stage stage;
//    private Texture startButtonTexture;
//    private Texture exitButtonTexture;
//    private SpriteBatch batch;
//    private BitmapFont font;
//    private Texture buttonTexture;
//    private Texture backgroundTexture;
//    private Rectangle startButton;
//    private Rectangle exitButton;
//
//    public MainScreen(SeaBattleGame seaBattleGame) {
//        this.seaBattleGame = seaBattleGame;
//        stage = new Stage(new ScreenViewport());
//        Gdx.input.setInputProcessor(stage);
//
//        // Загружаем текстуры для кнопок
//        startButtonTexture = new Texture("textures/button.png"); // Замените на путь к вашей текстуре кнопки "Начать игру"
//        exitButtonTexture = new Texture("textures/button.png");   // Замените на путь к вашей текстуре кнопки "Выход"
//
//        // Создаем кнопки
//        ImageButton startButton = new ImageButton(new TextureRegionDrawable(startButtonTexture));
//        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(exitButtonTexture));
//
//        // Устанавливаем размеры и позицию кнопок
//        startButton.setSize(200, 50);
//        startButton.setPosition(100, 200);
//
//        exitButton.setSize(200, 50);
//        exitButton.setPosition(100, 100);
//
//        // Добавляем слушатели на кнопки
//        startButton.addListener(event -> {
//            if (event.isHandled()) {
//                seaBattleGame.setScreen(seaBattleGame.gameScreen);
//                return true;
//            }
//            return false;
//        });
//
//        exitButton.addListener(event -> {
//            if (event.isHandled()) {
//                Gdx.app.exit();
//                return true;
//            }
//            return false;
//        });
//
//        // Добавляем кнопки на сцену
//        stage.addActor(startButton);
//        stage.addActor(exitButton);
//    }
//
//    @Override
//    public void show() {
//
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.act(delta);
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//        startButtonTexture.dispose();
//        exitButtonTexture.dispose();
//    }
//}
