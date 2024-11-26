package com.mygdxexample.seabattle.utils;

import static com.mygdxexample.seabattle.resources.GlobalVariables.COORDINATE_X_START_PLAYGROUND;
import static com.mygdxexample.seabattle.resources.GlobalVariables.COORDINATE_Y_START_PLAYGROUND;
import static com.mygdxexample.seabattle.resources.GlobalVariables.PADDING_FOR_PLAYGROUND_BACK;
import static com.mygdxexample.seabattle.resources.GlobalVariables.RADIUS;
import static com.mygdxexample.seabattle.resources.GlobalVariables.SQUARE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.mygdxexample.seabattle.enums.Direction;
import com.mygdxexample.seabattle.model.Cell;
import com.mygdxexample.seabattle.model.Ship;

import java.util.ArrayList;
import java.util.Random;

public class ShipRepository {
    private final SpriteBatch batch;
    private static final int BOARD_SIZE = 10;
    private Random random;
    private ArrayList<Ship> ships;
    private Cell[][] cells;
    private ShaderProgram shader;
    private Vector2 circlePos;


    public ShipRepository(SpriteBatch batch, Vector2 circlePos) {
        this.circlePos = circlePos;
        this.batch = batch;
        ships = new ArrayList<>();
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        random = new Random();
        shader = new ShaderProgram(Gdx.files.internal("vertexShips.glsl"), Gdx.files.internal("fragmentShips.glsl"));

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
        ArrayList<Ship> tempShips = new ArrayList<>();
        tempShips.add(new Ship(4));
        tempShips.add(new Ship(3));
        tempShips.add(new Ship(3));
        tempShips.add(new Ship(2));
        tempShips.add(new Ship(2));
        tempShips.add(new Ship(2));
        tempShips.add(new Ship(1));
        tempShips.add(new Ship(1));
        tempShips.add(new Ship(1));
        tempShips.add(new Ship(1));
        for (Ship ship : tempShips) {
            placeShipRandomly(ship.getSize());
        }
        printCells();
    }


    private void placeShipRandomly(int size) {
        boolean placed = false;

        while (!placed) {
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            Direction direction = random.nextBoolean()
                ? Direction.HORIZONTAL
                : Direction.VERTICAL;

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

            // Проверка, свободна ли клетка
            if (!isCellFree(newX, newY)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCellFree(int x, int y) {
        for (int i = -1; i <= 1; i++) { // Проверяем соседние клетки
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
            int newX = direction == Direction.HORIZONTAL
                ? x + i
                : x;
            int newY = direction == Direction.HORIZONTAL ? y : y + i;

            cells[newX][newY].setOccupied();
        }
    }

    public void drawShip(Ship ship) {
        Texture texture = ship.getTexture();
        float x = ship.getStartX() * SQUARE_SIZE + COORDINATE_X_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;
        float y = ship.getStartY() * SQUARE_SIZE + COORDINATE_Y_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;

        batch.begin();
        batch.draw(texture, x, y);
        batch.end();

    }

    public void printCells() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                // Предположим, что у вас есть метод isEmpty() в классе Cell
                System.out.print(cells[i][j].isEmpty() ? "O " : "X "); // O - пустая клетка, X - занятая
            }
            System.out.println();
        }
    }


    public void drawShips() {
        batch.flush();

        for (Ship ship : ships) {
            drawUsingShaders(ship);
        }
    }

    public void drawUsingShaders(Ship ship) {
        batch.flush();
        batch.setShader(null);
        Texture oldShipTexture = ship.getTexture();
        Texture newShipTexture = ship.getHittedTexture();
        float x = ship.getStartX() * SQUARE_SIZE + COORDINATE_X_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;
        float y = ship.getStartY() * SQUARE_SIZE + COORDINATE_Y_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;
        float maxX = ship.isHorizontal() == Direction.HORIZONTAL ? x + ship.getSize() * SQUARE_SIZE : x + SQUARE_SIZE;
        float maxY = ship.isHorizontal() == Direction.HORIZONTAL ? y + SQUARE_SIZE : y + ship.getSize() * SQUARE_SIZE;
        Vector2 resolution = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Vector2 shipMin = new Vector2(x, y);
        Vector2 shipMax = new Vector2(maxX, maxY);

        shader.setUniformf("u_resolution", resolution);
        shader.setUniformf("u_circlePos", circlePos);
        shader.setUniformf("u_radius", RADIUS);
        shader.setUniformf("u_shipBounds",
            shipMin.x / resolution.x,
            shipMin.y / resolution.y,
            shipMax.x / resolution.x,
            shipMax.y / resolution.y);

        oldShipTexture.bind(3);
        shader.setUniformi("u_texture", 0);
        newShipTexture.bind(4);
        shader.setUniformi("u_newShipTexture", 1);

        batch.setShader(shader);

        batch.begin();
        batch.draw(oldShipTexture, x, y, maxX - x, maxY - y);
        batch.end();

        batch.setShader(null);
        batch.flush();
    }

}

