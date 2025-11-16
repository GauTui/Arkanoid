package com.example.arkanoid.Controller.Managers;

import com.example.arkanoid.Application.Arkanoid;
import com.example.arkanoid.Model.Entities.Ball;
import com.example.arkanoid.Model.PowerUps.ExpandPaddlePowerUp;
import com.example.arkanoid.Model.PowerUps.PowerUp;
import java.net.MalformedURLException;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameManagerTest {
    GameManager gm = GameManager.getInstance();

    @BeforeAll
    static void initJavaFX() {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        this.gm.init(new Pane(), new Arkanoid(), 1);
    }

    @Test
    void testInit() {
        Assertions.assertEquals(0, this.gm.score, "ban đầu điểm phải bằng 0");
        Assertions.assertEquals(3, this.gm.lives, "ban đầu máu phải bằng 3");
        Assertions.assertNotNull(this.gm.getPaddle(), "phải khởi tạo được paddle rồi");
        Assertions.assertEquals(1, this.gm.getBalls().size(), "phải khởi tạo được 1 quả bóng rồi");
        Assertions.assertFalse(((Ball)this.gm.getBalls().getFirst()).isLaunched(), "ban đầu bóng chưa được phóng");
    }

    @Test
    void testDecreaseLives() throws MalformedURLException {
        Assertions.assertEquals(3, this.gm.lives, "Máu ban đầu bằng 3");
        this.gm.loseLife();
        this.gm.loseLife();
        Assertions.assertEquals(1, this.gm.lives, "Máu phải bằng 1");
    }

    @Test
    void testInitPowerup() {
        Assertions.assertEquals(0, this.gm.getFallingPowerups().size(), "ban đầu phải không có powerup rơi");
        Assertions.assertEquals(0, this.gm.getActivePowerups().size(), "ban đầu phải không có activePowerup");
    }

    @Test
    void testExpandPaddlePowerUp() throws MalformedURLException {
        PowerUp pE = new ExpandPaddlePowerUp(this.gm.getPaddle().getX(), this.gm.getPaddle().getY());
        this.gm.addFallingPowerups(pE);
        Assertions.assertEquals(1, this.gm.getFallingPowerups().size(), "phải có powerup đang rơi sau khi thêm");
        this.gm.update();
        Assertions.assertEquals(0, this.gm.getFallingPowerups().size(), "falling powerup chạm vào paddle rồi thì phải xóa ra khỏi danh sách");
        Assertions.assertEquals(1, this.gm.getActivePowerups().size(), "phải có activePowerup khi chạm vào paddle");
    }
}
