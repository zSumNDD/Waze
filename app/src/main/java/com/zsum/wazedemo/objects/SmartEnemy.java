package com.zsum.wazedemo.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.zsum.wazedemo.core.GameObject;
import com.zsum.wazedemo.core.Handler;
import com.zsum.wazedemo.core.ID;

import java.util.Random;

public class SmartEnemy extends GameObject {
    private Handler handler;
    private GameObject player;

    public SmartEnemy(ID id, Context context, int screenX, int screenY, int x, int y, int color, Handler handler){
        super(id, context, screenX, screenY);
        this.handler = handler;

        for(int i = 0; i < handler.objects.size(); i++){
            if(handler.objects.get(i).getID() == ID.Player) player = handler.objects.get(i);
        }

        setBitmap(circleBitmap(40, 40, color));

        //Lấy điểm giữa bitmap
        setXBitmap(bitmap.getWidth() / 2f);
        setYBitmap(bitmap.getHeight() / 2f);

        setX(x);
        setY(y);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(getBitmap(), getX(), getY(), null);
    }

    public void update(){
        x += velX;
        y += velY;

        float diffX = x - player.getX() - bitmap.getWidth();
        float diffY = y - player.getY() - bitmap.getHeight();
        float distance = (float) Math.sqrt( (x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));

        velX = ((-1 / distance) * diffX * 4);
        velY = ((-1 / distance) * diffY * 4);
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
