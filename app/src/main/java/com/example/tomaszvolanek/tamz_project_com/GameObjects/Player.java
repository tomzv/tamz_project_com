package com.example.tomaszvolanek.tamz_project_com.GameObjects;

import android.graphics.Bitmap;

public class Player extends GameEntity {
    public Player(int xPos, int yPos, Bitmap image, double velocityX, double velocityY) {
        super(xPos, yPos, image, velocityX, velocityY);
    }

    private boolean shooting = false;

    public boolean getShooting() {
        return this.shooting;
    }
    public void setShooting(boolean b) {
        this.shooting = b;
    }


    public boolean inCanvas(int width, int height, double posX, double posY) {
        return(posX >= 0
            && posX <= width - getImage().getWidth()
            && posY >= 0
            && posY <= height - getImage().getHeight());
    }
}
