package com.zsum.wazedemo.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class HUD{
    public static int HEALTH = 100;
    private Bitmap hitPointBar, hitPoint;
    private float xHitPointBar, yHitPointBar, xHealthBar, yHealthBar;

    public HUD(Context context, int screenX, int screenY){
        /*
         * HealthBar
         */
        hitPointBar = healthBar(HEALTH * 8, 40, Color.GRAY);
        hitPoint = healthBar( HEALTH * 8, 40, Color.GREEN);

        //Lấy điểm giữa bitmap
        xHitPointBar = hitPointBar.getWidth() / 2f;
        yHitPointBar = hitPointBar.getHeight() / 2f;

        xHealthBar = screenX / 2f;
        yHealthBar = 30;
    }

    public void update(){
        if(HEALTH < 1) HEALTH = 1;
        hitPoint = healthBar( HEALTH * 8, 40, Color.GREEN);
    }

    private static Bitmap healthBar(int width, int height, int color){
        Bitmap nBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(nBitmap);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);

        canvas.drawRect(rect, paint);

        return nBitmap;
    }

    public Bitmap getHPBar(){ return hitPointBar;}
    public Bitmap getHP(){ return hitPoint;}
    public float getXCenter(){ return xHealthBar - xHitPointBar;}
    public float getYCenter(){ return yHealthBar - yHitPointBar;}
}
