package com.example.demo;

public class Tile {
    private int x;
    private int y;
    private int color;
    private int status;

    public Tile(int x, int y, int color, int status) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.status = status;
    }

    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStatus() {
        return status;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
