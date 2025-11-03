package com.example.arkanoid.Model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Brick extends GameObject {
    protected int hitPoints;
    protected boolean isDestroyed = false;

    public static final int BRICK_WIDTH = 60;
    public static final int BRICK_HEIGHT = 60;

    public Brick(double x, double y, int width, int height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;

        // FIX: Không set vị trí trong constructor của Rectangle, chỉ set kích thước
//        Rectangle rBrick= new Rectangle(x, y, width, height);
        Rectangle rBrick = new Rectangle(width, height);
        //đặt màu mặc định
        Image BrickImg = new Image(getClass().getResourceAsStream("/com/example/arkanoid/images/NormalBrick.png"));

        rBrick.setFill(new ImagePattern(BrickImg));
        rBrick.setStroke(Color.BLACK);        // Màu viền: Đen
        rBrick.setStrokeWidth(2.0);           // Độ dày viền: 2.0 pixels
        this.view = rBrick;

        // Set vị trí thông qua updateView() để nhất quán
        updateView();
    }
    public void takeHit() {
        hitPoints--;
        if(hitPoints <= 0) {
            isDestroyed = true;
            // FIX: Ẩn view ngay lập tức khi destroyed
            if (view != null) {
                view.setVisible(false);
            }
        }
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
