package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BombBallPowerUp extends PowerUp {

    public BombBallPowerUp(double x, double y) {
        super(x, y);

        // --- CHÚ THÍCH THÊM ẢNH SAU ---
        // Hiện tại, nó là một hình vuông màu Cam.
        // Sau này, bạn có thể thay thế 4 dòng code tạo hình vuông này
        // bằng code tải ảnh quả bom của bạn, giống như trong ExtraLifePowerUp.
        // Ví dụ:
        // Image image = new Image(new File(".../bomb_powerup.png").toURI().toString());
        // this.view = new ImageView(image);
        int size = 40;
        Rectangle powerUpShape = new Rectangle(size, size, Color.BLACK);
        powerUpShape.setStroke(Color.BLACK);
        powerUpShape.setStrokeWidth(2);
        this.view = powerUpShape;

        this.width = size;
        this.height = size;
        updateView();
    }

    /**
     * Phương thức này được gọi khi paddle thu thập được power-up.
     * Nó sẽ biến tất cả các quả bóng trên màn hình thành bom.
     * @param gm Instance của GameManager.
     */
    @Override
    public void applyEffect(GameManager gm) {
        for (Ball ball : gm.getBalls()) {
            ball.setBomb(true);
        }
    }

    /**
     * Vì hiệu ứng này được bật/tắt bởi người chơi và quả bóng,
     * nên không cần logic xóa hiệu ứng dựa trên thời gian.
     */
    @Override
    public void removeEffect(GameManager gm) {
        // Để trống.
    }

    /**
     * Power-up này không có thời gian hiệu lực.
     * @return 0
     */
    @Override
    public long getDuration() {
        return 0;
    }
}