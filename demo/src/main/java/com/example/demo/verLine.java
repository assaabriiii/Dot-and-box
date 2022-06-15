package com.example.demo;

public class verLine {
    private int x;
    private int y;
    private int color;
    private int status;

    public verLine(int x, int y, int color, int status) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public int getStatus() {
        return status;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
