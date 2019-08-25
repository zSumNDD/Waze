package com.zsum.wazedemo.objects;

import java.util.Random;

public class Star {
    //**
    // Tọa độ
    // **//
    private int x;
    private int y;
    private int speed;
    //**
    // Tọa độ khung hình
    // **//
    private int maxX;
    private int maxY;

    //**
    // Khởi tạo
    // **//
    public Star(int screenX, int screenY){
        maxX = screenX;
        maxY = screenY;
        Random generator = new Random();
        speed = generator.nextInt(10);

        //Tạo ra ngẫu nhiên nhưng vẫn trong màn hình
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(){
        //Tạo star bên phải bằng tốc độ người chơi
        Random random = new Random();
        x -= 10;
        x -= speed;
        if(x < 0){
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(15);
        }
    }

    //**
    // Tạo tọa độ ngẫu nhiên
    // **//
    public float getStarWidth(){
        float minX = 1.0f;
        float maxX = 4.0f;
        Random random = new Random();
        float finalX = random.nextFloat() * (maxX - minX) + minX;

        return finalX;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
