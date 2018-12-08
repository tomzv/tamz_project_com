package com.example.tomaszvolanek.tamz_project_com;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class GameSurfaceView extends SurfaceView implements Runnable {
    private boolean isRunning = false;
    private Thread gameThread;
    private SurfaceHolder holder;

    //field members encapsulating game difficulty
    private String difficultyFile = "difficultyhard.xml";
    private LevelDifficulty levelDifficulty;

    //height and width of the canvas holder
    private int height;
    private int width;

    //game entities
    Player player;
    ArrayList<GameEntity> enemies = new ArrayList<GameEntity>();
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    //fps
    private final static int MAX_FPS = 40;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    //timers
    private long shotTimer = 500;
    private long lastShotTime;
    private long lastSpawn;
    private int fuelSpawn = 0;


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
    //runs the game loop
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
    //updates the game entities
    protected void updateGame() {
        Bitmap enemyImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.enemy);
        Bitmap fuelImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.fuel);
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
        //checks if an enemy can be spawned and spawns the enemy
        if((System.currentTimeMillis()- lastSpawn) > levelDifficulty.getEnemySpawnTime()) {
            Random rnd = new Random();
            int spawnX = rnd.nextInt(this.width-enemyImage.getWidth());
            int spawnY = this.height;
            //checks if a fuel ship needs to be spawned, if not, then spawns a regular enemy
            if(this.fuelSpawn % levelDifficulty.getFuelShips() == 0) {
                enemies.add(new Fuel(spawnX,
                        spawnY,
                        fuelImage,0, levelDifficulty.getEnemyVelocity()));
                fuelSpawn++;
            } else {
                enemies.add(new Enemy(spawnX,
                        spawnY,
                        enemyImage,0, levelDifficulty.getEnemyVelocity()));
                lastSpawn = System.currentTimeMillis();
                fuelSpawn++;
            }

        }
        //checks if the projectile can be fired and spaws the projectile
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
    //draws the game state onto the canvas
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
    //initializes the game state
    protected void initialize()  {
        this.LoadLevelDifficulty(this.difficultyFile);
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
    //loads level difficulty into a class field from xml files in the assets folder
    public void LoadLevelDifficulty(String fileName)  {
        LevelXmlParser parser = new LevelXmlParser();
        InputStream raw =  null;

        AssetManager am = this.getContext().getAssets();
        try {

            raw = am.open(fileName);

            List<LevelDifficulty> xmlList = parser.parse(raw);
            this.levelDifficulty = xmlList.get(0);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(XmlPullParserException e) {
            e.printStackTrace();
        }

    }
}
