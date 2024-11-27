package com.mygdxexample.seabattle.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.mygdxexample.seabattle.enums.Direction;
import com.mygdxexample.seabattle.model.Cell;
import com.mygdxexample.seabattle.model.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mygdxexample.seabattle.resources.GlobalVariables.*;

public class ShipService {
    private final SpriteBatch batch;
    private static final int BOARD_SIZE = 10;
    private final Random random;
    private ArrayList<Ship> ships;
    private Cell[][] cells;
    private final ShaderProgram shader;
    private final ShaderProgram shaderHitted;
    private final Vector2 circlePos;
    private final FrameBuffer fboShips;
    private final FrameBuffer fboHitted;


    public ShipService(SpriteBatch batch, Vector2 circlePos) {
        this.circlePos = circlePos;
        this.batch = batch;
        ships = new ArrayList<>();
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        random = new Random();
        shader = new ShaderProgram(Gdx.files.internal(VERTEX_SHIP_PATH), Gdx.files.internal(FRAGMENT_SHIP_PATH));
        shaderHitted = new ShaderProgram(Gdx.files.internal(VERTEX_HITTED_PATH), Gdx.files.internal(FRAGMENT_HITTED_PATH));
        fboShips = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        fboHitted = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        initCells();
        initializeShips();
    }

    public void generateRandomShips() {
        ships = new ArrayList<>();
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];

        initCells();
        initializeShips();
    }

    private void initCells() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void initializeShips() {
        List<Ship> tempShips = new ArrayList<>(List.of(
            new Ship(4),
            new Ship(3),
            new Ship(3),
            new Ship(2),
            new Ship(2),
            new Ship(2),
            new Ship(1),
            new Ship(1),
            new Ship(1),
            new Ship(1)));

        for (Ship ship : tempShips) {
            placeShipRandomly(ship.getSize());
        }
    }


    private void placeShipRandomly(int size) {
        boolean placed = false;

        while (!placed) {
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

            if (canPlaceShip(x, y, size, direction)) {
                Ship ship = new Ship(size, x, y, direction);
                ships.add(ship);
                markShipOnField(ship);
                placed = true;
            }
        }
    }

    private boolean canPlaceShip(int x, int y, int size, Direction direction) {

        if (direction == Direction.HORIZONTAL) {
            if (x + size > BOARD_SIZE) {
                return false;
            }
        } else {
            if (y + size > BOARD_SIZE) {
                return false;
            }
        }

        for (int i = 0; i < size; i++) {
            int newX = direction == Direction.HORIZONTAL ? x + i : x;
            int newY = direction == Direction.HORIZONTAL ? y : y + i;

            if (!isCellFree(newX, newY)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCellFree(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = x + i;
                int neighborY = y + j;

                if (neighborX >= 0 && neighborX < BOARD_SIZE && neighborY >= 0 && neighborY < BOARD_SIZE) {
                    if (!cells[neighborX][neighborY].isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void markShipOnField(Ship ship) {
        int x = ship.getStartX();
        int y = ship.getStartY();
        int size = ship.getSize();
        Direction direction = ship.getDirection();

        for (int i = 0; i < size; i++) {
            int newX = direction == Direction.HORIZONTAL ? x + i : x;
            int newY = direction == Direction.HORIZONTAL ? y : y + i;

            cells[newX][newY].setOccupied();
        }
    }

    public void drawShip(Ship ship, Ship.Type type) {
        Texture texture = type == Ship.Type.NOT_HITTED
            ? ship.getTexture()
            : ship.getHittedTexture();
        float x = ship.getStartX() * SQUARE_SIZE + COORDINATE_X_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;
        float y = ship.getStartY() * SQUARE_SIZE + COORDINATE_Y_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;
        float width = ship.getDirection() == Direction.HORIZONTAL
            ? ship.getSize() * SQUARE_SIZE
            : SQUARE_SIZE;
        float heigth = ship.getDirection() == Direction.VERTICAL
            ? ship.getSize() * SQUARE_SIZE
            : SQUARE_SIZE;


        batch.draw(texture, x, y, width, heigth);

    }


    public void drawShips() {
        batch.begin();
        for (Ship ship : ships) {
            drawShip(ship, Ship.Type.NOT_HITTED);
        }
        batch.flush();
        batch.end();
    }

    public void drawShipsWithShaders() {

        Texture ships = createShips();

        ships.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ships.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        ShaderProgram.pedantic = false;

        if (!shader.isCompiled()) {
            System.err.println(SHADER_STRING + shader.getLog());
        }

        shader.bind();

        shader.setUniformf(CIRCLE_RADIUS_UNIFORM, RADIUS);
        shader.setUniformf(CIRCLE_CENTER_UNIFORM, circlePos);
        shader.setUniformf(
            TEXTURE_SIZE_UNIFORM, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shader.setUniformi(MASK_UNIFORM, 0);
        ships.bind(0);

        batch.setShader(shader);
        batch.begin();
        batch.draw(ships, 0, 0);
        batch.end();
    }

    public void drawHittedShipsWithShaders() {

        Texture mask = createHitShips();

        mask.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mask.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);

        ShaderProgram.pedantic = false;

        if (!shaderHitted.isCompiled()) {
            System.err.println(SHADER_STRING + shader.getLog());
        }


        shaderHitted.bind();


        shaderHitted.setUniformf(CIRCLE_RADIUS_UNIFORM, RADIUS);
        shaderHitted.setUniformf(CIRCLE_CENTER_UNIFORM, circlePos);
        shaderHitted.setUniformf(
            TEXTURE_SIZE_UNIFORM, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shaderHitted.setUniformi(MASK_UNIFORM, 0);
        mask.bind(0);

        batch.setShader(shaderHitted);
        batch.begin();
        batch.draw(mask, 0, 0);
        batch.end();
    }


    public Texture createShips() {
        fboShips.begin();
        batch.begin();

        for (Ship ship : ships) {
            drawShip(ship, Ship.Type.NOT_HITTED);
        }

        batch.end();
        fboShips.end();
        TextureRegion textureRegion = new TextureRegion(fboShips.getColorBufferTexture());
        textureRegion.flip(false, true);

        return textureRegion.getTexture();
    }

    public Texture createHitShips() {
        fboHitted.begin();
        batch.begin();

        for (Ship ship : ships) {
            drawShip(ship, Ship.Type.HITTED);
        }

        batch.end();
        fboHitted.end();
        TextureRegion textureRegion = new TextureRegion(fboHitted.getColorBufferTexture());
        textureRegion.flip(false, true);

        return textureRegion.getTexture();
    }

    public void dispose() {
        fboShips.dispose();
        fboHitted.dispose();
        batch.dispose();
        shader.dispose();
        shaderHitted.dispose();
    }
}
