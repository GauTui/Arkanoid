package com.example.arkanoid.Model.Entities;

import com.example.arkanoid.Controller.Managers.GameManager;
import com.example.arkanoid.Model.PowerUps.LaserPaddlePowerUp;
import com.example.arkanoid.Model.PowerUps.PowerUp;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaddleTest {
    private Paddle paddle;

    @BeforeAll
    static void initJavaFX() {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        this.paddle = new Paddle((double)100.0F, (double)500.0F);
        GameManager gm = GameManager.getInstance();
        gm.setPaddle(this.paddle);
    }

    @Test
    void testMovement() {
        this.paddle.moveRight();
        Assertions.assertEquals((double)8.0F, this.paddle.getDx(), "Paddle nên di chuyển phải với vận tốc 8.");
        this.paddle.stopMove();
        Assertions.assertEquals((double)0.0F, this.paddle.getDx(), "Paddle nên dừng di chuyển.");
        this.paddle.moveLeft();
        Assertions.assertEquals((double)-8.0F, this.paddle.getDx(), "Paddle nên di chuyển trái với vận tốc -8.");
    }

    @Test
    void testScreenBounds() {
        this.paddle.setX((double)-10.0F);
        this.paddle.update();
        Assertions.assertEquals((double)0.0F, this.paddle.getX(), "Paddle phải trả về giá trị x = 0 để không kẹt vào bên trái.");
        double maxRightX = (double)620.0F;
        this.paddle.setX((double)730.0F);
        this.paddle.update();
        Assertions.assertEquals(maxRightX, this.paddle.getX(), 0.001, "Paddle phải đặt ở mép phải màn hình.");
    }

    @Test
    void testSetHasLaser() {
        Assertions.assertFalse(this.paddle.getHasLaser(), "Paddle ban đầu chưa có laser powerup.");
        this.paddle.setHasLaser(true);
        Assertions.assertTrue(this.paddle.getHasLaser(), "Paddle phải có laser.");
        this.paddle.setHasLaser(false);
        Assertions.assertFalse(this.paddle.getHasLaser(), "Paddle không còn laser sau hết hiệu lực.");
    }

    @Test
    void testApplyPowerUp() {
        PowerUp laserPowerUp = new LaserPaddlePowerUp((double)0.0F, (double)0.0F);
        this.paddle.applyPowerUp(laserPowerUp);
        Assertions.assertEquals(laserPowerUp, this.paddle.getCurrentPowerUp(), "PowerUp phải áp đặt hiệu ứng lên paddle.");
        Assertions.assertTrue(this.paddle.getHasLaser(), "Paddle phải có hiệu ứng laser.");
    }
}
