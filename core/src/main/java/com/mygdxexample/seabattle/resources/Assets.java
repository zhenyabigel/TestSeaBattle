package com.mygdxexample.seabattle.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static final String AUTO_BUTTON = "textures/button.png";
    public static final String BACK_BUTTON = "textures/backButton.png";
    public final AssetManager manager = new AssetManager();
    public static final String START_BUTTON = "textures/buttonStart.png";
    public static final String EXIT_BUTTON = "textures/exitButton.png";
    public static final String WOOD_DESK = "textures/wood_background.jpg";
    public static final String MENU_BACKGROUND = "textures/menu_background.jpg";
    public static final String SHADERS_TEXTURE = "textures/buttonGenerate.png";
    public static final String WOOD_FOR_CIRCLE = "textures/wood_background.jpg";
    public static final String VERTEX_FOR_PLAYGROUND = "vertexPlayground.glsl";
    public static final String FRAGMENT_FOR_PLAYGROUND = "fragmentPlayground.glsl";
    public static final String FRAGMENT_FOR_SHIPS = "fragmentShips.glsl";
    public static final String VERTEX_FOR_SHIPS = "vertexShips.glsl";
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
