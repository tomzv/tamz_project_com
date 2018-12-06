package com.example.tomaszvolanek.tamz_project_com;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.tomaszvolanek.tamz_project_com.GameObjects.Enemy;
import com.example.tomaszvolanek.tamz_project_com.GameObjects.Fuel;
import com.example.tomaszvolanek.tamz_project_com.GameObjects.GameEntity;
import com.example.tomaszvolanek.tamz_project_com.GameObjects.Player;
import com.example.tomaszvolanek.tamz_project_com.GameObjects.Projectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameSurfaceView extends SurfaceView implements Runnable {
    private boolean isRunning = false;
    private Thread gameThread;
    private SurfaceHolder holder;

    private int height;
    private int width;

    Player player;
    ArrayList<GameEntity> enemies = new ArrayList<GameEntity>();
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    private final static int MAX_FPS = 40;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    private long shotTimer = 500;
    private long lastShotTime;
    private long enemySpawnTime = 1000;
    private long lastSpawn;


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

            this.height = holder.getSurfaceFrame().height();
            this.width = holder.getSurfaceFrame().width();

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
        Bitmap enemyImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.enemy);
        Bitmap projectileImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.projectile);

        //checks if a new enemy can be spawned
        Iterator<GameEntity> enemyIterator = enemies.iterator();
        while(enemyIterator.hasNext()) {

            GameEntity e = enemyIterator.next();
            if(e.getBoundary().intersect(player.getBoundary())) {
                enemyIterator.remove();
                isRunning = false;

            }
            else if(!e.inCanvas(width, height)) {
                enemyIterator.remove();
            }
            else {
                Iterator<Projectile> projectileIterator = projectiles.iterator();
                while(projectileIterator.hasNext()) {
                    Projectile p = projectileIterator.next();
                    if(e.getBoundary().intersect(p.getBoundary())) {
                        enemyIterator.remove();
                        projectileIterator.remove();

                        /*
                        player.addScore();
                        if(e instanceof Fuel) {
                            player.addFuel();
                        }
                        break;
                        */
                    }
                }

            }


        }
        //checks if a projectile can be fired
        if((System.currentTimeMillis()- lastSpawn) > enemySpawnTime) {
            Random rnd = new Random();
            int spawnX = rnd.nextInt(this.width-enemyImage.getWidth());
            int spawnY = this.height;
            enemies.add(new Enemy(spawnX,
                    spawnY,
                    BitmapFactory.decodeResource(this.getResources(), R.drawable.enemy),0, -5));
            lastSpawn = System.currentTimeMillis();
        }
        if(player.getShooting() && (System.currentTimeMillis() - lastShotTime) > shotTimer) {
            projectiles.add(
                    new Projectile(
                            player.getPositionX(),
                            player.getPositionY() + player.getImage().getHeight(),
                            projectileImage,
                            0, 10
                    ));
            projectiles.add(
                    new Projectile(
                            player.getPositionX() + player.getImage().getWidth()-projectileImage.getWidth(),
                            player.getPositionY() + player.getImage().getHeight(),
                            projectileImage,
                            0, 10
                    ));
            lastShotTime = System.currentTimeMillis();
        }
        //moves projectiles and enemies
        for(Projectile projectile : projectiles) {
            projectile.move();
        }
        for(GameEntity enemy : enemies) {
            enemy.move();
        }
        if(player.inCanvas(width, height,
                player.getPositionX()+ player.getVelocityX(),
                player.getPositionY() + player.getVelocityY())) {
            player.move();
        }

    }
    protected void drawGame(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(player.getImage(), player.getPositionX(), player.getPositionY(), null);

        for(GameEntity enemy : enemies) {
            canvas.drawBitmap(enemy.getImage(), enemy.getPositionX(), enemy.getPositionY(), null);
        }
        for(Projectile projectile : projectiles) {
            canvas.drawBitmap(projectile.getImage(), projectile.getPositionX(), projectile.getPositionY(), null);

        }
    }
    protected void initialize() {
        this.player = new Player(100, 100, BitmapFactory
                .decodeResource(this.getResources(), R.drawable.ship), 0, 0);
        lastShotTime = System.currentTimeMillis();
        lastSpawn = System.currentTimeMillis();
    }

    public void onSensorEvent(SensorEvent event) {
        player.setVelocityX(event.values[0]*(-2));
        player.setVelocityY(event.values[1]*(2));
    }

    public void onTouch(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                player.setShooting(true);
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                player.setShooting(false);
                break;
            }
        }

    }
}
