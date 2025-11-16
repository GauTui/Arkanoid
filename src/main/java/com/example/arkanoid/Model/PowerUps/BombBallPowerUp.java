package com.example.arkanoid.Model.PowerUps;

import com.example.arkanoid.Controller.Managers.GameManager;
import com.example.arkanoid.Model.Entities.Ball;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class BombBallPowerUp extends PowerUp {

    public BombBallPowerUp(double x, double y) {
        super(x, y);


        Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/BombBallPowerUp.png").toURI().toString());
        this.view = new ImageView(image);
        ((ImageView) this.view).setFitWidth(POWERUP_WIDTH);
        ((ImageView) this.view).setFitHeight(POWERUP_WIDTH);
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