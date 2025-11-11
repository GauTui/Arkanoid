package com.example.arkanoid;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class Tutorial {
    public static void TutorialGame (Stage stage, Scene scene) {

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
                Arkanoid arkanoid = new Arkanoid(); //tạo instance của Arkanoid
                arkanoid.start(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Pane TutorialPane = new Pane(TutorialView, backImgV, nextImgV,BackToMenuImgV);
        Scene TutorialScene = new Scene(TutorialPane,920,720);
        stage.setScene(TutorialScene);
    }
}
