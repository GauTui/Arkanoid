package com.example.arkanoid.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class StrongBrick extends Brick{
    public static final int HIT_POINT_STRONG_BRICK = 3;

    public StrongBrick(double x, double y, int width, int height) {
        super(x,y,width,height,HIT_POINT_STRONG_BRICK);
        //sửa màu
        Image StrongBrickIMG = new Image(getClass().getResourceAsStream("/com/example/arkanoid/images/SStrongBrick.png"));
        ((ImageView) this.view).setImage(StrongBrickIMG);
    }

    @Override
    public void takeHit() {
        hitPoints--;
        // Đặt lại màu gạch cho phù hợp với số máu của nó
        if(hitPoints == 2) {
            Image StrongBrickIMG2 = new Image(getClass().getResourceAsStream("/com/example/arkanoid/images/StrongBrick.png"));
            ((ImageView) this.view).setImage(StrongBrickIMG2);
        } else if(hitPoints == 1) {
            Image StrongBrickIMG3 = new Image(getClass().getResourceAsStream("/com/example/arkanoid/images/BrickSapVo.png"));
            ((ImageView) this.view).setImage(StrongBrickIMG3);
        }
        else if(hitPoints <= 0 ) {
            isDestroyed = true;
        }
    }


}
