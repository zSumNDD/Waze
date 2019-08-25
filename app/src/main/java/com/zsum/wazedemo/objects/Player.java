package com.zsum.wazedemo.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.zsum.wazedemo.R;
import com.zsum.wazedemo.core.GameObject;
import com.zsum.wazedemo.core.Handler;
import com.zsum.wazedemo.core.ID;
import com.zsum.wazedemo.views.GameView;
import com.zsum.wazedemo.views.HUD;

public class Player extends GameObject implements SensorEventListener {
    private Handler handler;

    public Player(ID id, Context context, int screenX, int screenY, Handler handler){
        super(id, context, screenX, screenY);
        this.handler = handler;

        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

        setBitmap(circleBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player), 80, 80, true)));

        //Lấy điểm giữa bitmap
        setXBitmap(bitmap.getWidth() / 2f);
        setYBitmap(bitmap.getHeight() / 2f);

        //Spawn giữa màn hình
        setX(screenX / 2f);
        setY(screenY / 2f);
    }

    @Override
    public void onAccuracyChanged(Sensor arg, int agr1){}

    @Override
    public void onSensorChanged(SensorEvent e){
        if(e.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            x += e.values[1] * 5; y += e.values[0] * 5;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(getBitmap(), getX(), getY(), null);
    }

    public void update(){
        x = GameView.clamp((int) x, (int) xBitmap, (int) (screenX - xBitmap));
        y = GameView.clamp((int) y, (int) yBitmap, (int) (screenY - yBitmap));

        collision();
    }

    public Rect getBounds() {
         return new Rect((int) x, (int) y, (int) x + bitmap.getWidth(), (int) y + bitmap.getHeight());
    }

    public void collision(){
        for(int i = 0; i < handler.objects.size(); i++){
            GameObject object = handler.objects.get(i);
            Rect rect = object.getBounds();

            if(object.getID() == ID.Enemy || object.getID() == ID.SmartEnemy){
                if(getBounds().intersects(rect.left, rect.top, rect.right, rect.bottom)){
                    if(HUD.HEALTH == 1) GameView.endGame = true;
                    HUD.HEALTH -= 2;
                }
            }
        }
    }

    private static Bitmap circleBitmap(Bitmap bitmap){
        Bitmap nBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(nBitmap);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);

        canvas.drawCircle(bitmap.getWidth() / 2f, bitmap.getHeight() / 2f, bitmap.getWidth() / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return nBitmap;
    }

    public Bitmap getBitmap(){ return bitmap;}
    public float getX(){ return x - xBitmap;}
    public float getY(){ return y - yBitmap;}
}
