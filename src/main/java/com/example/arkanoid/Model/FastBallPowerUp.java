package com.example.arkanoid.Model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.arkanoid.GameManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** Tăng tốc bóng */
public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.3; // hệ số tăng tốc

    // Lưu vận tốc gốc để hoàn tác: Ball -> {dx, dy}
    private final Map<Ball, double[]> originalSpeeds = new HashMap<>();

    public FastBallPowerUp(double x, double y) {
        super(x, y); // dy = POWERUP_GRAVITY

        // Đặt ảnh powerup
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

        originalSpeeds.clear();
        for (Ball b : balls) {
            double dx0 = b.getDx();
            double dy0 = b.getDy();
            originalSpeeds.put(b, new double[]{dx0, dy0});

            b.setDx(dx0 * SPEED_MULTIPLIER);
            b.setDy(dy0 * SPEED_MULTIPLIER);
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