package com.mygdxexample.seabattle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdxexample.seabattle.resources.Assets;
import com.mygdxexample.seabattle.screens.GameScreen;
import com.mygdxexample.seabattle.screens.MainScreen;
import com.sun.tools.javac.Main;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class SeaBattleGame extends Game {
    SpriteBatch batch;
    Assets assets;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assets = new Assets();

        assets.load();
        assets.manager.finishLoading();

        MainScreen mainScreen = new MainScreen(this);
        GameScreen gameScreen = new GameScreen(this);
        setScreen(mainScreen);
    }

    public SpriteBatch getBatch() {
        return batch;
    }
    public Assets getAssets() {
        return assets;
    }
}
