package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.scene.paint.Color;      // SỬA: Thêm import cho Color
import javafx.scene.shape.Rectangle;  // SỬA: Thêm import cho Rectangle
import java.util.ArrayList;
import java.util.List;

/**
 * Power-up chia mỗi quả bóng thành 3 (1 gốc + 2 lệch góc)
 */
public class SplitBallPowerUp extends PowerUp {

    private static final double SPLIT_ANGLE = Math.PI / 6; // 30 độ
    private static final double BALL_SIZE = 20.0; // Đồng bộ với Ball.java


    public SplitBallPowerUp(double x, double y) {
        super(x, y);

        // --- BẮT ĐẦU THAY ĐỔI: TẠO HÌNH VUÔNG MÀU ĐỎ ---

        // Kích thước cạnh của hình vuông (kiểu int để tránh lỗi)
        int size = 40;

        // Tạo một đối tượng Rectangle mới
        Rectangle powerUpShape = new Rectangle(size, size);

        // Tô MÀU ĐỎ cho hình vuông
        powerUpShape.setFill(Color.BROWN);

        // (Tùy chọn) Thêm viền màu trắng cho nổi bật
        powerUpShape.setStroke(Color.WHITE);
        powerUpShape.setStrokeWidth(2);

        // Gán hình vuông này làm "view" (hình ảnh đại diện) của power-up
        this.view = powerUpShape;

        // Cập nhật lại chiều rộng và chiều cao của GameObject để va chạm chính xác
        this.width = size;
        this.height = size;

        // Gọi phương thức của lớp cha để đặt vị trí hiển thị của hình vuông
        updateView();
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