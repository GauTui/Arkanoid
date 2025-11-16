package com.example.arkanoid;

import com.example.arkanoid.Model.Paddle;
import com.example.arkanoid.Utils.HoverEffect;
import com.example.arkanoid.Utils.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class StartLevel extends Arkanoid {
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static final double PADDLE_SPEED = 600.0;
    public static double mouseX;
    private Pane pausePane = null;

    public void startLevel(Stage stage, int LevelNumber, Arkanoid mainApp) {
        Pane gamePane = new Pane();   // tao pane nay de luu scene cho game chay
        Pane gameHUD = new Pane();      // tao pane nay de luu cai scene hien phan thong tin cho nguoi choi xem
        StackPane root = new StackPane();       // tao stackpane de luu 2 cai pane nay de no hien thi ra
        GameManager gm = GameManager.getInstance();

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
        gamePane.getChildren().addAll(loadBGImgV);
        gm.init(gamePane, mainApp, LevelNumber);

        Scene scene = new Scene(root, 1080, GameManager.SCREEN_HEIGHT);
        loadBGImgV.setPreserveRatio(false);

        File loadHUD = new File("src/main/resources/com/example/arkanoid/images/HUDgame.png");
        Image HUDImg = new Image(loadHUD.toURI().toString());
        ImageView HUDView = new ImageView(HUDImg);
        HUDView.setFitHeight(720);
        HUDView.setFitWidth(360);
        HUDView.setLayoutY(0);
        HUDView.setLayoutX(720);

        gameHUD.getChildren().add(HUDView);

        root.getChildren().addAll(gameHUD,gamePane);
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
                    //gm.hud.render();
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

                        if (pausePane == null) { // tránh tạo nhiều pane
                            pausePane = new Pane();

                            // Resume Button
                            File loadResume = new File("src/main/resources/com/example/arkanoid/images/ResumeButton.png");
                            ImageView ResumeImgV = new ImageView(new Image(loadResume.toURI().toString()));
                            ResumeImgV.setFitWidth(230); ResumeImgV.setFitHeight(80);
                            ResumeImgV.setLayoutX(245); ResumeImgV.setLayoutY(250);
                            HoverEffect.addHoverEffect(ResumeImgV);
                            ResumeImgV.setOnMouseClicked(e2 -> {
                                gm.launchBall();
                                gamePane.getChildren().remove(pausePane); // remove đúng pane
                                SoundManager.resumeMusic();
                                gameLoop.start();
                                pausePane = null; // reset reference
                            });

                            // Restart Button
                            File loadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png");
                            ImageView RestartImgV = new ImageView(new Image(loadRestart.toURI().toString()));
                            RestartImgV.setFitWidth(230); RestartImgV.setFitHeight(80);
                            RestartImgV.setLayoutX(245); RestartImgV.setLayoutY(350);
                            HoverEffect.addHoverEffect(RestartImgV);
                            RestartImgV.setOnMouseClicked(e2 -> {
                                try {
                                    String bgmFile = SoundManager.getCurrentBgmFile();
                                    GameManager kk = GameManager.getInstance();
                                    kk.reset();
                                    Arkanoid.closeAllStages();
                                    Stage newStage = new Stage();
                                    SoundManager.playBackgroundMusic(bgmFile);
                                    new StartLevel().startLevel(newStage, LevelNumber, mainApp);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });

                            // Menu Button
                            File loadMenu = new File("src/main/resources/com/example/arkanoid/images/MenuButton.png");
                            ImageView MenuImgV = new ImageView(new Image(loadMenu.toURI().toString()));
                            MenuImgV.setFitWidth(230); MenuImgV.setFitHeight(80);
                            MenuImgV.setLayoutX(245); MenuImgV.setLayoutY(450);
                            HoverEffect.addHoverEffect(MenuImgV);
                            MenuImgV.setOnMouseClicked(e2 -> {
                                try {
                                    Arkanoid.closeAllStages();
                                    GameManager.getInstance().reset();
                                    SoundManager.stopBackgroundMusic();
                                    Stage newStage = new Stage();
                                    mainApp.start(newStage);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });

                            pausePane.getChildren().addAll(ResumeImgV, RestartImgV, MenuImgV);
                            gamePane.getChildren().add(pausePane);
                            SoundManager.pauseMusic();
                        }
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
