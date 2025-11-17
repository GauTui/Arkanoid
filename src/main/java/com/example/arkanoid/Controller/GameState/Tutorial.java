package com.example.arkanoid.Controller.GameState;

import com.example.arkanoid.Application.Arkanoid;
import com.example.arkanoid.Controller.Utils.HoverEffect;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Tutorial {
    public static void TutorialGame (Stage stage, Scene scene) {

        File[] loadTutorial = {
                new File("src/main/resources/com/example/arkanoid/images/Tutorial1.jpg"),//nhap hinh vao day,
                new File("src/main/resources/com/example/arkanoid/images/Tutorial2.jpg"),// nhap dia chi vao day,
                new File("src/main/resources/com/example/arkanoid/images/Tutorial3.jpg"),
                new File("src/main/resources/com/example/arkanoid/images/Tutorial4.jpg"),
                new File("src/main/resources/com/example/arkanoid/images/Tutorial5.jpg"),
                new File("src/main/resources/com/example/arkanoid/images/Tutorial6.jpg"),
                new File("src/main/resources/com/example/arkanoid/images/Tutorial7.jpg"),
        };
        Image[] TutorialImg = new Image[loadTutorial.length];
        for(int i=0; i<7;++i) {
            TutorialImg[i] = new Image(loadTutorial[i].toURI().toString());
        }

        ImageView TutorialView = new ImageView(TutorialImg[0]);
        TutorialView.setFitWidth(1080);
        TutorialView.setFitHeight(720);

        File loadBackButton = new File("src/main/resources/com/example/arkanoid/images/BackButton.png");
        Image backImg = new Image(loadBackButton.toURI().toString());
        ImageView backImgV = new ImageView(backImg);
        backImgV.setFitHeight(80);
        backImgV.setFitWidth(160);
        backImgV.setPickOnBounds(false);
        backImgV.setVisible(false);
        backImgV.setLayoutX(5);
        backImgV.setLayoutY(5);
        HoverEffect.addHoverEffect(backImgV);

        File loadNextButton = new File("src/main/resources/com/example/arkanoid/images/NextButton.png");
        Image nextImg = new Image(loadNextButton.toURI().toString());
        ImageView nextImgV = new ImageView(nextImg);
        nextImgV.setFitHeight(80);
        nextImgV.setFitWidth(160);
        nextImgV.setPickOnBounds(false);
        nextImgV.setLayoutX(900);
        nextImgV.setLayoutY(635);
        HoverEffect.addHoverEffect(nextImgV);

        int[] CurrentPage = {0};

        backImgV.setOnMouseClicked(e->{
            CurrentPage[0] --;
            TutorialView.setImage(TutorialImg[CurrentPage[0]]);
            backImgV.setVisible(CurrentPage[0] > 0);
        });
        nextImgV.setOnMouseClicked(e->{
            CurrentPage[0] ++;
            if(CurrentPage[0] == 7) {
                CurrentPage[0] = 0;
            }
            TutorialView.setImage(TutorialImg[CurrentPage[0]]);
            backImgV.setVisible(CurrentPage[0] > 0);
        });
        File LoadMainMenuT = new File("src/main/resources/com/example/arkanoid/images/BackToMenu.png");
        Image BackToMenuImg = new Image(LoadMainMenuT.toURI().toString());
        ImageView BackToMenuImgV = new ImageView(BackToMenuImg);
        BackToMenuImgV.setFitWidth(240);
        BackToMenuImgV.setFitHeight(80);
        BackToMenuImgV.setPickOnBounds(false);
        BackToMenuImgV.setLayoutX((1080-240)/2);
        BackToMenuImgV.setLayoutY(630);
        HoverEffect.addHoverEffect(BackToMenuImgV);

        BackToMenuImgV.setOnMouseClicked(e->{
            try{
                Arkanoid arkanoid = new Arkanoid(); //tạo instance của Arkanoid
                arkanoid.start(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Pane TutorialPane = new Pane(TutorialView, backImgV, nextImgV,BackToMenuImgV);
        Scene TutorialScene = new Scene(TutorialPane,1080,720);
        stage.setScene(TutorialScene);
    }
}
