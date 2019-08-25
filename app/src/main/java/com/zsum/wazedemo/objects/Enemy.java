package com.zsum.wazedemo.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.zsum.wazedemo.core.GameObject;
import com.zsum.wazedemo.core.ID;

public class Enemy extends GameObject {

    public Enemy(ID id, Context context, int screenX, int screenY, float x, float y, int velX, int velY, int color){
        super(id, context, screenX, screenY);

        setBitmap(circleBitmap(40, 40, color));

        //Lấy điểm giữa bitmap
        setXBitmap(bitmap.getWidth() / 2f);
        setYBitmap(bitmap.getHeight() / 2f);

        setX(x); setY(y); setVelX(velX); setVelY(velY);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(getBitmap(), getX(), getY(), null);
    }

    public void update() {
        x += velX;
        y += velY;

        if (x <= 0 || x >= screenX) velX *= -1;
        if (y <= 0 || y >= screenY) velY *= -1;
    }

    public Rect getBounds() {
        return new Rect((int) x, (int) y, (int) x + bitmap.getWidth(), (int) y + bitmap.getHeight());
    }

    public Bitmap getBitmap(){ return bitmap;}
    @Override
    public float getX(){ return x - xBitmap;}
    @Override
    public float getY(){ return y - yBitmap;}
}
