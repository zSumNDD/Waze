package com.zsum.wazedemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zsum.wazedemo.core.Handler;
import com.zsum.wazedemo.core.ID;
import com.zsum.wazedemo.objects.Enemy;
import com.zsum.wazedemo.objects.Player;
import com.zsum.wazedemo.objects.SmartEnemy;
import com.zsum.wazedemo.threads.GameThread;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    /**
     * Drawing
     */
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    /**
     * Game Thread
     */
    private GameThread gameThread;

    /**
     * Boolean thread
     */
    private volatile boolean surfaceReady = false;
    public volatile boolean running = false;
    /**
     * Boolean game
     */
    private volatile boolean begin = false;
    public static boolean endGame = false;

    protected int beginTime = 4;
    protected int waze = 0;
    protected int wazeTimer = 10;

    private Handler handler;

    private HUD hud;

    private final Random random = new Random();

    private CountDownTimer countDownTimer;

    private int screenX, screenY;

    public GameView(final Context context, final int screenX, final int screenY){
        super(context);
        setFocusable(true);
        setZOrderOnTop(true);
        setWillNotDraw(false);

        this.screenX = screenX;
        this.screenY = screenY;

        paint = new Paint();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        handler = new Handler();

        hud = new HUD(context, screenX, screenY);

        /**
         * Add player
         */
        handler.addObject(new Player(ID.Player, context, screenX, screenY, handler));

        /**
         * Count down for begin
         * Seconds: 3
         */
        new CountDownTimer(4000, 1000){

            @Override
            public void onTick(long l) {
                beginTime--;
            }

            @Override
            public void onFinish() {
                begin = true;
                waze = 1;
            }
        }.start();
        /**
         * Count down for waze
         * Second: 10
         */
        countDownTimer =  new CountDownTimer(10000, 1000){

            @Override
            public void onTick(long l) {
                wazeTimer--;
            }

            @Override
            public void onFinish() {
                waze++;
                wazeTimer = 10;
                /**
                 * Add enemy
                 */
                if(waze >= 2 && waze < 4) handler.addObject(new Enemy(ID.Enemy, context, screenX, screenY, random.nextInt(screenX), random.nextInt(screenY), 10, 10, Color.RED));
                if(waze >= 4 && waze < 5) handler.addObject(new Enemy(ID.Enemy, context, screenX, screenY, random.nextInt(screenX), random.nextInt(screenY), 15, 15, Color.CYAN));
                if(waze >= 5 && waze < 6) handler.addObject(new SmartEnemy(ID.SmartEnemy, context, screenX, screenY, random.nextInt(screenX), random.nextInt(screenY), Color.YELLOW, handler));
                if(waze >= 6 && waze < 8) handler.addObject(new Enemy(ID.Enemy, context, screenX, screenY, random.nextInt(screenX), random.nextInt(screenY), 10, 10, Color.RED));
            }
        };
    }

    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        if(surfaceHolder.getSurface().isValid()){
            //Màu nền
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);

            canvas.drawText("" + beginTime, screenX / 2, screenY / 2, paint);

            /**
             * Begin Game
             */
            if(begin){
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                handler.draw(canvas);
            }

            /**
             * HUD
             */
            canvas.drawBitmap(hud.getHPBar(), hud.getXCenter(), hud.getYCenter(), null);
            canvas.drawBitmap(hud.getHP(), hud.getXCenter(), hud.getYCenter(), null);

            canvas.drawText("Waze " + waze, 10, 50, paint);
            canvas.drawText("" + wazeTimer, 1210, 50, paint);

            /**
             * End Game
             */
            if(endGame){
                paint.setTextSize(70);
                canvas.drawText("Game Over", (screenX / 2) - 180, screenY / 2 , paint);
                stopThread();
            }
        }
    }

    public void update(){
        if(begin){
            if(wazeTimer == 10) countDownTimer.start();
            hud.update();
            handler.update();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        if(gameThread != null){
            running = false;
            try{
                gameThread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        surfaceReady = true;
        startThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        if(width == 0 || height == 0) return;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopThread();

        surfaceHolder.getSurface().release();

        surfaceReady = false;
        this.surfaceHolder = null;
    }

    public static int clamp(int var, int min, int max){
        if(var >= max) return var = max;
        else if(var <= min) return var = min;
        else return var;
    }

    public void startThread(){
        if(surfaceReady && gameThread == null){
            gameThread = new GameThread(this, surfaceHolder);
            running = true;

            gameThread.start();
        }
    }

    public void pause(){
        running = false;
        while(true){
            try{
                gameThread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            break;
        }
        gameThread = null;
    }

    public void resume(){
        running = true;
        //sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        gameThread = new GameThread(this, surfaceHolder);
        gameThread.start();
    }

    public void stopThread(){
        if(gameThread == null) return;
        running = false;
        while(true) {
            try {
                gameThread.join(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        gameThread = null;
    }
}
