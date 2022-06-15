package com.example.demo;


public class Player {
    private String id;
    private String name;
    private int score;
    private int color;

    public Player(String id) {
        this.id = id;
        score = 0;
    }

    public Player(){ }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getColor() {
        return color;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void resetScore(){
        score = 0;
    }

}
