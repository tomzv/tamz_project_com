package com.example.tomaszvolanek.tamz_project_com.GameObjects;

public abstract class GameEntity {


    private int positionX;
    private int positionY;

    private int pixelHeight;
    private int pixelWidth;

    private double velocity;

    public GameEntity(int xPos, int yPos, int pxHeight, int pxWidth) {


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

    public double getVelocity() {
        return this.velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void moveUp() {
        this.positionY -= velocity;
    }

    public void moveDown() {
        this.positionY += velocity;
    }

    public void moveRight() {
        this.positionX += velocity;
    }

    public void moveLeft() {
        this.positionX -= velocity;
    }
}
