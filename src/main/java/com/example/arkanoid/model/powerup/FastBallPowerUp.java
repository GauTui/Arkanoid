package com.example.arkanoid.model.powerup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.arkanoid.core.GameManager;
import com.example.arkanoid.model.Ball;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** Tăng tốc bóng */
public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.3; // hệ số tăng tốc
    private static final double MAX_SPEED = 9.5; // GIỚI HẠN TỐC ĐỘ TỐI ĐA

    // Lưu vận tốc gốc để hoàn tác: Ball -> {dx, dy}
    private final Map<Ball, Double> originalSpeedMagnitudes = new HashMap<>();

    public FastBallPowerUp(double x, double y) {
        super(x, y); // dy = POWERUP_GRAVITY
        Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/FastBallPowerUp.png").toURI().toString());
        this.view = new ImageView(image);

        // Ép kiểu để đặt kích thước cho ảnh
        ((ImageView) this.view).setFitWidth(POWERUP_WIDTH);
        ((ImageView) this.view).setFitHeight(POWERUP_WIDTH);
        updateView();
    }

    @Override
    public void applyEffect(GameManager gm) {
        if (isActive()) return;
        setActive(true);

        // cần GameManager.getBalls()
        List<Ball> balls = gm.getBalls();

        originalSpeedMagnitudes.clear();

        for (Ball b : balls) {
            double dx = b.getDx();
            double dy = b.getDy();

            // Lưu tốc độ gốc (magnitude)
            double originalSpeed = Math.sqrt(dx * dx + dy * dy);
            originalSpeedMagnitudes.put(b, originalSpeed);

            // Tăng tốc độ
            double newDx = dx * SPEED_MULTIPLIER;
            double newDy = dy * SPEED_MULTIPLIER;

            double newSpeed = Math.sqrt(newDx * newDx + newDy * newDy);

            // Nếu vượt quá MAX_SPEED, scale lại
            if (newSpeed > MAX_SPEED) {
                double scale = MAX_SPEED / newSpeed;
                newDx *= scale;
                newDy *= scale;
            }

            b.setDx(newDx);
            b.setDy(newDy);
        }
        System.out.println("FastBallPowerUp effect applied.");
    }

    @Override
    public void removeEffect(GameManager gm) {
        if (!isActive()) return;
        setActive(false);

        // GIỮ NGUYÊN HƯỚNG, CHỈ GIẢM TỐC ĐỘ
        for (Map.Entry<Ball, Double> e : originalSpeedMagnitudes.entrySet()) {
            Ball b = e.getKey();
            double originalSpeed = e.getValue();

            // Lấy hướng HIỆN TẠI
            double currentDx = b.getDx();
            double currentDy = b.getDy();
            double currentSpeed = Math.sqrt(currentDx * currentDx + currentDy * currentDy);

            if (currentSpeed > 0) {
                // Giữ nguyên hướng, scale về tốc độ gốc
                double scale = originalSpeed / currentSpeed;
                b.setDx(currentDx * scale);
                b.setDy(currentDy * scale);
            }
        }
        originalSpeedMagnitudes.clear();
        System.out.println("FastBallPowerUp effect removed.");
    }
}