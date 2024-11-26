package com.mygdxexample.seabattle.model;

import lombok.ToString;

@ToString
public class Cell {

    private boolean isEmpty;

    public Cell(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Cell() {
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    // Метод для установки состояния ячейки
    public void setOccupied() {
        this.isEmpty = false; // Если ячейка занята, то она не пустая
    }
}
