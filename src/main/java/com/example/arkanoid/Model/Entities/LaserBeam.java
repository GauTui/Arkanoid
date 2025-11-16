package com.example.arkanoid.Model.Entities;

import com.example.arkanoid.Model.GameObject;
import com.example.arkanoid.Controller.Utils.SoundEffect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LaserBeam extends MovableObject {
    private static SoundEffect laserSound;
    private static final int LASER_WIDTH = 5;      // Chiều rộng của tia laser
    private static final int LASER_HEIGHT = 20;    // Chiều cao của tia laser
    private static final int LASER_SPEED = 8;      // Tốc độ bay lên trên

    public LaserBeam(double x, double y) {
        super(x, y, LASER_WIDTH, LASER_HEIGHT, 0, -LASER_SPEED);
        laserSound = new SoundEffect("/com/example/arkanoid/sounds/gunshot.mp3");
        Rectangle laserView = new Rectangle(LASER_WIDTH, LASER_HEIGHT, Color.ORANGE);
        this.view = laserView;
        updateView();
    }

    // DÁN PHƯƠNG THỨC MỚI VÀO ĐÂY
    public boolean checkCollision(GameObject other) {
        if (this.view == null || other.getView() == null) {
            return false;
        }
        return this.view.getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    public static SoundEffect getLaserSound() {
        return laserSound;
    }

}