package com.example.tomaszvolanek.tamz_project_com.GameObjects;

import android.graphics.Bitmap;

public class Projectile extends GameEntity {
    public Projectile(int xPos, int yPos, Bitmap image, double velocity) {
        super(xPos, yPos, image, velocity);
    }
}
