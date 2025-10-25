package com.example.arkanoid.Model.Brick;

import com.example.arkanoid.Model.GameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends GameObject {
    protected int hitPoints;
    protected boolean isDestroyed = false;

    public static final int BRICK_WIDTH = 60;
    public static final int BRICK_HEIGHT = 60;

    public Brick(double x, double y, int width, int height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        Rectangle rBrick= new Rectangle(x, y, width, height);
        //đặt màu mặc định
        rBrick.setFill(Color.GREEN);
        this.view = rBrick;
    }
    public void takeHit() {
        hitPoints--;
        if(hitPoints <= 0) {
            isDestroyed = true;
        }
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
