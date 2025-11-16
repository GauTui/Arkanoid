package com.example.arkanoid.Model.Entities;

import com.example.arkanoid.Model.GameObject;
import com.example.arkanoid.Model.Bricks.NormalBrick;
import com.example.arkanoid.Model.Bricks.StrongBrick;
import java.net.MalformedURLException;
import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BallTest {
    private Ball ball;
    private Paddle paddle;

    @BeforeAll
    static void initJavaFX() {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        this.ball = new Ball((double)350.0F, (double)500.0F, (double)0.0F, (double)0.0F);
        this.paddle = new Paddle((double)310.0F, (double)550.0F);
    }

    @Test
    void testImageBall() {
        Assertions.assertInstanceOf(Rectangle.class, this.ball.getView(), "View phải là kiểu Rectangle");
        Rectangle rect = (Rectangle)this.ball.getView();
        Assertions.assertNotNull(rect.getFill(), "Rectangle chứa nền khác null");
        Assertions.assertInstanceOf(ImagePattern.class, rect.getFill(), "Fill phải là 1 kiểu ImagePattern");
        ImagePattern pattern = (ImagePattern)rect.getFill();
        String url = pattern.getImage().getUrl();
        Assertions.assertEquals(Ball.NORMAL_BALL_PATTERN, pattern, "ảnh bóng ban đầu phải là bóng thường");
    }

    @Test
    void testIsLaunched1() {
        Assertions.assertFalse(this.ball.isLaunched(), "bóng chưa nên được phóng");
    }

    @Test
    void testIsLaunched2() {
        this.ball.launch();
        Assertions.assertTrue(this.ball.isLaunched(), "bóng nên được phóng sau khi gọi launch().");
    }

    @Test
    void testReset() {
        this.ball.setDx((double)5.0F);
        this.ball.setDy((double)-5.0F);
        this.ball.setBomb(true);
        this.ball.reset(this.paddle);
        Assertions.assertEquals((double)0.0F, this.ball.getDx(), "DX nên bằng 0 sau khi reset.");
        Assertions.assertEquals((double)0.0F, this.ball.getDy(), "DY nên bằng 0 sau khi reset.");
        Assertions.assertFalse(this.ball.isBomb(), "Ball nên về trạng thái bình thường sau khi reset.");
        double expectedX = this.paddle.getX() + (double)this.paddle.getWidth() / (double)2.0F - (double)this.ball.getWidth() / (double)2.0F;
        double expectedY = this.paddle.getY() - (double)this.ball.getHeight();
        Assertions.assertEquals(expectedX, this.ball.getX(), 0.001, "Ball X should be centered above the paddle.");
        Assertions.assertEquals(expectedY, this.ball.getY(), 0.001, "Ball Y should be just above the paddle.");
    }

    @Test
    void testCheckCollision1() {
        GameObject intersectingObject = new StrongBrick(this.ball.getX() + (double)1.0F, this.ball.getY() + (double)1.0F, 10, 10);
        Assertions.assertTrue(this.ball.checkCollision(intersectingObject), "Nên phát hiện va chạm khi giao nhau.");
        GameObject nonIntersectingObject = new NormalBrick(this.ball.getX() + (double)this.ball.getWidth() + (double)1.0F, this.ball.getY(), 10, 10);
        Assertions.assertFalse(this.ball.checkCollision(nonIntersectingObject), "Không nên phát hiện va chạm.");
    }

    @Test
    void testCheckCollision2() {
        Assertions.assertFalse(this.ball.checkCollision((GameObject)null), "Kết quả phải false khi va chạm với đối tượng null.");
    }

    @Test
    void testCollideWithWall() throws MalformedURLException {
        this.ball.setDx((double)-5.0F);
        this.ball.setDy((double)-5.0F);
        this.ball.setX((double)-1.0F);
        this.ball.collideWithWall();
        Assertions.assertEquals((double)5.0F, this.ball.getDx(), "DX nên bị đảo ngược sau khi va chạm với tường.");
        Assertions.assertEquals((double)0.0F, this.ball.getX(), "X  nên reset về 0 sau khi va chạm với tường, để bóng không bị kẹt.");
        this.ball.setY((double)-1.0F);
        this.ball.collideWithWall();
        Assertions.assertEquals((double)5.0F, this.ball.getDy(), "DY nên bị đảo ngược sau khi va chạm với tường.");
        Assertions.assertEquals((double)0.0F, this.ball.getY(), "Y nên reset về 0 sau khi va chạm với tường, để bóng không bị kẹt.");
    }
}
