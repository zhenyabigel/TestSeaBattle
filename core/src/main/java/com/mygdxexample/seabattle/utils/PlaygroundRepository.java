package com.mygdxexample.seabattle.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdxexample.seabattle.resources.Assets;

import java.util.Random;
import java.util.Vector;

import static com.mygdxexample.seabattle.resources.GlobalVariables.*;

public class PlaygroundRepository {

    private final SpriteBatch batch;

    private AssetManager manager;
    private Texture[] cellsLettersTextures;
    private Texture[] cellsNumbersTextures;

    private final Texture playableBackground;
    private final Texture squareTexture;
    private final Texture circleTexture;

    private final FrameBuffer fbo;
    private final ShaderProgram shader;

    private Vector2 circlePos;

    private final ExtendViewport viewport;


    public PlaygroundRepository(SpriteBatch batch, AssetManager manager) {
        this.batch = batch;
        this.manager = manager;
        playableBackground = new Texture(Gdx.files.internal(Assets.PLAYABLE_BACKGROUND));
        squareTexture = new Texture(Gdx.files.internal(Assets.CELL_TEXTURE));
        circleTexture = new Texture(Gdx.files.internal(Assets.WOOD_FOR_CIRCLE));
        viewport = new ExtendViewport(WORLD_WIDTH, MIN_WORLD_HEIGHT);
        fbo = new FrameBuffer(
            Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        shader = new ShaderProgram(Gdx.files.internal(Assets.VERTEX_FOR_PLAYGROUND), Gdx.files.internal(Assets.FRAGMENT_FOR_PLAYGROUND));
        generateRandomBallCenter();

        initTextureLettersArray();
        initTextureNumbersArray();
    }

    public void drawCells() {
        batch.begin();

        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                if (x == COORDINATE_X_START_PLAYGROUND - 1 && y >= COORDINATE_Y_START_PLAYGROUND && y <= 10) {
                    batch.draw(cellsLettersTextures[y - 1], x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                } else if (y == COORDINATE_Y_START_PLAYGROUND + PLAYGROUND_NUM_CELLS_Y && x >= COORDINATE_X_START_PLAYGROUND && x <= COORDINATE_X_START_PLAYGROUND + PLAYGROUND_NUM_CELLS_Y - 1) {
                    batch.draw(cellsNumbersTextures[x - 2], x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                } else {
                    batch.draw(squareTexture, x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }

        batch.end();
        batch.flush();
        drawPlaygroundBackground();
    }

    public void drawPlaygroundBackground() {
        float x = COORDINATE_X_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;
        float y = COORDINATE_Y_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;

        float width = PLAYGROUND_NUM_CELLS_X * SQUARE_SIZE + 2 * PADDING_FOR_PLAYGROUND_BACK;
        float height = PLAYGROUND_NUM_CELLS_Y * SQUARE_SIZE + 2 * PADDING_FOR_PLAYGROUND_BACK;

        batch.begin();
        batch.draw(playableBackground, x, y, width, height);
        batch.end();
    }


    public void drawUsingShaders() {
        batch.flush();

        fbo.begin();
        drawCells();
        batch.flush();
        fbo.end();

        Texture fboTex = fbo.getColorBufferTexture();
        TextureRegion textureRegion = new TextureRegion(fboTex);
        textureRegion.flip(false, true);

        batch.begin();
        batch.setShader(shader);
        shader.setUniformf(UNIFORMS_RESOLUTION, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shader.setUniformf(UNIFORMS_CIRCLE_POS, circlePos);
        shader.setUniformf(UNIFORMS_RADIUS, RADIUS);
        circleTexture.bind(1);
        shader.setUniformi(UNIFORMS_CIRCLE_TEXTURE, 1);
        fboTex.bind(0);
        shader.setUniformi(UNIFORMS_TEXTURE, 0);

        batch.draw(textureRegion, 0, 0, fboTex.getWidth(), fboTex.getHeight());
        batch.end();
        batch.setShader(null);
        batch.flush();
    }


    public void initTextureLettersArray() {
        cellsLettersTextures = new Texture[PLAYGROUND_NUM_CELLS_Y];

        for (int i = 0; i < cellsLettersTextures.length; i++) {
            cellsLettersTextures[i] = new Texture(Gdx.files.internal("textures/cellLetter" + i + ".png"));
        }
    }

    public void initTextureNumbersArray() {

        cellsNumbersTextures = new Texture[PLAYGROUND_NUM_CELLS_X];

        for (int i = 0; i < cellsLettersTextures.length; i++) {
            cellsNumbersTextures[i] = new Texture(Gdx.files.internal("textures/cellNumber" + i + ".png"));
        }
    }

    public void generateRandomBallCenter() {

        Random random = new Random();
        boolean isCoordFounded = false;

        while (!isCoordFounded) {
            int maxCellX = PLAYGROUND_NUM_CELLS_Y - (RADIUS / SQUARE_SIZE) + COORDINATE_X_START_PLAYGROUND + 1;
            int minCellX = (RADIUS / SQUARE_SIZE) + COORDINATE_X_START_PLAYGROUND - 1;
            int maxCellY = PLAYGROUND_NUM_CELLS_Y - (RADIUS / SQUARE_SIZE) + COORDINATE_Y_START_PLAYGROUND;
            int minCellY = (RADIUS / SQUARE_SIZE) + COORDINATE_Y_START_PLAYGROUND;
            int randomCellX = random.nextInt(maxCellX - minCellX) + minCellX;
            int randomCellY = random.nextInt(maxCellY - minCellY) + minCellY;
            int centerX = COORDINATE_X_START_PLAYGROUND * SQUARE_SIZE + randomCellX * SQUARE_SIZE;
            int centerY = COORDINATE_Y_START_PLAYGROUND * SQUARE_SIZE + randomCellY * SQUARE_SIZE;
            circlePos = new Vector2(centerX, centerY);

            if (isCircleInsideSquare(circlePos, RADIUS, SQUARE_SIZE, PLAYGROUND_NUM_CELLS_X)) {
                isCoordFounded = true;
            }
        }
    }

    public static boolean isCircleInsideSquare(Vector2 circlePos, float radius, float squareSize, int playgroundNumCells) {
        float squareLength = squareSize * playgroundNumCells;
        boolean insideLeft = circlePos.x - radius >= 0;
        boolean insideRight = circlePos.x + radius <= squareLength;
        boolean insideTop = circlePos.y - radius >= 0;
        boolean insideBottom = circlePos.y + radius <= squareLength;

        return insideLeft && insideRight && insideTop && insideBottom;
    }

    public Vector2 getCirclePos() {
        return circlePos;
    }

}
