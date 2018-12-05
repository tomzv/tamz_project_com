package com.example.tomaszvolanek.tamz_project_com.GameObjects;

import android.graphics.Bitmap;

public class Projectile extends GameEntity {
    public Projectile(int xPos, int yPos, Bitmap image, double velocityX, double velocityY) {
        super(xPos, yPos, image, velocityX, velocityY);
    }
}
