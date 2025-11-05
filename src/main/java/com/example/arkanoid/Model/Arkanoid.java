// java
package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Arkanoid extends Application {
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private static final double PADDLE_SPEED = 600.0; // pixels per second

    @Override
    public void start(Stage stage) {
        Pane gamePane = new Pane();
        GameManager gm = GameManager.getInstance();
        gm.init(gamePane, this, 1);

        Scene scene = new Scene(gamePane, GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT);

        // Key handlers for paddle movement and launching the ball
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT, A -> leftPressed = true;
                case RIGHT, D -> rightPressed = true;
                case SPACE -> gm.launchBall();
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT, A -> leftPressed = false;
                case RIGHT, D -> rightPressed = false;
            }
        });

        // Ensure the pane has focus so it receives key events
        gamePane.setOnMouseClicked(e -> gamePane.requestFocus());

        // Game loop with delta-time paddle movement
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double elapsedSeconds = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                // Update paddle position using keyboard input
                Paddle paddle = gm.getPaddle();
                if (paddle != null) {
                    double dx = 0;
                    if (leftPressed) dx -= PADDLE_SPEED * elapsedSeconds;
                    if (rightPressed) dx += PADDLE_SPEED * elapsedSeconds;

                    double newX = Math.max(0, Math.min(GameManager.SCREEN_WIDTH - paddle.getWidth(),
                            paddle.getX() + dx));
                    paddle.setX(newX);
                    paddle.updateView();
                }

                // Update the rest of the game
                try {
                    gm.update();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        };

        stage.setScene(scene);
        stage.show();

        // Request focus so key events work immediately
        gamePane.requestFocus();

        gameLoop.start();
    }
}
