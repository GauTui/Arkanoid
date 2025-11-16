package com.example.arkanoid.Controller.GameState;

import com.example.arkanoid.Application.Arkanoid;
import com.example.arkanoid.Controller.Loop.StartLevel;
import com.example.arkanoid.Controller.Managers.GameManager;
import com.example.arkanoid.Controller.Utils.HoverEffect;
import com.example.arkanoid.Controller.Utils.BackgroundMusic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.File;

public class GameLose {
    public Pane GameLoseSc(Stage stage, int score, int CurrentLV, Arkanoid mainApp) throws Exception {
        Pane GameLosePane = new Pane();
        File LoadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png"); // nem dia chi nut start
        Image RestartImg2 = new Image(LoadRestart.toURI().toString());
        ImageView RestartImgV = new ImageView(RestartImg2);
        RestartImgV.setFitHeight(80);
        RestartImgV.setFitWidth(230);
        RestartImgV.setPickOnBounds(false);
        RestartImgV.setLayoutX(235);
        RestartImgV.setLayoutY(330);
        HoverEffect.addHoverEffect(RestartImgV);

        RestartImgV.setOnMouseClicked(e->{
            try {//  đóng hết mọi Stage đang mở
                String bgmFile = BackgroundMusic.getCurrentBgmFile();
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Arkanoid.closeAllStages();
                Stage newStage = new Stage();
                BackgroundMusic.playBackgroundMusic(bgmFile);
                StartLevel s1 = new StartLevel();
                s1.startLevel(newStage, CurrentLV,mainApp);
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
        HoverEffect.addHoverEffect(MainmenuImgV2);

        MainmenuImgV2.setOnMouseClicked(e -> {
            try {
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Arkanoid.closeAllStages(); // đóng hết mọi Stage đang mở
                BackgroundMusic.stopBackgroundMusic();
                Stage newStage = new Stage();
                Arkanoid mainApp1 = new Arkanoid();
                mainApp.start(newStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        GameLosePane.getChildren().addAll(MainmenuImgV2,RestartImgV);
        return GameLosePane;
    }
}
