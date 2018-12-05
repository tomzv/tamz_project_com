package com.example.tomaszvolanek.tamz_project_com.GameObjects;

import android.graphics.Bitmap;

public class Enemy extends GameEntity {
    public Enemy(int xPos, int yPos, Bitmap image, double velocityX, double velocityY) {
        super(xPos, yPos, image, velocityX, velocityY);
    }
}
