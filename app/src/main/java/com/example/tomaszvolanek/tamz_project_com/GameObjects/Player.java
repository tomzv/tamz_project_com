package com.example.tomaszvolanek.tamz_project_com.GameObjects;

import android.graphics.Bitmap;

public class Player extends GameEntity {
    public Player(int xPos, int yPos, Bitmap image, double velocityX, double velocityY) {
        super(xPos, yPos, image, velocityX, velocityY);
    }

}
