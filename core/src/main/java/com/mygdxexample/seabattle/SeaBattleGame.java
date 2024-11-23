package com.mygdxexample.seabattle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdxexample.seabattle.resources.Assets;
import com.mygdxexample.seabattle.screens.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SeaBattleGame extends Game {

    public SpriteBatch batch;
    public Assets assets;
    public GameScreen gameScreen;

    @Override
    public void create() {
        batch  = new SpriteBatch();
        assets = new Assets();

        assets.load();
        assets.manager.finishLoading();

        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }
}
