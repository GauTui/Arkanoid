package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Đây là class định nghĩa Power-up "Paddle Bắn Laser".
 * Khi được thu thập, nó cho phép paddle tự động bắn laser trong một khoảng thời gian.
 */
public class LaserPaddlePowerUp extends PowerUp {

    // Thời gian hiệu lực của power-up (10000 milliseconds = 10 giây)
    private static final long DURATION_MS = 10000;

    /**
     * Constructor để tạo đối tượng power-up tại một vị trí cụ thể.
     * @param x Tọa độ X ban đầu.
     * @param y Tọa độ Y ban đầu.
     */
    public LaserPaddlePowerUp(double x, double y) {
        // Gọi constructor của lớp cha
        super(x, y);

        // --- Thiết lập ngoại hình cho power-up ---
        // Tạo một hình vuông màu vàng để người chơi dễ nhận biết
        int size = 40;
        Rectangle powerUpShape = new Rectangle(size, size, Color.YELLOW);
        powerUpShape.setStroke(Color.WHITE);
        powerUpShape.setStrokeWidth(2);

        // Gán hình vuông này làm "view" (hình ảnh đại diện)
        this.view = powerUpShape;
        this.width = size;
        this.height = size;

        // Cập nhật vị trí hiển thị ban đầu
        updateView();
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