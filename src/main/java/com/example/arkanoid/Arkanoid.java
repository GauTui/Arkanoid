package com.example.arkanoid;

import com.example.arkanoid.Model.Paddle;
import com.example.arkanoid.Utils.AnimationGame;
import com.example.arkanoid.Utils.BackgroundMusic;
import com.example.arkanoid.Utils.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import javafx.util.Duration;

import java.awt.*;
import javafx.geometry.Rectangle2D;
import java.io.File;


import java.io.IOException;
import java.net.MalformedURLException;


/**
 * cái này là để chạy start game nhé ae!!!!!
 */

public class Arkanoid extends Application {
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private static final double PADDLE_SPEED = 600.0; // pixels per second

    public static void closeAllStages() {
        Platform.runLater(() -> {
            for (Window window : Stage.getWindows()) {
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            }
        });
    }
    private double mouseX;
    private void switchToMainMenu(Stage stage) {
        try {
            stage.getScene().setRoot(new Pane()); // clear nodes cũ
            start(stage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Pane PauseGame(Stage stage,int LevelNumber) throws Exception {
        Pane PauseGamePane = new Pane();

        File loadResume = new File("src/main/resources/com/example/arkanoid/images/ResumeButton.png");
        Image ResumeImg = new Image(loadResume.toURI().toString());
        ImageView ResumeImgV = new ImageView(ResumeImg);
        ResumeImgV.setFitHeight(80);
        ResumeImgV.setFitWidth(230);
        ResumeImgV.setPickOnBounds(false);
        ResumeImgV.setLayoutX(245);
        ResumeImgV.setLayoutY(250);
        ResumeImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), ResumeImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        ResumeImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), ResumeImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        File loadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png");
        Image RestartImg = new Image(loadRestart.toURI().toString());
        ImageView RestartImgV = new ImageView(RestartImg);
        RestartImgV.setFitHeight(80);
        RestartImgV.setFitWidth(230);
        RestartImgV.setPickOnBounds(false);
        RestartImgV.setLayoutX(245);
        RestartImgV.setLayoutY(350);
        RestartImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        RestartImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        File LoadMainMenuImg = new File("src/main/resources/com/example/arkanoid/images/MenuButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image MainMenu2Img = new Image(LoadMainMenuImg.toURI().toString());
        ImageView MainMenu2ImgV = new ImageView(MainMenu2Img);
        MainMenu2ImgV.setFitHeight(80); // set chieu cao
        MainMenu2ImgV.setFitWidth(230); // set chiều rong
        MainMenu2ImgV.setPickOnBounds(false);
        MainMenu2ImgV.setLayoutX(245); // tọa độ X của đầu nút
        MainMenu2ImgV.setLayoutY(450);
        MainMenu2ImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenu2ImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        MainMenu2ImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenu2ImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        MainMenu2ImgV.setOnMouseClicked(e->{
            try {
                Arkanoid.closeAllStages(); // đóng hết mọi Stage đang mở
                GameManager gm = GameManager.getInstance();
                gm.reset();
                SoundManager.stopBackgroundMusic();
                Stage newStage = new Stage();
                Arkanoid mainApp = new Arkanoid();
                mainApp.start(newStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        ResumeImgV.setOnMouseClicked(e -> {
            ((Pane) PauseGamePane.getParent()).getChildren().remove(PauseGamePane);
            SoundManager.resumeMusic();
            GameManager.getInstance().getGameLoop().start();
        });

        RestartImgV.setOnMouseClicked(e->{
            try {// đóng hết mọi Stage đang mở
                String bgmFile = SoundManager.getCurrentBgmFile();
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Arkanoid.closeAllStages();
                Stage newStage = new Stage();
                Arkanoid mainApp = new Arkanoid();
                SoundManager.playBackgroundMusic(bgmFile);
                mainApp.startLevel(newStage,LevelNumber);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        PauseGamePane.getChildren().addAll(MainMenu2ImgV,RestartImgV,ResumeImgV);
        return PauseGamePane;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //set button 1 va tuong tu voi cac button con lai nhe!!!
        if (stage.getScene() != null && stage.getScene().getRoot() instanceof Pane oldPane) {
            oldPane.getChildren().clear();
        }
        File LoadImg = new File("src/main/resources/com/example/arkanoid/images/StartButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image StartImg = new Image(LoadImg.toURL().toString());
        ImageView StartImgV = new ImageView(StartImg);
        StartImgV.setFitHeight(200); // set chieu cao
        StartImgV.setFitWidth(270); // set chiều rong
        StartImgV.setPickOnBounds(false);
        StartImgV.setLayoutX(325); // tọa độ X của đầu nút
        StartImgV.setLayoutY(230); // tọa độ Y của đầu nút
        StartImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), StartImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        StartImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), StartImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
        //chuc nang cho start button

        File LoadImg2 = new File("src/main/resources/com/example/arkanoid/images/TutorialBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image TutorialImg = new Image(LoadImg2.toURL().toString());
        ImageView TutorialImgV = new ImageView(TutorialImg);
        TutorialImgV.setFitHeight(200); // set chieu cao
        TutorialImgV.setFitWidth(270); // set chiều rong
        TutorialImgV.setPickOnBounds(false);
        TutorialImgV.setLayoutX(325); // tọa độ X của đầu nút
        TutorialImgV.setLayoutY(340); // tọa độ Y của đầu nút
        TutorialImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), TutorialImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        TutorialImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), TutorialImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });


        File LoadImg3 = new File("src/main/resources/com/example/arkanoid/images/ExitGameBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image ExitImg = new Image(LoadImg3.toURL().toString());
        ImageView ExitImg3 = new ImageView(ExitImg);
        ExitImg3.setFitHeight(200); // set chieu cao
        ExitImg3.setFitWidth(270); // set chiều rong
        ExitImg3.setPickOnBounds(false);
        ExitImg3.setLayoutX(325); // tọa độ X của đầu nút
        ExitImg3.setLayoutY(450); // tọa độ Y của đầu nút
        ExitImg3.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), ExitImg3);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        ExitImg3.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), ExitImg3);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        ExitImg3.setOnMouseClicked(e->{
            System.exit(0);
        });


        //tạo file để load ảnh nền
        File StartBackground = new File("src/main/resources/com/example/arkanoid/images/nenStartGame.png"); //ảnh này là nền nhé, anh em chỉnh thành ảnh gì cũng được

        //tạo ảnh nền cho phần start game
        Image anhnen = new Image(StartBackground.toURL().toString());
        ImageView bgView = new ImageView(anhnen);
        //set độ dài rộng của ảnh nền
        bgView.setFitWidth(920);
        bgView.setFitHeight(720);
        //dòng này để chọn xem có bóp méo ảnh để lấy đúng tỷ lệ hay không!!
        bgView.setPreserveRatio(false);
        //

        StackPane root = new StackPane();
        Pane bt = new Pane();
        //thêm các nút button làm con của bt
        bt.getChildren().addAll(StartImgV, TutorialImgV, ExitImg3);
        //thêm bt làm con của root để chỉ cần chạy root
        root.getChildren().addAll(bgView, bt);

        //load khung hình start game với từng tỷ lệ
        Scene scene = new Scene(root, 920, 720);
        // tiêu đề của game
        StartImgV.setOnMouseClicked(e -> {
            try {
                openLevelSelect(stage, scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        TutorialImgV.setOnMouseClicked(e->{
            TutorialGame(stage,scene);
        });
        stage.setTitle("Arkanoid Game - Team 6");

        stage.setScene(scene);
        stage.show();
    }

    public void openLevelSelect(Stage stage, Scene menuScene) throws IOException {
        Pane SelectLVPane = new Pane();
        StackPane SelectLV = new StackPane();

        Image image = new Image("file:src/main/resources/com/example/arkanoid/images/SelectLVImg.png");
        ImageView imageView = new ImageView(image);
        imageView.setViewport(new Rectangle2D(0, 0, 920, 863));
        AnimationGame anim = new AnimationGame(imageView, 4, 1056, 992, 600);
        anim.play();


        File LoadImgLV1 = new File("src/main/resources/com/example/arkanoid/images/Level1Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV1Img = new Image(LoadImgLV1.toURL().toString());
        ImageView LV1ImgV = new ImageView(LV1Img);
        LV1ImgV.setFitHeight(200); // set chieu cao
        LV1ImgV.setFitWidth(310); // set chiều rong
        LV1ImgV.setPickOnBounds(false);
        LV1ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV1ImgV.setLayoutY(100);
        LV1ImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV1ImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
            PreViewGame(SelectLVPane ,1);
        });

        LV1ImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV1ImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
            SelectLVPane.getChildren().removeIf(node -> "PreView".equals(node.getId()));
        });

        LV1ImgV.setOnMouseClicked(e -> {
            Stage stage1 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv1sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic1.wav");
            startLevel(stage, 1);
        });

        File LoadImgLV2 = new File("src/main/resources/com/example/arkanoid/images/Level2Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV2Img = new Image(LoadImgLV2.toURL().toString());
        ImageView LV2ImgV = new ImageView(LV2Img);
        LV2ImgV.setFitHeight(200); // set chieu cao
        LV2ImgV.setFitWidth(310); // set chiều rong
        LV2ImgV.setPickOnBounds(false);
        LV2ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV2ImgV.setLayoutY(200);

        LV2ImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV2ImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV2ImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV2ImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV2ImgV.setOnMouseClicked(e -> {
            Stage stage2 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv2sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic2.wav");
            startLevel(stage, 2);
        });

        File LoadImgLV3 = new File("src/main/resources/com/example/arkanoid/images/Level3Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV3Img = new Image(LoadImgLV3.toURL().toString());
        ImageView LV3ImgV = new ImageView(LV3Img);
        LV3ImgV.setFitHeight(200); // set chieu cao
        LV3ImgV.setFitWidth(310); // set chiều rong
        LV3ImgV.setPickOnBounds(false);
        LV3ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV3ImgV.setLayoutY(300);
        LV3ImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV3ImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV3ImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV3ImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV3ImgV.setOnMouseClicked(e -> {
            Stage stage3 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv3sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic3.wav");
            startLevel(stage, 3);
        });

        File LoadImgLV4 = new File("src/main/resources/com/example/arkanoid/images/Level4Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV4Img = new Image(LoadImgLV4.toURL().toString());
        ImageView LV4ImgV = new ImageView(LV4Img);
        LV4ImgV.setFitHeight(200); // set chieu cao
        LV4ImgV.setFitWidth(310); // set chiều rong
        LV4ImgV.setPickOnBounds(false);
        LV4ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV4ImgV.setLayoutY(400);
        LV4ImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV4ImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV4ImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV4ImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV4ImgV.setOnMouseClicked(e -> {
            Stage stage4 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv4sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic4.wav");
            startLevel(stage, 4);
        });

        File LoadImgLV5 = new File("src/main/resources/com/example/arkanoid/images/Level5Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV5Img = new Image(LoadImgLV5.toURL().toString());
        ImageView LV5ImgV = new ImageView(LV5Img);
        LV5ImgV.setFitHeight(200); // set chieu cao
        LV5ImgV.setFitWidth(310); // set chiều rong
        LV5ImgV.setPickOnBounds(false);
        LV5ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV5ImgV.setLayoutY(500);
        LV5ImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV5ImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV5ImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV5ImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV5ImgV.setOnMouseClicked(e -> {
            Stage stage5 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv5sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic5.wav");
            startLevel(stage, 5);
        });


        File LoadImg4 = new File("src/main/resources/com/example/arkanoid/images/BackButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image MainMenuImg = new Image(LoadImg4.toURL().toString());
        ImageView MainMenuImgV = new ImageView(MainMenuImg);
        MainMenuImgV.setFitHeight(80); // set chieu cao
        MainMenuImgV.setFitWidth(160); // set chiều rong
        MainMenuImgV.setPickOnBounds(false);
        MainMenuImgV.setLayoutX(5); // tọa độ X của đầu nút
        MainMenuImgV.setLayoutY(5);
        MainMenuImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenuImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        MainMenuImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenuImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        MainMenuImgV.setOnMouseClicked(e -> {
            try {
                start(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        SelectLVPane.getChildren().addAll(LV1ImgV, LV2ImgV, LV3ImgV, LV4ImgV, LV5ImgV, MainMenuImgV);
        SelectLV.getChildren().addAll(imageView, SelectLVPane);
        Scene lvScene = new Scene(SelectLV, 920, 720);
        stage.setScene(lvScene);
    }
    public Pane GameLoseSc(Stage stage, int score,int CurrentLV) throws Exception {
        Pane GameLosePane = new Pane();
        File LoadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png"); // nem dia chi nut start
        Image RestartImg2 = new Image(LoadRestart.toURI().toString());
        ImageView RestartImgV = new ImageView(RestartImg2);
        RestartImgV.setFitHeight(80);
        RestartImgV.setFitWidth(230);
        RestartImgV.setPickOnBounds(false);
        RestartImgV.setLayoutX(235);
        RestartImgV.setLayoutY(330);
        RestartImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        RestartImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        RestartImgV.setOnMouseClicked(e->{
            try {//  đóng hết mọi Stage đang mở
                String bgmFile = SoundManager.getCurrentBgmFile();
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Arkanoid.closeAllStages();
                Stage newStage = new Stage();
                Arkanoid mainApp = new Arkanoid();
                SoundManager.playBackgroundMusic(bgmFile);
                mainApp.startLevel(newStage,CurrentLV);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        File LoadMainmenu = new File("src/main/resources/com/example/arkanoid/images/MenuButton.png"); // nem dia chi nut start
        Image MainenuImg2 = new Image(LoadMainmenu.toURI().toString());
        ImageView MainmenuImgV2 = new ImageView(MainenuImg2);
        MainmenuImgV2.setFitHeight(90);
        MainmenuImgV2.setFitWidth(230);
        MainmenuImgV2.setPickOnBounds(false);
        MainmenuImgV2.setLayoutX(235);
        MainmenuImgV2.setLayoutY(420);
        MainmenuImgV2.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainmenuImgV2);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        MainmenuImgV2.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainmenuImgV2);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        MainmenuImgV2.setOnMouseClicked(e -> {
            try {
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Arkanoid.closeAllStages(); // đóng hết mọi Stage đang mở
                SoundManager.stopBackgroundMusic();
                Stage newStage = new Stage();
                Arkanoid mainApp = new Arkanoid();
                mainApp.start(newStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        GameLosePane.getChildren().addAll(MainmenuImgV2,RestartImgV);
        return GameLosePane;
    }

    public void TutorialGame (Stage stage, Scene scene) {

        File[] loadTutorial = {
                new File("src/main/resources/com/example/arkanoid/images/Tutorial1.png"),//nhap hinh vao day,
                new File("src/main/resources/com/example/arkanoid/images/Tutorial2.png"),// nhap dia chi vao day,
                new File("src/main/resources/com/example/arkanoid/images/Tutorial3.png"), // nhap dia chi file vao day
        };
        Image[] TutorialImg = new Image[loadTutorial.length];
        for(int i=0; i<3;++i) {
            TutorialImg[i] = new Image(loadTutorial[i].toURI().toString());
        }

        ImageView TutorialView = new ImageView(TutorialImg[0]);
        TutorialView.setFitWidth(920);
        TutorialView.setFitHeight(720);

        File loadBackButton = new File("src/main/resources/com/example/arkanoid/images/BackButton.png");
        Image backImg = new Image(loadBackButton.toURI().toString());
        ImageView backImgV = new ImageView(backImg);
        backImgV.setFitHeight(80);
        backImgV.setFitWidth(160);
        backImgV.setPickOnBounds(false);
        backImgV.setLayoutX(5);
        backImgV.setLayoutY(5);
        backImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), backImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        backImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), backImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        File loadNextButton = new File("src/main/resources/com/example/arkanoid/images/NextButton.png");
        Image nextImg = new Image(loadNextButton.toURI().toString());
        ImageView nextImgV = new ImageView(nextImg);
        nextImgV.setFitHeight(80);
        nextImgV.setFitWidth(160);
        nextImgV.setPickOnBounds(false);
        nextImgV.setLayoutX(755);
        nextImgV.setLayoutY(635);
        nextImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), nextImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        nextImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), nextImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        int[] CurrentPage = {0};

        backImgV.setOnMouseClicked(e->{
            CurrentPage[0] --;
            if(CurrentPage[0] <0) {
                CurrentPage[0] = 2;
            }
            TutorialView.setImage(TutorialImg[CurrentPage[0]]);
        });
        nextImgV.setOnMouseClicked(e->{
            CurrentPage[0] ++;
            if(CurrentPage[0] == 3) {
                CurrentPage[0] = 0;
            }
            TutorialView.setImage(TutorialImg[CurrentPage[0]]);
        });
        File LoadMainMenuT = new File("src/main/resources/com/example/arkanoid/images/BackToMenu.png");
        Image BackToMenuImg = new Image(LoadMainMenuT.toURI().toString());
        ImageView BackToMenuImgV = new ImageView(BackToMenuImg);
        BackToMenuImgV.setFitWidth(300);
        BackToMenuImgV.setFitHeight(60);
        BackToMenuImgV.setPickOnBounds(false);
        BackToMenuImgV.setLayoutX(310);
        BackToMenuImgV.setLayoutY(650);
        BackToMenuImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), BackToMenuImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        BackToMenuImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), BackToMenuImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        BackToMenuImgV.setOnMouseClicked(e->{
            try{
                start(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Pane TutorialPane = new Pane(TutorialView, backImgV, nextImgV,BackToMenuImgV);
        Scene TutorialScene = new Scene(TutorialPane,920,720);
        stage.setScene(TutorialScene);
    }
    public Pane GameWin(Stage stage, int Score) throws Exception{
        Pane GameWinPane = new Pane();

        File loadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png");
        Image RestartImg = new Image(loadRestart.toURI().toString());
        ImageView RestartImgV = new ImageView(RestartImg);
        RestartImgV.setFitHeight(80);
        RestartImgV.setFitWidth(230);
        RestartImgV.setPickOnBounds(false);
        RestartImgV.setLayoutX(235);
        RestartImgV.setLayoutY(300);
        RestartImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        RestartImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
        /*
        File loadNextLV = new File("src/main/resources/com/example/arkanoid/images/NextLevelButton.png");
        Image NextLVImg = new Image(loadNextLV.toURI().toString());
        ImageView NextLVImgV = new ImageView(NextLVImg);
        NextLVImgV.setFitHeight(70);
        NextLVImgV.setFitWidth(230);
        Button NextLevel = new Button();
        NextLevel.setStyle("-fx-background-color: transparent");
        NextLevel.setGraphic(NextLVImgV);
        NextLevel.setOnMouseEntered(e-> NextLVImgV.setOpacity(0.8));
        NextLevel.setOnMouseExited(e-> NextLVImgV.setOpacity(1.0));
        NextLevel.setLayoutX(245);
        NextLevel.setLayoutY(330);
         */


        File LoadMainMenuImg = new File("src/main/resources/com/example/arkanoid/images/MenuButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image MainMenu2Img = new Image(LoadMainMenuImg.toURI().toString());
        ImageView MainMenu2ImgV = new ImageView(MainMenu2Img);
        MainMenu2ImgV.setFitHeight(80); // set chieu cao
        MainMenu2ImgV.setFitWidth(230); // set chiều rong
        MainMenu2ImgV.setPickOnBounds(false);
        MainMenu2ImgV.setLayoutX(235); // tọa độ X của đầu nút
        MainMenu2ImgV.setLayoutY(400);
        MainMenu2ImgV.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenu2ImgV);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        MainMenu2ImgV.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenu2ImgV);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        MainMenu2ImgV.setOnMouseClicked(e -> {
            try {
                Arkanoid.closeAllStages(); //
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Stage newStage = new Stage();
                Arkanoid mainApp = new Arkanoid();
                SoundManager.stopBackgroundMusic();
                mainApp.start(newStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        RestartImgV.setOnMouseClicked(e -> {
            try {//  đóng hết mọi Stage đang mở
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Arkanoid.closeAllStages();
                Stage newStage = new Stage();
                Arkanoid mainApp = new Arkanoid();
                mainApp.startLevel(newStage, 1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        GameWinPane.getChildren().addAll(RestartImgV,MainMenu2ImgV);
        return GameWinPane;
    }
    public void startLevel(Stage stage, int LevelNumber) {
        Pane gamePane = new Pane();
        GameManager gm = GameManager.getInstance();
        File loadBackGroundImg;
        switch (LevelNumber) {
            case 1 -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level1.jpg");
            case 2 -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level1.jpg");
            case 3 -> loadBackGroundImg = new File("src/main/resources/bg_level3.png");
            case 4 -> loadBackGroundImg = new File("src/main/resources/bg_level4.png");
            case 5 -> loadBackGroundImg = new File("src/main/resources/bg_level5.png");
            default -> loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/bg_Level1.jpg");
        }
        Image loadBGImg = new Image(loadBackGroundImg.toURI().toString());
        ImageView loadBGImgV = new ImageView(loadBGImg);
        loadBGImgV.setFitWidth(720);
        loadBGImgV.setFitHeight(720);
        gamePane.getChildren().add(loadBGImgV);
        gm.init(gamePane, this, LevelNumber);
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
                case ENTER -> gm.detonateBombs();
                case ESCAPE -> {
                    try {
                        gameLoop.stop(); // dừng game
                        Pane pausePane = PauseGame(stage, LevelNumber); // tạo menu pause
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
    public Stage getPrimaryStage() {
        return getPrimaryStage();
    }
    public void PreViewGame(Pane PreView, int LevelNumber) {
        File loadPreView;
        switch (LevelNumber) {
            case 1 -> loadPreView = new File("src/main/resources/com/example/arkanoid/images/TestPreView.jfif");
            case 2 -> loadPreView = new File("");
            case 3 -> loadPreView = new File("");
            case 4 -> loadPreView = new File("");
            case 5 -> loadPreView = new File("");
            default -> loadPreView = new File("");
        }
        Image PreViewImg = new Image(loadPreView.toURI().toString());
        ImageView PreImgView = new ImageView(PreViewImg);
        PreImgView.setFitHeight(400);
        PreImgView.setFitWidth(360);
        PreImgView.setX(530);
        PreImgView.setY(120);
        PreImgView.setId("PreView");
        PreView.getChildren().add(PreImgView);
    }
}