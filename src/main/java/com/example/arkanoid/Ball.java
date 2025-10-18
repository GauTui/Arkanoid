package com.example.arkanoid;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Ball extends MovableObject{
    public static final int BALL_SIZE = 20;
    public static final double BALL_DX = 3;
    public static final double BALL_DY = 3;

    public Ball(double x, double y, double dx, double dy) {
        super(x, y, BALL_SIZE, BALL_SIZE, dx, dy);

        //khởi tạo hình chữ nhật chứa ball
        Rectangle Rball = new Rectangle(BALL_SIZE, BALL_SIZE);

        Rball.setTranslateX(x);
        Rball.setTranslateY(y);

        Rball.setFill(Color.RED);
        //Node view khoi tao la Rball
        this.view = Rball;
    }

    public boolean checkCollision(GameObject other) {
        if (other == null || other.getView() == null) return false;
        // Lấy ranh giới hiển thị thực tế của bóng
        Bounds ballBounds = this.view.getBoundsInParent();

        if (other instanceof Brick) {
            if (ballBounds.intersects(other.getView().getBoundsInParent())) {
                return true;
            }
        }

        //kiem tra va cham voi paddle
        if (other instanceof Paddle) {
            if (ballBounds.intersects(other.getView().getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }

    public void collideWithWall() {
        if(this.getX() < 0) {

        }
    }

    public void collideWithPaddle(Paddle paddle) {

    }

    public void collideWithBrick(Brick brick) {

    }

    public void updateView() {
        this.view.setTranslateX(this.getX());
        this.view.setTranslateY(this.getY());
    }

    public void reset(Paddle paddle) {
        this.setX(paddle.getX() + paddle.getWidth()/2 - this.getWidth()/2);
        this.setY(paddle.getY() - this.getHeight);
    }


}
