package com.example.arkanoid.Brick;

public class StrongBrick extends Brick{
    public StrongBrick(double x, double y, int width, int height, int hitPoints) {
        super(x,y,width,height,hitPoints);
    }

    @Override
    public void takeHit() {
        hitPoints--;
        if(hitPoints <=0 ) {
            isDestroyed = true;
            this.view = null;
        }
    }


}
