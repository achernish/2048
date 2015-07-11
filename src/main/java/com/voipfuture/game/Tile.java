package com.voipfuture.game;

/**
 * @author Anatoly Chernysh
 */
public class Tile {

    private int value;

    public Tile() {
        this(0);
    }

    public Tile(int num) {
        value  = num;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}