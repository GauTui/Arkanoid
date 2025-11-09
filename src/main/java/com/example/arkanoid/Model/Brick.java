package com.example.arkanoid.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Brick extends GameObject {
    protected int hitPoints;
    protected boolean isDestroyed = false;

    //public static final int BRICK_WIDTH = 60;
    //public static final int BRICK_HEIGHT = 60;

    public Brick(double x, double y, int width, int height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        /*
        Rectangle rBrick = new Rectangle(width, height);
        rBrick.setTranslateX(x);
        rBrick.setTranslateY(y);

        //đặt màu mặc định
        Image BrickImg = new Image(getClass().getResourceAsStream("/com/example/arkanoid/images/NormalBrick.png"));

        rBrick.setFill(new ImagePattern(BrickImg));
        rBrick.setStroke(Color.BLACK);        // Màu viền: Đen
        rBrick.setStrokeWidth(2.0);           // Độ dày viền: 2.0 pixels

         */

        Image BrickImg = new Image(getClass().getResourceAsStream("/com/example/arkanoid/images/NormalBrick.png"));
        ImageView BrickView = new ImageView(BrickImg);
        BrickView.setFitWidth(width);
        BrickView.setFitHeight(height);
        BrickView.setLayoutX(x);
        BrickView.setLayoutY(y);

        this.view = BrickView;
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

    public void destroyed() {
        this.isDestroyed = true;
        hitPoints = 0;
    }
}
