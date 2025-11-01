package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; // Import thêm ImageView

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Power-up này có chức năng chia mỗi quả bóng hiện có thành ba.
 * Một quả bóng giữ nguyên hướng, và hai quả bóng mới được tạo ra bay theo góc lệch.
 */
public class SplitBallPowerUp extends PowerUp {

    // Hằng số góc lệch cho các quả bóng mới (tính bằng radian)
    private static final double SPLIT_ANGLE = Math.PI / 6; // 30 độ

    // Hằng số kích thước cho các quả bóng mới
    private static final double NEW_BALL_SIZE = 15.0; // Bạn có thể chỉnh lại kích thước này cho phù hợp

    public SplitBallPowerUp(double x, double y) {

        super(x, y);

        try {
            Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/split_powerup.png").toURI().toString());
            this.view = new ImageView(image); // Gán ImageView mới cho view
            ((ImageView) this.view).setFitWidth(40);
            ((ImageView) this.view).setFitHeight(40);
            updateView(); // Gọi phương thức để cập nhật vị trí của view
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh cho SplitBallPowerUp: " + e.getMessage());
        }
    }

    /**
     * khi paddle thu thập được power-up.
     * Đây là nơi chứa logic chính của power-up.
     * @param gm Instance của GameManager để truy cập các thành phần trong game.
     */
    @Override
    public void applyEffect(GameManager gm) {
        List<Ball> newBalls = new ArrayList<>();

        for (Ball originalBall : new ArrayList<>(gm.getBalls())) {
            double originalDx = originalBall.getDx();
            double originalDy = originalBall.getDy();

            // --- Tạo quả bóng mới thứ nhất (lệch sang trái) ---
            double cosAngle1 = Math.cos(SPLIT_ANGLE);
            double sinAngle1 = Math.sin(SPLIT_ANGLE);
            double dx1 = originalDx * cosAngle1 - originalDy * sinAngle1;
            double dy1 = originalDx * sinAngle1 + originalDy * cosAngle1;

            Ball ball1 = new Ball(originalBall.getX(), originalBall.getY(), NEW_BALL_SIZE, NEW_BALL_SIZE);
            ball1.setDx(dx1);
            ball1.setDy(dy1);
            newBalls.add(ball1);

            // --- Tạo quả bóng mới thứ hai (lệch sang phải) ---
            double cosAngle2 = Math.cos(-SPLIT_ANGLE);
            double sinAngle2 = Math.sin(-SPLIT_ANGLE);
            double dx2 = originalDx * cosAngle2 - originalDy * sinAngle2;
            double dy2 = originalDx * sinAngle2 + originalDy * cosAngle2;

            Ball ball2 = new Ball(originalBall.getX(), originalBall.getY(), NEW_BALL_SIZE, NEW_BALL_SIZE);
            ball2.setDx(dx2);
            ball2.setDy(dy2);
            newBalls.add(ball2);
        }

        for (Ball ball : newBalls) {
            gm.addBall(ball);
        }
    }

    /**
     * LỖI ĐÃ SỬA: Bắt buộc phải override phương thức này từ lớp cha PowerUp.
     * Vì đây là hiệu ứng tức thời, chúng ta không cần làm gì khi nó "hết hạn".
     * @param gm Instance của GameManager.
     */
    @Override
    public void removeEffect(GameManager gm) {

    }
}