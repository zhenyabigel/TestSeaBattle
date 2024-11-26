package com.mygdxexample.seabattle.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdxexample.seabattle.enums.Direction;

public class Ship {
    private int size;
    private int startX;
    private int startY;
    private Direction direction;
    private final Texture texture;
    private Texture hittedTexture;

    public Ship(int size, int startX, int startY, Direction direction) {
        this.size = size;
        this.startX = startX;
        this.startY = startY;
        this.direction = direction;
        this.texture = loadTexture(size, direction, Type.NOT_HITTED);
        this.hittedTexture = loadTexture(size, direction, Type.HITTED);
    }

    public Ship(int size) {
        this.size = size;
        this.startX = 0;
        this.startY = 0;
        this.direction = Direction.HORIZONTAL;
        this.texture = new Texture(Gdx.files.internal("textures/transparent.png"));
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public Direction isHorizontal() {
        return direction;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public Direction getDirection() {
        return direction;
    }

    private Texture loadTexture(int size, Direction direction, Type type) {
        String textureName = type == Type.NOT_HITTED ? "textures/ship" : "textures/hitted_ship";
        textureName += size;
        textureName += direction == Direction.VERTICAL ? "vertical.png" : "horizontal.png";

        return new Texture(Gdx.files.internal(textureName));
    }

    public Texture getHittedTexture() {
        return hittedTexture;
    }

    public void setHittedTexture(Texture hittedTexture) {
        this.hittedTexture = hittedTexture;
    }

    private enum Type {
        HITTED, NOT_HITTED
    }
}
