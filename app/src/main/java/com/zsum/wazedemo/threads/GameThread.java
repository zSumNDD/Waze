package com.zsum.wazedemo.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.zsum.wazedemo.views.GameView;

public class GameThread extends Thread{
    private GameView gameView;
    private SurfaceHolder surfaceHolder;

    private final static int MAX_FPS = 60;
    private final static int MAX_FRAME_SKIPS = 6;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    public GameThread(GameView gameView, SurfaceHolder surfaceHolder){
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run(){
        long beginTime, timeDiff;
        int sleepTime, framesSkipped;

        //long frameStartTime;
        //long frameTime;

        while(gameView.running) {
            if(surfaceHolder == null) return;
            //frameStartTime = System.nanoTime();
            Canvas canvas = this.surfaceHolder.lockCanvas();
            if(canvas != null) {
                try {
                    synchronized (canvas) {
                        beginTime = System.currentTimeMillis();
                        framesSkipped = 0; //Reset frames skipped

                        gameView.update();
                        gameView.draw(canvas);

                        timeDiff = System.currentTimeMillis() - beginTime;
                        sleepTime = (int)(FRAME_PERIOD - timeDiff);
                    }
                    if(sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                        gameView.update();

                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            /*
            frameTime = (System.nanoTime() - frameStartTime) / 1000000;
            if (frameTime < (int) (1000.0 / 60.0)){
                try {
                    Thread.sleep((int) (1000.0 / 60.0) - frameTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            */
        }
        /*
        long beginTime, timeDiff;
        int sleepTime, framesSkipped;

        while(running){
            canvas = null;
            try{
                canvas = this.holder.lockCanvas();
                synchronized (holder){
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0; //Reset frames skipped

                    update();
                    draw(canvas);

                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);
                }
                if(sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                    update();

                    sleepTime += FRAME_PERIOD;
                    framesSkipped++;
                }
            }finally {
                if(canvas != null) holder.unlockCanvasAndPost(canvas);
            }
        }
        */
    }
}
