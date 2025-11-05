package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; // Import thêm ImageView

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.arkanoid.Model.Ball.BALL_SIZE;

/**
 * Power-up này có chức năng chia mỗi quả bóng hiện có thành ba.
 * Một quả bóng giữ nguyên hướng, và hai quả bóng mới được tạo ra bay theo góc lệch.
 */
public class SplitBallPowerUp extends PowerUp {

    // Hằng số góc lệch cho các quả bóng mới (tính bằng radian)
    private static final double SPLIT_ANGLE = Math.PI / 6; // 30 độ

    public SplitBallPowerUp(double x, double y) {

        super(x, y);

        try {
            Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/SplitBallPowerUp.png").toURI().toString());
            this.view = new ImageView(image); // Gán ImageView mới cho view


            ((ImageView) this.view).setFitWidth(POWERUP_WIDTH);
            ((ImageView) this.view).setFitHeight(POWERUP_WIDTH);

            updateView(); // Gọi phương thức để cập nhật vị trí của view
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh cho SplitBallPowerUp: " + e.getMessage());
        }
    }


    @Override
    public void applyEffect(GameManager gm) {
        List<Ball> currentBalls = new ArrayList<>(gm.getBalls());
        List<Ball> newBalls = new ArrayList<>();

        for (Ball original : currentBalls) {
            if (!original.isVisible()) continue;

            double dx = original.getDx();
            double dy = original.getDy();

            // Tính tốc độ mới theo góc lệch
            double speed = Math.sqrt(dx * dx + dy * dy);
            double baseAngle = Math.atan2(dy, dx);

            // Bóng 1: lệch +30 độ
            double angle1 = baseAngle + SPLIT_ANGLE;
            Ball ball1 = createSplitBall(original, angle1, speed);
            newBalls.add(ball1);

            // Bóng 2: lệch -30 độ
            double angle2 = baseAngle - SPLIT_ANGLE;
            Ball ball2 = createSplitBall(original, angle2, speed);
            newBalls.add(ball2);
        }

        // Thêm bóng mới vào game
        for (Ball ball : newBalls) {
            if( gm.getBalls().size() < GameManager.MAX_BALLS ) {// Giới hạn số lượng bóng tối đa
                return;
            }
            gm.addBall(ball);
        }
    }

    private Ball createSplitBall(Ball original, double angle, double speed) {
        Ball newBall = new Ball(
                original.getX() + original.getWidth() / 2 - BALL_SIZE / 2,
                original.getY() + original.getHeight() / 2 - BALL_SIZE / 2,
                BALL_SIZE, BALL_SIZE
        );
        newBall.setDx(speed * Math.cos(angle));
        newBall.setDy(speed * Math.sin(angle));
        return newBall;
    }

    @Override
    public void removeEffect(GameManager gm) {
        // Hiệu ứng tức thì → không cần xóa
    }

    @Override
    public long getDuration() {
        return 0; // Tức thì
    }
}