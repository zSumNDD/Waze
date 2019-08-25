package com.zsum.wazedemo.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;

import com.zsum.wazedemo.views.GameView;

public class GameActivity extends AppCompatActivity {

    protected GameView gameView;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Khung hình
        Display display = getWindowManager().getDefaultDisplay();
        //Độ phân giải màn hình
        Point size = new Point();
        display.getRealSize(size);

        gameView = new GameView(this, size.x, size.y);

        setContentView(gameView);
    }

    public void fullScreen(){
        if(Build.VERSION.SDK_INT > 17 && Build.VERSION.SDK_INT < 19){
            View view = this.getWindow().getDecorView();
            view.setSystemUiVisibility(View.GONE);
        }else if(Build.VERSION.SDK_INT >= 19){
            View decorView = this.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        fullScreen();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
