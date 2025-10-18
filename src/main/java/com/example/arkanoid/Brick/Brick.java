package com.example.arkanoid.Brick;

public class Brick {
    protected int hitPoints;
    protected boolean isDestroyed = false;

    public static final int BRICK_WIDTH = 60;
    public static final int BRICK_HEIGHT = 60;

    public Brick(double x, double y, int width, int height, int hitPoints) {
        //super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.view = new Rectangle(x, y, width, height);
    }
    public void takeHit() {
        hitPoints--;
        if(hitPoints <= 0) {
            isDestroyed = true;
            this.view = null;
        }
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
