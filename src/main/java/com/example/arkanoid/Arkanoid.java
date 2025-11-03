package com.example.arkanoid;

import com.example.arkanoid.Model.Paddle;
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

import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;


/**
 * cái này là để chạy start game nhé ae!!!!!
 */

public class Arkanoid extends Application {
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
        ResumeImgV.setFitHeight(70);
        ResumeImgV.setFitWidth(230);
        Button ResumeBt = new Button();
        ResumeBt.setStyle("-fx-background-color: transparent;");
        ResumeBt.setGraphic(ResumeImgV);
        ResumeBt.setLayoutX(245);
        ResumeBt.setLayoutY(330);
        ResumeBt.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), ResumeBt);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        ResumeBt.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), ResumeBt);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        File loadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png");
        Image RestartImg = new Image(loadRestart.toURI().toString());
        ImageView RestartImgV = new ImageView(RestartImg);
        RestartImgV.setFitHeight(70);
        RestartImgV.setFitWidth(230);
        Button RestartBt = new Button();
        RestartBt.setStyle("-fx-background-color: transparent;");
        RestartBt.setGraphic(RestartImgV);
        RestartBt.setLayoutX(245);
        RestartBt.setLayoutY(420);
        RestartBt.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartBt);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        RestartBt.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartBt);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        File LoadMainMenuImg = new File("src/main/resources/com/example/arkanoid/images/MenuButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image MainMenu2Img = new Image(LoadMainMenuImg.toURI().toString());
        ImageView MainMenu2ImgV = new ImageView(MainMenu2Img);
        MainMenu2ImgV.setFitHeight(70); // set chieu cao
        MainMenu2ImgV.setFitWidth(230); // set chiều rong
        Button MainMenu2Button = new Button();
        MainMenu2Button.setStyle("-fx-background-color: transparent;");
        MainMenu2Button.setGraphic(MainMenu2ImgV);
        MainMenu2Button.setLayoutX(245); // tọa độ X của đầu nút
        MainMenu2Button.setLayoutY(510);
        MainMenu2Button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenu2Button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        MainMenu2Button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenu2Button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        MainMenu2Button.setOnAction(e->{
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

        ResumeBt.setOnAction(e -> {
            ((Pane) PauseGamePane.getParent()).getChildren().remove(PauseGamePane);
            SoundManager.resumeMusic();
            GameManager.getInstance().getGameLoop().start();
        });

        RestartBt.setOnAction(e->{
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

        PauseGamePane.getChildren().addAll(MainMenu2Button,RestartBt,ResumeBt);
        return PauseGamePane;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //set button 1 va tuong tu voi cac button con lai nhe!!!
        if (stage.getScene() != null && stage.getScene().getRoot() instanceof Pane oldPane) {
            oldPane.getChildren().clear();
        }
        File LoadImg = new File("src/main/resources/com/example/arkanoid/images/StartGameBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image StartImg = new Image(LoadImg.toURL().toString());
        ImageView StartImgV = new ImageView(StartImg);
        StartImgV.setFitHeight(70); // set chieu cao
        StartImgV.setFitWidth(230); // set chiều rong
        Button StartButton = new Button();
        StartButton.setStyle("-fx-background-color: transparent;");
        StartButton.setGraphic(StartImgV);
        StartButton.setLayoutX(345); // tọa độ X của đầu nút
        StartButton.setLayoutY(330); // tọa độ Y của đầu nút
        StartButton.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), StartButton);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        StartButton.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), StartButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
        //chuc nang cho start button

        File LoadImg2 = new File("src/main/resources/com/example/arkanoid/images/TutorialBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image TutorialImg = new Image(LoadImg2.toURL().toString());
        ImageView TutorialImgV = new ImageView(TutorialImg);
        TutorialImgV.setFitHeight(70); // set chieu cao
        TutorialImgV.setFitWidth(230); // set chiều rong
        Button TutorialButton = new Button();
        TutorialButton.setStyle("-fx-background-color: transparent;");
        TutorialButton.setGraphic(TutorialImgV);
        TutorialButton.setLayoutX(345); // tọa độ X của đầu nút
        TutorialButton.setLayoutY(420); // tọa độ Y của đầu nút
        TutorialButton.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), TutorialButton);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        TutorialButton.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), TutorialButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });


        File LoadImg3 = new File("src/main/resources/com/example/arkanoid/images/ExitGameBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image ExitImg = new Image(LoadImg3.toURL().toString());
        ImageView ExitImg3 = new ImageView(ExitImg);
        ExitImg3.setFitHeight(70); // set chieu cao
        ExitImg3.setFitWidth(230); // set chiều rong
        Button ExitButton = new Button();
        ExitButton.setStyle("-fx-background-color: transparent;");
        ExitButton.setGraphic(ExitImg3);
        ExitButton.setLayoutX(345); // tọa độ X của đầu nút
        ExitButton.setLayoutY(510); // tọa độ Y của đầu nút
        ExitButton.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), ExitButton);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        ExitButton.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), ExitButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        ExitButton.setOnAction(e->{
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
        bt.getChildren().addAll(StartButton, TutorialButton, ExitButton);
        //thêm bt làm con của root để chỉ cần chạy root
        root.getChildren().addAll(bgView, bt);

        //load khung hình start game với từng tỷ lệ
        Scene scene = new Scene(root, 920, 720);
        // tiêu đề của game
        StartButton.setOnAction(e -> {
            try {
                openLevelSelect(stage, scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        TutorialButton.setOnAction(e->{
            TutorialGame(stage,scene);
        });
        stage.setTitle("Arkanoid Game - Team 6");

        stage.setScene(scene);
        stage.show();
    }

    public void openLevelSelect(Stage stage, Scene menuScene) throws IOException {
        Pane SelectLVButton = new Pane();
        StackPane SelectLV = new StackPane();
        File PauseBackground = new File("src/main/resources/com/example/arkanoid/images/SelectLVBG.png");// nhap dia chi;
        Image anhpause = new Image(PauseBackground.toURL().toString());
        ImageView pbgView = new ImageView(anhpause);
        pbgView.setFitWidth(920);
        pbgView.setFitHeight(720);


        File LoadImgLV1 = new File("src/main/resources/com/example/arkanoid/images/Level1Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV1Img = new Image(LoadImgLV1.toURL().toString());
        ImageView LV1ImgV = new ImageView(LV1Img);
        LV1ImgV.setFitHeight(60); // set chieu cao
        LV1ImgV.setFitWidth(310); // set chiều rong
        Button LV1Button = new Button();
        LV1Button.setStyle("-fx-background-color: transparent;");
        LV1Button.setGraphic(LV1ImgV);
        LV1Button.setLayoutX(30); // tọa độ X của đầu nút
        LV1Button.setLayoutY(180);
        LV1Button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV1Button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV1Button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV1Button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV1Button.setOnAction(e -> {
            Stage stage1 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv1sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic1.wav");
            startLevel(stage, 1);
        });

        File LoadImgLV2 = new File("src/main/resources/com/example/arkanoid/images/Level2Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV2Img = new Image(LoadImgLV2.toURL().toString());
        ImageView LV2ImgV = new ImageView(LV2Img);
        LV2ImgV.setFitHeight(60); // set chieu cao
        LV2ImgV.setFitWidth(310); // set chiều rong
        Button LV2Button = new Button();
        LV2Button.setStyle("-fx-background-color: transparent;");
        LV2Button.setGraphic(LV2ImgV);
        LV2Button.setLayoutX(30); // tọa độ X của đầu nút
        LV2Button.setLayoutY(260);

        LV2Button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV2Button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV2Button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV2Button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV2Button.setOnAction(e -> {
            Stage stage2 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv2sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic2.wav");
            startLevel(stage, 2);
        });

        File LoadImgLV3 = new File("src/main/resources/com/example/arkanoid/images/Level3Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV3Img = new Image(LoadImgLV3.toURL().toString());
        ImageView LV3ImgV = new ImageView(LV3Img);
        LV3ImgV.setFitHeight(60); // set chieu cao
        LV3ImgV.setFitWidth(310); // set chiều rong
        Button LV3Button = new Button();
        LV3Button.setStyle("-fx-background-color: transparent;");
        LV3Button.setGraphic(LV3ImgV);
        LV3Button.setLayoutX(30); // tọa độ X của đầu nút
        LV3Button.setLayoutY(340);
        LV3Button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV3Button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV3Button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV3Button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV3Button.setOnAction(e -> {
            Stage stage3 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv3sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic3.wav");
            startLevel(stage, 3);
        });

        File LoadImgLV4 = new File("src/main/resources/com/example/arkanoid/images/Level4Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV4Img = new Image(LoadImgLV4.toURL().toString());
        ImageView LV4ImgV = new ImageView(LV4Img);
        LV4ImgV.setFitHeight(60); // set chieu cao
        LV4ImgV.setFitWidth(310); // set chiều rong
        Button LV4Button = new Button();
        LV4Button.setStyle("-fx-background-color: transparent;");
        LV4Button.setGraphic(LV4ImgV);
        LV4Button.setLayoutX(30); // tọa độ X của đầu nút
        LV4Button.setLayoutY(420);
        LV4Button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV4Button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV4Button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV4Button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV4Button.setOnAction(e -> {
            Stage stage4 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv4sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic4.wav");
            startLevel(stage, 4);
        });

        File LoadImgLV5 = new File("src/main/resources/com/example/arkanoid/images/Level5Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV5Img = new Image(LoadImgLV5.toURL().toString());
        ImageView LV5ImgV = new ImageView(LV5Img);
        LV5ImgV.setFitHeight(60); // set chieu cao
        LV5ImgV.setFitWidth(310); // set chiều rong
        Button LV5Button = new Button();
        LV5Button.setStyle("-fx-background-color: transparent;");
        LV5Button.setGraphic(LV5ImgV);
        LV5Button.setLayoutX(30); // tọa độ X của đầu nút
        LV5Button.setLayoutY(500);
        LV5Button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV5Button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV5Button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV5Button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV5Button.setOnAction(e -> {
            Stage stage5 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv5sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic5.wav");
            startLevel(stage, 5);
        });

        File LoadImgLV6 = new File("src/main/resources/com/example/arkanoid/images/Level6Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV6Img = new Image(LoadImgLV6.toURL().toString());
        ImageView LV6ImgV = new ImageView(LV6Img);
        LV6ImgV.setFitHeight(60); // set chieu cao
        LV6ImgV.setFitWidth(310); // set chiều rong
        Button LV6Button = new Button();
        LV6Button.setStyle("-fx-background-color: transparent;");
        LV6Button.setGraphic(LV6ImgV);
        LV6Button.setOnMouseExited(e-> LV6Button.setOpacity(1.0));
        LV6Button.setOnMouseEntered(e-> LV6Button.setOpacity(0.5));// set ảnh
        LV6Button.setLayoutX(30); // tọa độ X của đầu nút
        LV6Button.setLayoutY(580);
        LV6Button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV6Button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        LV6Button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), LV6Button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        LV6Button.setOnAction(e -> {
            Stage stage6 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv6sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic1.wav");
            startLevel(stage, 6);
        });

        File LoadImg4 = new File("src/main/resources/com/example/arkanoid/images/BackButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image MainMenuImg = new Image(LoadImg4.toURL().toString());
        ImageView MainMenuImgV = new ImageView(MainMenuImg);
        MainMenuImgV.setFitHeight(60); // set chieu cao
        MainMenuImgV.setFitWidth(110); // set chiều rong
        Button MainMenuButton = new Button();
        MainMenuButton.setStyle("-fx-background-color: transparent;");
        MainMenuButton.setGraphic(MainMenuImgV);
        MainMenuButton.setLayoutX(20); // tọa độ X của đầu nút
        MainMenuButton.setLayoutY(20);
        MainMenuButton.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenuButton);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        MainMenuButton.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenuButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        MainMenuButton.setOnAction(e -> {
            try {
                start(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        SelectLVButton.getChildren().addAll(LV1Button, LV2Button, LV3Button, LV4Button, LV5Button,LV6Button, MainMenuButton);
        SelectLV.getChildren().addAll(pbgView, SelectLVButton);
        Scene lvScene = new Scene(SelectLV, 920, 720);
        stage.setScene(lvScene);
    }
    public Pane GameLoseSc(Stage stage, int score,int CurrentLV) throws Exception {
        Pane GameLosePane = new Pane();
        File LoadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png"); // nem dia chi nut start
        Image RestartImg2 = new Image(LoadRestart.toURI().toString());
        ImageView RestartImgV = new ImageView(RestartImg2);
        RestartImgV.setFitHeight(70);
        RestartImgV.setFitWidth(230);
        Button RestartButton2 = new Button();
        RestartButton2.setStyle("-fx-background-color: transparent;");
        RestartButton2.setGraphic(RestartImgV);
        RestartButton2.setLayoutX(345);
        RestartButton2.setLayoutY(330);
        RestartButton2.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartButton2);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        RestartButton2.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartButton2);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        RestartButton2.setOnAction(e->{
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
        MainmenuImgV2.setFitHeight(70);
        MainmenuImgV2.setFitWidth(230);
        Button MainMenuButton2 = new Button();
        MainMenuButton2.setStyle("-fx-background-color: transparent;");
        MainMenuButton2.setGraphic(MainmenuImgV2);
        MainMenuButton2.setLayoutX(345);
        MainMenuButton2.setLayoutY(420);
        MainMenuButton2.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenuButton2);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        MainMenuButton2.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenuButton2);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        MainMenuButton2.setOnAction(e -> {
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

        GameLosePane.getChildren().addAll(MainMenuButton2,RestartButton2);
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
        backImgV.setFitHeight(55);
        backImgV.setFitWidth(110);
        Button BackButton = new Button();
        BackButton.setStyle("-fx-background-colo: transparent; -fx-padding: 0;");
        BackButton.setGraphic(backImgV);
        BackButton.setLayoutX(30);
        BackButton.setLayoutY(5);
        BackButton.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), BackButton);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        BackButton.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), BackButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        File loadNextButton = new File("src/main/resources/com/example/arkanoid/images/NextButton.png");
        Image nextImg = new Image(loadNextButton.toURI().toString());
        ImageView nextImgV = new ImageView(nextImg);
        nextImgV.setFitHeight(55);
        nextImgV.setFitWidth(110);
        Button NextButton = new Button();
        NextButton.setStyle("-fx-background-colo: transparent; -fx-padding: 0;");
        NextButton.setGraphic(nextImgV);
        NextButton.setLayoutX(790);
        NextButton.setLayoutY(650);
        NextButton.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), NextButton);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        NextButton.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), NextButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        int[] CurrentPage = {0};

        BackButton.setOnAction(e->{
            CurrentPage[0] --;
            if(CurrentPage[0] <0) {
                CurrentPage[0] = 2;
            }
            TutorialView.setImage(TutorialImg[CurrentPage[0]]);
        });
        NextButton.setOnAction(e->{
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
        Button BackToMenu = new Button();
        BackToMenu.setGraphic(BackToMenuImgV);
        BackToMenu.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        BackToMenu.setLayoutX(310);
        BackToMenu.setLayoutY(650);
        BackToMenu.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), BackToMenu);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        BackToMenu.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), BackToMenu);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        BackToMenu.setOnAction(e->{
            try{
                start(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Pane TutorialPane = new Pane(TutorialView, BackButton, NextButton,BackToMenu);
        Scene TutorialScene = new Scene(TutorialPane,920,720);
        stage.setScene(TutorialScene);
    }
    public Pane GameWin(Stage stage, int Score) throws Exception{
        Pane GameWinPane = new Pane();

        File loadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png");
        Image RestartImg = new Image(loadRestart.toURI().toString());
        ImageView RestartImgV = new ImageView(RestartImg);
        RestartImgV.setFitHeight(70);
        RestartImgV.setFitWidth(230);
        Button RestartBt = new Button();
        RestartBt.setStyle("-fx-background-color: transparent;");
        RestartBt.setGraphic(RestartImgV);
        RestartBt.setLayoutX(345);
        RestartBt.setLayoutY(420);
        RestartBt.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartBt);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        RestartBt.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), RestartBt);
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
        MainMenu2ImgV.setFitHeight(70); // set chieu cao
        MainMenu2ImgV.setFitWidth(230); // set chiều rong
        Button MainMenu2Button = new Button();
        MainMenu2Button.setStyle("-fx-background-color: transparent;");
        MainMenu2Button.setGraphic(MainMenu2ImgV);
        MainMenu2Button.setLayoutX(345); // tọa độ X của đầu nút
        MainMenu2Button.setLayoutY(510);
        MainMenu2Button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenu2Button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        MainMenu2Button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), MainMenu2Button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        MainMenu2Button.setOnAction(e -> {
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

        RestartBt.setOnAction(e->{
            try {//  đóng hết mọi Stage đang mở
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Arkanoid.closeAllStages();
                Stage newStage = new Stage();
                Arkanoid mainApp = new Arkanoid();
                mainApp.startLevel(newStage,1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        GameWinPane.getChildren().addAll(RestartBt,MainMenu2Button);
        return GameWinPane;
    }
    public void startLevel(Stage stage, int LevelNumber) {
        Pane gamePane = new Pane();
        GameManager gm = GameManager.getInstance();
        File loadBackGroundImg = new File("src/main/resources/com/example/arkanoid/images/NenInGame.png");
        Image loadBGImg = new Image(loadBackGroundImg.toURI().toString());
        ImageView loadBGImgV = new ImageView(loadBGImg);
        loadBGImgV.setFitWidth(720);
        loadBGImgV.setFitHeight(800);
        gamePane.getChildren().add(loadBGImgV);
        gm.init(gamePane, this, LevelNumber);
        Scene scene = new Scene(gamePane, 920, GameManager.SCREEN_HEIGHT);
        loadBGImgV.setPreserveRatio(false);

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
                        mouseX - paddle.getWidth() / 2.0));
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
        gm.setGameLoop(gameLoop);
        gameLoop.start();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    gameLoop.stop(); // dừng game
                    Pane pausePane = PauseGame(stage,LevelNumber); // tạo menu pause
                    gamePane.getChildren().add(pausePane); // chồng menu lên game
                    SoundManager.pauseMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stage.setScene(scene);
        stage.show();
        Platform.runLater(() -> stage.centerOnScreen());
    }
    public Stage getPrimaryStage() {
        return getPrimaryStage();
    }
}
