package com.example.arkanoid;

import com.example.arkanoid.Model.Paddle;
import com.example.arkanoid.Utils.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class StartLevel extends Arkanoid {
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static final double PADDLE_SPEED = 600.0;
    public static double mouseX;
    public void startLevel(Stage stage, int LevelNumber, Arkanoid mainApp) {
        Pane gamePane = new Pane();
        GameManager gm = GameManager.getInstance();
        File loadSide = new File("");
        Image SideImage = new Image(loadSide.toURI().toString());
        ImageView SideView = new ImageView(SideImage);
        SideView.setLayoutY(720);
        SideView.setLayoutX(0);
        SideView.setFitHeight(720);
        SideView.setFitWidth(200);

        File loadBackGroundImg;
        switch (LevelNumber) {
            case 1 -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level1.jpg");
            case 2 -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level2.png");
            case 3 -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level3.jpg");
            case 4 -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level4.jpg");
            case 5 -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level5.jpg");
            default -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level1.jpg");
        }
        Image loadBGImg = new Image(loadBackGroundImg.toURI().toString());
        ImageView loadBGImgV = new ImageView(loadBGImg);
        loadBGImgV.setFitWidth(720);
        loadBGImgV.setFitHeight(720);
        gamePane.getChildren().addAll(loadBGImgV, SideView);
        gm.init(gamePane, mainApp, LevelNumber);
        Scene scene = new Scene(gamePane, 920, GameManager.SCREEN_HEIGHT);
        loadBGImgV.setPreserveRatio(false);

        // Bắt tọa độ chuột
        scene.setOnMouseMoved(event -> {
            mouseX = event.getX();
        });
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
        gameLoop.start();

        gm.setGameLoop(gameLoop);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT, A -> leftPressed = true;
                case RIGHT, D -> rightPressed = true;
                // nút space để phóng bóng
                case SPACE -> gm.launchBall();
                case ESCAPE -> {
                    try {
                        gameLoop.stop(); // dừng game
                        Pane pausePane = PauseGame.PauseGame(stage, LevelNumber, mainApp); // tạo menu pause
                        gamePane.getChildren().add(pausePane); // chồng menu lên game
                        SoundManager.pauseMusic();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // thả phím di chuyển paddle
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT, A -> leftPressed = false;
                case RIGHT, D -> rightPressed = false;
            }
        });

        stage.setScene(scene);
        stage.show();
        Platform.runLater(() -> stage.centerOnScreen());
    }
}
