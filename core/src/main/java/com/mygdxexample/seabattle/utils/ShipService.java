package com.mygdxexample.seabattle.utils;

import com.badlogic.gdx.graphics.Texture;
import com.mygdxexample.seabattle.SeaBattleGame;
import com.mygdxexample.seabattle.model.Cell;
import com.mygdxexample.seabattle.model.Ship;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdxexample.seabattle.resources.GlobalVariables.*;

public class ShipService {
    private final SeaBattleGame game;
    private static final int BOARD_SIZE = 10;
    private Cell[][] board;
    private Random random;
    private ArrayList<Ship> ships;           // Список кораблей
    private Cell[][] cells;


    public ShipService(SeaBattleGame game) {
        this.game = game;
        ships = new ArrayList<>();
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        random = new Random();

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

    // Размещаем корабль случайным образом
    private void placeShipRandomly(int size) {
        boolean placed = false;


        while (!placed) {
            int x = random.nextInt(BOARD_SIZE); // Случайная начальная точка (x)
            int y = random.nextInt(BOARD_SIZE); // Случайная начальная точка (y)
            Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL; // Случайное направление

            if (canPlaceShip(x, y, size, direction)) {
                Ship ship = new Ship(size, x, y, direction);
                ships.add(ship); // Добавляем корабль в список
                markShipOnField(ship); // Помечаем клетки, занятые кораблём
                placed = true;
            }
        }
    }

    private boolean canPlaceShip(int x, int y, int size, Direction direction) {
        // Проверка, помещается ли корабль в пределах поля
        if (direction == Direction.HORIZONTAL) {
            if (x + size > BOARD_SIZE) {
                return false; // Корабль выходит за пределы по горизонтали
            }
        } else { // Вертикально
            if (y + size > BOARD_SIZE) {
                return false; // Корабль выходит за пределы по вертикали
            }
        }

        // Проверка на занятость клеток
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

    // Проверка, свободна ли клетка и её соседи
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

    // Пометка клеток поля, занятых кораблём
    private void markShipOnField(Ship ship) {
        int x = ship.getStartRow();
        int y = ship.getStartCol();
        int size = ship.getSize();
        Direction direction = ship.getDirection();

        for (int i = 0; i < size; i++) {
            int newX = direction == Direction.HORIZONTAL ? x + i : x;
            int newY = direction == Direction.HORIZONTAL ? y : y + i;

            cells[newX][newY].setOccupied(); // Помечаем клетку как занятую
        }
    }

    public void drawShip(Ship ship) {
        Texture texture = ship.getTexture(); // Получаем текстуру корабля
        float x = ship.getStartRow() * SQUARE_SIZE + COORDINATE_X_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;
        float y = ship.getStartCol() * SQUARE_SIZE + COORDINATE_Y_START_PLAYGROUND * SQUARE_SIZE - PADDING_FOR_PLAYGROUND_BACK;

        game.batch.begin();

        for (int i = 0; i < ship.getSize(); i++) {
            game.batch.draw(texture, x, y);
        }

        game.batch.end();
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
        for (Ship ship : ships) {
            drawShip(ship);
        }
    }

}

