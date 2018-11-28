package com.example.tomaszvolanek.tamz_project_com;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.tomaszvolanek.tamz_project_com.GameObjects.Enemy;
import com.example.tomaszvolanek.tamz_project_com.GameObjects.Fuel;
import com.example.tomaszvolanek.tamz_project_com.GameObjects.Player;
import com.example.tomaszvolanek.tamz_project_com.GameObjects.Projectile;

import java.util.ArrayList;

public class GameSurfaceView extends SurfaceView implements Runnable {
    private boolean isRunning = false;
    private Thread gameThread;
    private SurfaceHolder holder;

    Player player;
    ArrayList<Enemy> enemies;
    ArrayList<Fuel> fuel;
    ArrayList<Projectile> projectiles;

    private final static int MAX_FPS = 40;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;


    public  GameSurfaceView(Context context) {
        super(context);
        holder = getHolder();
    }

    public void resume() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();

    }
    public void pause() {
        isRunning = false;
        boolean retry = true;
        while(retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public void run() {
        initialize();
        while(isRunning) {
            if(!holder.getSurface().isValid()) {
                continue;
            }
            long startTime = System.currentTimeMillis();
            //update
            updateGame();
            //draw
            Canvas canvas = holder.lockCanvas();
            if(canvas != null) {
                drawGame(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
            float deltaTime = System.currentTimeMillis() - startTime;
            int sleepTime = (int) (FRAME_PERIOD - deltaTime);
            if(sleepTime > 0) {
                try {
                    gameThread.sleep(sleepTime);
                } catch(InterruptedException e) {

                }
            }
            while(sleepTime < 0 ) {
                //update
                updateGame();
                sleepTime += FRAME_PERIOD;
            }

        }

    }
    protected void updateGame() {
        player.moveDown();
        player.moveRight();
    }
    protected void drawGame(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(player.getImage(), player.getPositionX(), player.getPositionY(), null);
    }
    protected void initialize() {
        this.player = new Player(0, 0, BitmapFactory
                .decodeResource(this.getResources(), R.drawable.ship), 1);
    }
}
