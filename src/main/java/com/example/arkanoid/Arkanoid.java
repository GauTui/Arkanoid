package com.example.arkanoid;

import com.example.arkanoid.Model.Paddle;
import com.example.arkanoid.Utils.AnimationGame;
import com.example.arkanoid.Utils.BackgroundMusic;
import com.example.arkanoid.Utils.HoverEffect;
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
import jdk.jfr.Frequency;

import java.io.File;


import java.io.IOException;
import java.net.MalformedURLException;


/**
 * cái này là để chạy start game nhé ae!!!!!
 */

public class Arkanoid extends Application {
    // pixels per second
    public static final int SCRENWIDTH = 1080;
    public static final int SCRENHEIGHT = 720;
    public static final int BUTTONWIDTH = 270;
    public static final int BUTTONHEIGHT = 200;
    public static void closeAllStages() {
        Platform.runLater(() -> {
            for (Window window : Stage.getWindows()) {
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            }
        });
    }
    private void switchToMainMenu(Stage stage) {
        try {
            stage.getScene().setRoot(new Pane()); // clear nodes cũ
            start(stage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        StartImgV.setFitHeight(BUTTONHEIGHT); // set chieu cao
        StartImgV.setFitWidth(BUTTONWIDTH); // set chiều rong
        StartImgV.setPickOnBounds(false);
        StartImgV.setLayoutX((SCRENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
        StartImgV.setLayoutY(230); // tọa độ Y của đầu nút
        HoverEffect.addHoverEffect(StartImgV);
        //chuc nang cho start button

        File LoadImg2 = new File("src/main/resources/com/example/arkanoid/images/TutorialBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image TutorialImg = new Image(LoadImg2.toURL().toString());
        ImageView TutorialImgV = new ImageView(TutorialImg);
        TutorialImgV.setFitHeight(BUTTONHEIGHT); // set chieu cao
        TutorialImgV.setFitWidth(BUTTONWIDTH); // set chiều rong
        TutorialImgV.setPickOnBounds(false);
        TutorialImgV.setLayoutX((SCRENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
        TutorialImgV.setLayoutY(340); // tọa độ Y của đầu nút
        HoverEffect.addHoverEffect(TutorialImgV);


        File LoadImg3 = new File("src/main/resources/com/example/arkanoid/images/ExitGameBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image ExitImg = new Image(LoadImg3.toURL().toString());
        ImageView ExitImg3 = new ImageView(ExitImg);
        ExitImg3.setFitHeight(BUTTONHEIGHT); // set chieu cao
        ExitImg3.setFitWidth(BUTTONWIDTH); // set chiều rong
        ExitImg3.setPickOnBounds(false);
        ExitImg3.setLayoutX((SCRENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
        ExitImg3.setLayoutY(450); // tọa độ Y của đầu nút
        HoverEffect.addHoverEffect(ExitImg3);

        ExitImg3.setOnMouseClicked(e->{
            System.exit(0);
        });


        //tạo file để load ảnh nền
        File StartBackground = new File("src/main/resources/com/example/arkanoid/images/nenStartGame.png"); //ảnh này là nền nhé, anh em chỉnh thành ảnh gì cũng được

        //tạo ảnh nền cho phần start game
        Image anhnen = new Image(StartBackground.toURL().toString());
        ImageView bgView = new ImageView(anhnen);
        //set độ dài rộng của ảnh nền
        bgView.setFitWidth(SCRENWIDTH);
        bgView.setFitHeight(SCRENHEIGHT);
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
        Scene scene = new Scene(root, SCRENWIDTH, SCRENHEIGHT);
        // tiêu đề của game
        StartImgV.setOnMouseClicked(e -> {
            try {
                SelectLevel selectLevel = new SelectLevel();
                selectLevel.openLevelSelect(stage, scene, this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        TutorialImgV.setOnMouseClicked(e->{
            Tutorial.TutorialGame(stage,scene);
        });
        stage.setTitle("Arkanoid Game - Team 6");

        stage.setScene(scene);
        stage.show();
    }
    public Stage getPrimaryStage() {
        return getPrimaryStage();
    }
    public Pane createGameWin(Stage stage, int score) throws Exception {
        GameWin win = new GameWin();
        return win.GameWin(stage, score, this); // truyền mainApp vào nếu cần
    }

    public Pane createGameLose(Stage stage, int score, int level) throws Exception {
        GameLose lose = new GameLose();
        return lose.GameLoseSc(stage, score, level, this);
    }
}