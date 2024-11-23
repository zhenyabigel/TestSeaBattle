package com.mygdxexample.seabattle.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public final AssetManager manager = new AssetManager();
    public static final String BACKGROUND_WOOD = "textures/background_wood.png";
    public static final String BACKGROUND_PAPER = "textures/sheet_of_paper_background.png";
    public static final String PLAYABLE_BACKGROUND = "textures/playable_background.png";
    public static final String CELL_TEXTURE = "textures/cell.png";

    public static final String ROBOTO_REGULAR = "fonts/Roboto-Regular.ttf";

    public void load() {
        loadGameplayAssets();
    }

    private void loadGameplayAssets() {
        manager.load(BACKGROUND_PAPER, Texture.class);
        manager.load(BACKGROUND_WOOD, Texture.class);
        manager.load(PLAYABLE_BACKGROUND, Texture.class);
        manager.load(CELL_TEXTURE, Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
