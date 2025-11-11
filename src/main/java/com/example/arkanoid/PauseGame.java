package com.example.arkanoid;

import com.example.arkanoid.Utils.SoundManager;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class PauseGame {
    public static Pane PauseGame(Stage stage, int LevelNumber, Arkanoid mainApp) throws Exception {
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
                Arkanoid mainApp1 = new Arkanoid();
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
                Arkanoid mainApp1 = new Arkanoid();
                SoundManager.playBackgroundMusic(bgmFile);
                StartLevel s1 = new StartLevel();
                s1.startLevel(newStage, LevelNumber,mainApp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        PauseGamePane.getChildren().addAll(MainMenu2ImgV,RestartImgV,ResumeImgV);
        return PauseGamePane;
    }
}
