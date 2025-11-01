package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Arkanoid extends Application {
    private double mouseX;

    @Override
    public void start(Stage stage) {
        Pane gamePane = new Pane();
        GameManager gm = GameManager.getInstance();
        gm.init(gamePane, this, 1);

        Scene scene = new Scene(gamePane, GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT);

        // Bắt tọa độ chuột
        scene.setOnMouseMoved(event -> {
            mouseX = event.getX();
        });

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Cập nhật vị trí paddle theo chuột
                Paddle paddle = gm.getPaddle();
                double newX = Math.max(0, Math.min(GameManager.SCREEN_WIDTH - paddle.getWidth(),
                        mouseX - paddle.getWidth()/2.0));
                paddle.setX(newX);
                paddle.updateView();

                // Cập nhật toàn bộ game
                try {
                    gm.update();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        };
        gameLoop.start();

        stage.setScene(scene);
        stage.show();
    }
}