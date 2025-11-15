package com.example.arkanoid.model;

import com.example.arkanoid.core.GameManager;
import com.example.arkanoid.model.powerup.PowerUp;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

import static com.example.arkanoid.core.GameManager.SCREEN_HEIGHT;
import static com.example.arkanoid.core.GameManager.SCREEN_WIDTH;

public class Paddle extends MovableObject{

    private PowerUp currentPowerUp;
    private int initialWidth;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 14;
    private static final double PADDLE_SPEED = 8.0;
    public static final Color PADDLE_COLOR = Color.AQUAMARINE;

    private boolean hasLaser = false;

    /**
     * Bật hoặc tắt trạng thái bắn laser của paddle.
     * Đồng thời thay đổi màu sắc để người chơi nhận biết.
     * @param hasLaser true để bật, false để tắt.
     */
    public void setHasLaser(boolean hasLaser) {
        this.hasLaser = hasLaser;

        // Đảm bảo rằng 'view' của paddle là một Rectangle để có thể đổi màu
        if (this.view instanceof javafx.scene.shape.Rectangle) {
            if (hasLaser) {
                // Khi có laser, đổi paddle sang màu vàng
                ((javafx.scene.shape.Rectangle) this.view).setFill(javafx.scene.paint.Color.YELLOW);
            } else {
                // Khi hết laser, trả về màu gốc.
                // HÃY THAY THẾ 'Color.CYAN' BẰNG MÀU GỐC CỦA PADDLE BẠN.
                ((javafx.scene.shape.Rectangle) this.view).setFill(javafx.scene.paint.Color.CYAN);
            }
        }
    }

    public boolean getHasLaser() {
        return this.hasLaser;
    }


    public Paddle(double x, double y){
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT, 0, 0);
        this.initialWidth = PADDLE_WIDTH;
        this.currentPowerUp = null; // Ban đầu không có power-up nào

        // Khởi tạo giao diện
        Rectangle paddleShape = new Rectangle(width, height);
        // Đặt ảnh powerup
        try {
            Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/Paddle.png").toURI().toString());
            this.view = new ImageView(image);

            // Ép kiểu để đặt kích thước cho ảnh
            ((ImageView) this.view).setFitWidth(PADDLE_WIDTH);
            ((ImageView) this.view).setFitHeight(PADDLE_HEIGHT);
        } catch (Exception e) {
            paddleShape.setFill(PADDLE_COLOR);
            this.view = paddleShape;
        }
        updateView();
    }
    public PowerUp getCurrentPowerUp(){
        return currentPowerUp;
    }

    public void setCurrentPowerUp(PowerUp currentPowerup){
        this.currentPowerUp = currentPowerup;
    }
    public void applyPowerUp(PowerUp p) {
        GameManager gm = GameManager.getInstance();
        // luu powerup moi
        this.currentPowerUp = p;
        this.currentPowerUp.applyEffect(gm);
    }
    public int getInitialWidth(){
        return initialWidth;
    }
    public void setInitialWidth(int initialWidth){
        this.initialWidth = initialWidth;
        this.updateView();
    }
    public void setWidth(int width){
        super.setWidth(width); // Cập nhật thuộc tính width của GameObject
        if (view instanceof Rectangle) {
            ((Rectangle) view).setWidth(width); // Cập nhật chiều rộng của hình chữ nhật
        }
    }

    public void moveLeft(){
        setDx(-PADDLE_SPEED);
    }
    public void moveRight(){
        setDx(PADDLE_SPEED);
    }

    public void stopMove(){
        setDx(0);
    }

    //đưa paddle về trạng thái ban đầu
    public void update() {
        super.update();

        // Giới hạn paddle không đi ra khỏi màn hình
        if (getX() < 0) {
            setX(0);
        }
        if (getX() + getWidth() > SCREEN_WIDTH) {
            setX(SCREEN_WIDTH - getWidth());
        }
        updateView();
    }
    public void reset(){
        setX(SCREEN_WIDTH/2.0 - PADDLE_WIDTH/2.0);
        setY(SCREEN_HEIGHT - PADDLE_HEIGHT - 15.0);

        // Đặt lại kích thước và dừng di chuyển
        setWidth(this.initialWidth);
        stopMove();

        GameManager gm = GameManager.getInstance();
        // gỡ powerup
        if (currentPowerUp != null) {
            currentPowerUp.removeEffect(gm); //bao gio co gamemanager thi dung lenh nay
            currentPowerUp = null;
        }
        updateView();
    }
}
