package com.mygdxexample.seabattle.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdxexample.seabattle.utils.Direction;

public class Ship {
    private int size;           // Размер корабля
    private int startRow;      // Начальная строка
    private int startCol;      // Начальный столбец
    private Direction direction; // Направление (горизонтальное или вертикальное)
    private Texture texture;

    public Ship(int size, int startRow, int startCol, Direction direction) {
        this.size = size;
        this.startRow = startRow;
        this.startCol = startCol;
        this.direction = direction;
        this.texture = loadTexture(size, direction); // Загружаем текстуру
    }

    public Ship(int size) {
        this.size = size;
        this.startRow = 0;
        this.startCol = 0;
        this.direction = Direction.HORIZONTAL;
        this.texture = new Texture(Gdx.files.internal("textures/transparent.png"));
        // Загружаем текстуру
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public Direction isHorizontal() {
        return direction;
    }

    public Texture getTexture() {
        return texture; // Геттер для текстуры
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setTexture() {
        texture = loadTexture(size, direction);
    }

    public Direction getDirection() {
        return direction;
    }

    private Texture loadTexture(int size, Direction direction) {
        String textureName = "textures/ship"; // Базовое имя текстуры
        textureName += size; // Добавляем размер
        textureName += direction == Direction.VERTICAL ? "vertical.png" : "horizontal.png";

        return new Texture(Gdx.files.internal(textureName));
    }
}
