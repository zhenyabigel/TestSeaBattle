package com.mygdxexample.seabattle.model;

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

    public void setOccupied() {
        this.isEmpty = false;
    }
}
