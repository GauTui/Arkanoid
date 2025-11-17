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

public class GameWin {
    public Pane GameWin(Arkanoid mainApp) throws Exception{
        Pane GameWinPane = new Pane();

        File loadRestart = new File("src/main/resources/com/example/arkanoid/images/RestartButton.png");
        Image RestartImg = new Image(loadRestart.toURI().toString());
        ImageView RestartImgV = new ImageView(RestartImg);
        RestartImgV.setFitHeight(80);
        RestartImgV.setFitWidth(230);
        RestartImgV.setPickOnBounds(false);
        RestartImgV.setLayoutX(235);
        RestartImgV.setLayoutY(300);
        HoverEffect.addHoverEffect(RestartImgV);

        File LoadMainMenuImg = new File("src/main/resources/com/example/arkanoid/images/MenuButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image MainMenu2Img = new Image(LoadMainMenuImg.toURI().toString());
        ImageView MainMenu2ImgV = new ImageView(MainMenu2Img);
        MainMenu2ImgV.setFitHeight(80); // set chieu cao
        MainMenu2ImgV.setFitWidth(230); // set chiều rong
        MainMenu2ImgV.setPickOnBounds(false);
        MainMenu2ImgV.setLayoutX(235); // tọa độ X của đầu nút
        MainMenu2ImgV.setLayoutY(400);
        HoverEffect.addHoverEffect(MainMenu2ImgV);

        MainMenu2ImgV.setOnMouseClicked(e -> {
            try {
                Arkanoid.closeAllStages(); //
                GameManager gm = GameManager.getInstance();
                gm.reset();
                Stage newStage = new Stage();
                Arkanoid mainApp1 = new Arkanoid();
                BackgroundMusic.stopBackgroundMusic();
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
                StartLevel s1 = new StartLevel();
                s1.startLevel(newStage, 1, mainApp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        GameWinPane.getChildren().addAll(RestartImgV,MainMenu2ImgV);
        return GameWinPane;
    }
}
