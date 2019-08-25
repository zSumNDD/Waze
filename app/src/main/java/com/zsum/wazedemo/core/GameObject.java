package com.zsum.wazedemo.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public abstract class GameObject {
    private Context context;
    protected ID id;
    protected int screenX, screenY;
    protected Bitmap bitmap;
    protected float xBitmap, yBitmap, x, y, velX, velY;


    public GameObject(ID id, Context context, int screenX, int screenY){
        this.id = id;
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();
    public abstract Rect getBounds();

    public static Bitmap circleBitmap(int width, int height, int color){
        Bitmap nBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(nBitmap);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);

        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(nBitmap, rect, rect, paint);

        return nBitmap;
    }

    //Set
    public void setID(ID id){ this.id = id;}

    public void setContext(Context context){
        this.context = context;
    }

    public void setScreenX(int screenX){
        this.screenX = screenX;
    }

    public void setScreenY(int screenY){
        this.screenY = screenY;
    }

    protected void setBitmap(Bitmap bitmap){ this.bitmap = bitmap;}

    protected void setXBitmap(float x){ this.xBitmap = x;}

    protected void setYBitmap(float y){ this.yBitmap = y;}

    protected void setX(float x){ this.x = x;}

    protected void setY(float y){ this.y = y;}

    public void setVelX(float velX){ this.velX = velX;}

    public void setVelY(float velY){ this.velY = velY;}

    //Get
    public ID getID(){ return id;}

    public Context getContext(){
        return context;
    }

    public int getScreenX(){
        return screenX;
    }

    public int getScreenY(){
        return screenY;
    }

    public Bitmap getBitmap(){ return bitmap;}

    public float getXBitmap(){ return xBitmap;}

    public float getYBitmap(){ return yBitmap;}

    public float getX(){ return x;}

    public float getY(){ return y;}

    public float getVelX(){ return velX;}

    public float getVelY(){ return velY;}
}
