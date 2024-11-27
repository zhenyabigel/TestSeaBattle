package com.mygdxexample.seabattle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdxexample.seabattle.resources.Assets;
import com.mygdxexample.seabattle.screens.MainScreen;

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
        setScreen(mainScreen);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Assets getAssets() {
        return assets;
    }
}
