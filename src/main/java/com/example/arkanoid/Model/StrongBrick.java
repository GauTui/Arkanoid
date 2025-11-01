package com.example.arkanoid.Model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class StrongBrick extends Brick{
    public static final int HIT_POINT_STRONG_BRICK = 3;

    public StrongBrick(double x, double y, int width, int height) {
        super(x,y,width,height,HIT_POINT_STRONG_BRICK);
        //sửa màu
        Image StrongBrickIMG = new Image(getClass().getResourceAsStream(""));

        ((Rectangle) this.view).setFill(new ImagePattern(StrongBrickIMG));
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
