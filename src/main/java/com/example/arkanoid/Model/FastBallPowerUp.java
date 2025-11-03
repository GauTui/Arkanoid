package com.example.arkanoid.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.arkanoid.GameManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Tăng tốc bóng */
public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.3; // hệ số tăng tốc
    private static final double MAX_SPEED = 9.5; // GIỚI HẠN TỐC ĐỘ TỐI ĐA

    // Lưu vận tốc gốc để hoàn tác: Ball -> {dx, dy}
    private final Map<Ball, double[]> originalSpeeds = new HashMap<>();

    public FastBallPowerUp(double x, double y) {
        super(x, y); // dy = POWERUP_GRAVITY
        // skin riêng (màu đỏ để phân biệt)
        this.view = new Rectangle(POWERUP_WIDTH, POWERUP_HEIGHT, Color.ORANGERED);
        updateView();
    }

    @Override
    public void applyEffect(GameManager gm) {
        if (isActive()) return;
        setActive(true);

        // cần GameManager.getBalls()
        List<Ball> balls = gm.getBalls();

        originalSpeeds.clear();
        for (Ball b : balls) {
            double dx0 = b.getDx();
            double dy0 = b.getDy();
            originalSpeeds.put(b, new double[]{dx0, dy0});

            // Tăng tốc nhưng GIỚI HẠN tốc độ tối đa
            double newDx = dx0 * SPEED_MULTIPLIER;
            double newDy = dy0 * SPEED_MULTIPLIER;

            // Tính tốc độ tổng
            double speed = Math.sqrt(newDx * newDx + newDy * newDy);

            // Nếu vượt quá MAX_SPEED, scale lại
            if (speed > MAX_SPEED) {
                double scale = MAX_SPEED / speed;
                newDx *= scale;
                newDy *= scale;
            }

            b.setDx(newDx);
            b.setDy(newDy);
        }
    }

    @Override
    public void removeEffect(GameManager gm) {
        if (!isActive()) return;
        setActive(false);

        // phục hồi vận tốc cũ
        for (Map.Entry<Ball, double[]> e : originalSpeeds.entrySet()) {
            Ball b = e.getKey();
            double[] v = e.getValue();
            b.setDx(v[0]);
            b.setDy(v[1]);
        }
        originalSpeeds.clear();
    }
}

