package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class PowerUp extends MovableObject {

    // ====== Thuộc tính chung ======
    private boolean isActive;
    private long activationTime;  // Thời điểm bắt đầu kích hoạt

    private long duration = 3000;    // Thời gian hiệu lực (ms)

    public static final int POWERUP_WIDTH = 20;
    public static final int POWERUP_HEIGHT = 20;
    public static final double POWERUP_GRAVITY = 1; // tốc độ rơi xuống (pixels/frame)

    // ====== Getter & Setter ======
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(long activationTime) {
        this.activationTime = activationTime;
    }

    public long getDuration() {
        return duration;
    }
    // ====== Constructor ======
    public PowerUp(double x, double y) {
        this(x, y, 0, POWERUP_GRAVITY); // rơi thẳng xuống
    }

    public PowerUp(double x, double y, double dx, double dy) {
        super(x, y, POWERUP_WIDTH, POWERUP_HEIGHT, dx, dy);
        // hình chữ nhật vàng là hiển thị của powerup
        Rectangle rect = new Rectangle(POWERUP_WIDTH, POWERUP_HEIGHT, Color.GOLD);
        this.view = rect;
        updateView();
    }

    // ====== Cập nhật vị trí mỗi frame ======
    @Override
    public void update() {
        // MovableObject.update(): x += dx; y += dy; updateView();
        super.update();
    }

    // ====== Hai phương thức trừu tượng (các class con sẽ định nghĩa sau) ======
    public abstract void applyEffect(GameManager gm);   // Khi được nhặt
    public abstract void removeEffect(GameManager gm);  // Khi hết hiệu lực
}
