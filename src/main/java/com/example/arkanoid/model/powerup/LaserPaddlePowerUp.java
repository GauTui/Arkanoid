package com.example.arkanoid.model.powerup;

import com.example.arkanoid.core.GameManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Đây là class định nghĩa Power-up "Paddle Bắn Laser".
 * Khi được thu thập, nó cho phép paddle tự động bắn laser trong một khoảng thời gian.
 */
public class LaserPaddlePowerUp extends PowerUp {

    // Thời gian hiệu lực của power-up (7000 milliseconds = 7 giây)
    private static final long DURATION_MS = 7000;

    /**
     * Constructor để tạo đối tượng power-up tại một vị trí cụ thể.
     * @param x Tọa độ X ban đầu.
     * @param y Tọa độ Y ban đầu.
     */
    public LaserPaddlePowerUp(double x, double y) {
        // Gọi constructor của lớp cha
        super(x, y);

        // --- Thiết lập ngoại hình cho power-up ---
        Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/LaserPowerUp.png").toURI().toString());
        this.view = new ImageView(image);
        ((ImageView) this.view).setFitWidth(POWERUP_WIDTH);
        ((ImageView) this.view).setFitHeight(POWERUP_WIDTH);

        updateView();
        /*Rectangle powerUpShape = new Rectangle(POWERUP_WIDTH, POWERUP_WIDTH, Color.YELLOW);
        powerUpShape.setStroke(Color.BLACK);
        powerUpShape.setStrokeWidth(2);
        this.view = powerUpShape;
        Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/LaserPowerUp.png").toURI().toString());
        ((Rectangle) this.view).setFill(new ImagePattern(image));
        */
    }

    /**
     * Phương thức này được gọi khi paddle thu thập được power-up.
     * Nó sẽ "bật công tắc" bắn laser trên paddle.
     * @param gm Instance của GameManager.
     */
    @Override
    public void applyEffect(GameManager gm) {
        // Báo cho paddle biết nó có khả năng bắn laser
        gm.getPaddle().setHasLaser(true);
    }

    /**
     * Phương thức này được gọi khi power-up hết thời gian hiệu lực.
     * Nó sẽ "tắt công tắc" bắn laser trên paddle.
     * @param gm Instance của GameManager.
     */
    @Override
    public void removeEffect(GameManager gm) {
        // Tắt khả năng bắn laser của paddle
        gm.getPaddle().setHasLaser(false);
    }

    /**
     * Trả về thời gian hiệu lực của power-up (tính bằng mili giây).
     * GameManager sẽ sử dụng thông số này để biết khi nào cần gọi removeEffect().
     * @return Thời gian hiệu lực.
     */
    @Override
    public long getDuration() {
        return DURATION_MS;
    }
}