package com.example.arkanoid.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class PreViewGame {
    public static void PreViewGame(Pane PreView, int LevelNumber) {
        File loadPreView;
        switch (LevelNumber) {
            case 1 -> loadPreView = new File("src/main/resources/com/example/arkanoid/images/Pre1.jfif");
            case 2 -> loadPreView = new File("src/main/resources/com/example/arkanoid/images/Pre2.jfif");
            case 3 -> loadPreView = new File("src/main/resources/com/example/arkanoid/images/Pre3.jfif");
            case 4 -> loadPreView = new File("src/main/resources/com/example/arkanoid/images/Pre4.jfif");
            case 5 -> loadPreView = new File("src/main/resources/com/example/arkanoid/images/Pre5.jfif");
            default -> loadPreView = new File("src/main/resources/com/example/arkanoid/images/Pre1.jfif");
        }
        Image PreViewImg = new Image(loadPreView.toURI().toString());
        ImageView PreImgView = new ImageView(PreViewImg);
        PreImgView.setFitHeight(390);
        PreImgView.setFitWidth(270);
        PreImgView.setX(533);
        PreImgView.setY(205);
        PreImgView.setId("PreView");

        File loadFrame = new File("src/main/resources/com/example/arkanoid/images/PreViewFrame.png");
        Image Frame = new Image(loadFrame.toURI().toString());
        ImageView FrameView = new ImageView(Frame);
        FrameView.setFitWidth(650);
        FrameView.setFitHeight(600);
        FrameView.setX(355);
        FrameView.setY(105);
        FrameView.setId("FrameView");

        PreView.getChildren().addAll(PreImgView,FrameView);

    }
}
