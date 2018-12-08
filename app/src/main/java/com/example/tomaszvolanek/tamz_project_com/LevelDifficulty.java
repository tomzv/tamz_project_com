package com.example.tomaszvolanek.tamz_project_com;

public class LevelDifficulty {

    private long enemySpawnTime;
    private double enemyVelocity;
    private int playerFuel;
    private int fuelShips;

    public long getEnemySpawnTime() {
        return this.enemySpawnTime;
    }
    public void setEnemySpawnTime(long time) {
        this.enemySpawnTime = time;
    }
    public double getEnemyVelocity() {
        return this.enemyVelocity;
    }
    public void setEnemyVelocity(double velocity) {
        this.enemyVelocity = velocity;
    }
    public int getPlayerFuel() {
        return this.playerFuel;
    }
    public void setPlayerFuel(int fuel){
        this.playerFuel = fuel;
    }
    public int getFuelShips() {
        return this.fuelShips;
    }
    public void setFuelShips(int ships) {
        this.fuelShips = ships;
    }


}
