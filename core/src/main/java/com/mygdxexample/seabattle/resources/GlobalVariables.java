package com.mygdxexample.seabattle.resources;

import com.badlogic.gdx.Gdx;

public class GlobalVariables {
    public static final int WINDOW_WIDTH = Gdx.graphics.getWidth();
    public static final int WINDOW_HEIGHT = Gdx.graphics.getHeight();

    public static final float WORLD_WIDTH = 100f;
    public static final float WORLD_HEIGHT = 48f;
    public static final float MIN_WORLD_HEIGHT = WORLD_HEIGHT * 0.85f;
    public static final float WORLD_SCALE = 0.3f;

    public static final int PADDING_FOR_PLAYGROUND_BACK = 5;
    public static final int PLAYGROUND_NUM_CELLS_X = 10;
    public static final int PLAYGROUND_NUM_CELLS_Y = 10;
    public static final int COORDINATE_X_START_PLAYGROUND = 2;
    public static final int COORDINATE_Y_START_PLAYGROUND = 1;
    public static final int SQUARE_SIZE = (int) (WINDOW_HEIGHT / 16.0f);
    public static final int GRID_WIDTH = Gdx.graphics.getWidth() / SQUARE_SIZE;
    public static final int GRID_HEIGHT = Gdx.graphics.getHeight() / SQUARE_SIZE;
    public static final int RADIUS = 60;
    public static final int COORDINATE_X_FOR_PLAYGROUND_TEXTURE =  COORDINATE_X_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;

    public static final String UNIFORMS_RESOLUTION = "u_resolution";
    public static final String UNIFORMS_TEXTURE= "u_texture";
    public static final String UNIFORMS_CIRCLE_TEXTURE = "u_circleTexture";
    public static final String UNIFORMS_RADIUS = "u_radius";
    public static final String UNIFORMS_CIRCLE_POS = "u_circlePos";



}
