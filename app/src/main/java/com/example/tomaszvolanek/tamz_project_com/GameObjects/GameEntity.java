package com.example.tomaszvolanek.tamz_project_com.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

public abstract class GameEntity {


    private int positionX;
    private int positionY;

    private int pixelHeight;
    private int pixelWidth;

    private double velocityX;
    private double velocityY;

    Bitmap image;

    public GameEntity(int xPos, int yPos, Bitmap image, double velocityX, double velocityY) {
        this.positionX = xPos;
        this.positionY = yPos;
        this.image = image;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPixelHeight() {
        return this.pixelHeight;
    }

    public void setPixelHeight(int pixelHeight) {
        this.pixelHeight = pixelHeight;
    }

    public int getPixelWidth() {
        return this.pixelWidth;
    }

    public void setPixelWidth(int pixelWidth) {
        this.pixelWidth = pixelWidth;
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityX(double velocity) {
        this.velocityX = velocity;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityY(double velocity) {
        this.velocityY = velocity;
    }

    public void move() {
        this.positionX += this.velocityX;
        this.positionY += this.velocityY;
    }


    public Bitmap getImage() {
        return this.image;
    }
    public Rect getBoundary() {
        return new Rect(this.positionX,
                this.positionY,
                this.image.getHeight()+this.positionX,
                this.image.getWidth()+this.positionY);
    }


}
