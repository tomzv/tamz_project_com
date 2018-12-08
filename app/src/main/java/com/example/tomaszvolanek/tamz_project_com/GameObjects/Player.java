package com.example.tomaszvolanek.tamz_project_com.GameObjects;

import android.graphics.Bitmap;

public class Player extends GameEntity {
    public Player(int xPos, int yPos, Bitmap image, double velocityX, double velocityY) {
        super(xPos, yPos, image, velocityX, velocityY);
    }

    private int score;
    private boolean shooting = false;
    private int fuel;

    public boolean getShooting() {
        return this.shooting;
    }
    public void setShooting(boolean b) {
        this.shooting = b;
    }

    public int getFuel() {
        return this.fuel;
    }
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }


    public boolean inCanvas(int width, int height, double posX, double posY) {
        return(posX >= 0
            && posX <= width - getImage().getWidth()
            && posY >= 0
            && posY <= height - getImage().getHeight());
    }
}
