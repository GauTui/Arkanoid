package Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Tăng tốc bóng */
public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.3; // hệ số tăng tốc

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
        if (isActive) return;
        isActive = true;

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
        if (!isActive) return;
        isActive = false;

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

