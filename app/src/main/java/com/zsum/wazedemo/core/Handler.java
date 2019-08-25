package com.zsum.wazedemo.core;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Handler {

    public ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public void draw(Canvas canvas){
        for(int i = 0; i < objects.size(); i++){
            GameObject object = objects.get(i);

            object.draw(canvas);
        }
    }

    public void update(){
        for(int i = 0; i < objects.size(); i++){
            GameObject object = objects.get(i);

            object.update();
        }
    }

    public void addObject(GameObject object){
        this.objects.add(object);
    }

    public void removeObject(GameObject object){
        this.objects.remove(object);
    }
}
