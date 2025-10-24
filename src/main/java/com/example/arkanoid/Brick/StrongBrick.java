package com.example.arkanoid.Brick;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StrongBrick extends Brick{
    public static final int HIT_POINT_STRONG_BRICK = 3;

    public StrongBrick(double x, double y, int width, int height) {
        super(x,y,width,height,HIT_POINT_STRONG_BRICK);
        //sửa màu
        ((Rectangle) this.view).setFill(Color.GRAY);
    }

    @Override
    public void takeHit() {
        hitPoints--;
        // Đặt lại màu gạch cho phù hợp với số máu của nó
        if(hitPoints == 2) {
            ((Rectangle) this.view).setFill(Color.YELLOW);
        } else if(hitPoints == 1) {
            ((Rectangle) this.view).setFill(Color.GREEN);
        }
        else if(hitPoints <= 0 ) {
            isDestroyed = true;
        }
    }


}
