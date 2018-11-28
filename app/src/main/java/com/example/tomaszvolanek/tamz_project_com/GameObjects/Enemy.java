package com.example.tomaszvolanek.tamz_project_com.GameObjects;

import android.graphics.Bitmap;

public class Enemy extends GameEntity {
    public Enemy(int xPos, int yPos, Bitmap image, double velocity) {
        super(xPos, yPos, image, velocity);
    }
}
