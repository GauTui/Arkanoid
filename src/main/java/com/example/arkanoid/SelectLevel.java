package com.example.arkanoid;

import com.example.arkanoid.Utils.AnimationGame;
import com.example.arkanoid.Utils.HoverEffect;
import com.example.arkanoid.Utils.SoundManager;
import javafx.animation.ScaleTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import com.example.arkanoid.SelectLevel;
import com.example.arkanoid.GameLose;
import com.example.arkanoid.StartLevel;
import com.example.arkanoid.GameWin;


public class SelectLevel {
    public void openLevelSelect(Stage stage, Scene menuScene, Arkanoid mainApp) throws IOException {
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
        HoverEffect.addHoverEffect(LV1ImgV);
        LV1ImgV.setOnMouseEntered(e -> {
            PreViewGame.PreViewGame(SelectLVPane,1);
        });
        LV1ImgV.setOnMouseExited(e -> {
            SelectLVPane.getChildren().removeIf(node ->"PreView".equals(node.getId()));
            SelectLVPane.getChildren().removeIf(node ->"FrameView".equals(node.getId()));
        });

        LV1ImgV.setOnMouseClicked(e -> {
            Stage stage1 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv1sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic1.wav");
            StartLevel s1 = new StartLevel();
            s1.startLevel(stage, 1,mainApp);
        });

        File LoadImgLV2 = new File("src/main/resources/com/example/arkanoid/images/Level2Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV2Img = new Image(LoadImgLV2.toURL().toString());
        ImageView LV2ImgV = new ImageView(LV2Img);
        LV2ImgV.setFitHeight(200); // set chieu cao
        LV2ImgV.setFitWidth(310); // set chiều rong
        LV2ImgV.setPickOnBounds(false);
        LV2ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV2ImgV.setLayoutY(200);
        HoverEffect.addHoverEffect(LV2ImgV);
        LV2ImgV.setOnMouseEntered(e -> {
            PreViewGame.PreViewGame(SelectLVPane,2);
        });

        LV2ImgV.setOnMouseExited(e -> {
            SelectLVPane.getChildren().removeIf(node ->"PreView".equals(node.getId()));
            SelectLVPane.getChildren().removeIf(node ->"FrameView".equals(node.getId()));
        });

        LV2ImgV.setOnMouseClicked(e -> {
            Stage stage2 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv2sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic2.wav");
            StartLevel s1 = new StartLevel();
            s1.startLevel(stage, 2,mainApp);
        });

        File LoadImgLV3 = new File("src/main/resources/com/example/arkanoid/images/Level3Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV3Img = new Image(LoadImgLV3.toURL().toString());
        ImageView LV3ImgV = new ImageView(LV3Img);
        LV3ImgV.setFitHeight(200); // set chieu cao
        LV3ImgV.setFitWidth(310); // set chiều rong
        LV3ImgV.setPickOnBounds(false);
        LV3ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV3ImgV.setLayoutY(300);
        HoverEffect.addHoverEffect(LV3ImgV);
        LV3ImgV.setOnMouseEntered(e -> {
            PreViewGame.PreViewGame(SelectLVPane,3);
        });

        LV3ImgV.setOnMouseExited(e -> {
            SelectLVPane.getChildren().removeIf(node ->"PreView".equals(node.getId()));
            SelectLVPane.getChildren().removeIf(node ->"FrameView".equals(node.getId()));
        });

        LV3ImgV.setOnMouseClicked(e -> {
            Stage stage3 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv3sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic3.wav");
            StartLevel s1 = new StartLevel();
            s1.startLevel(stage, 3,mainApp);
        });

        File LoadImgLV4 = new File("src/main/resources/com/example/arkanoid/images/Level4Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV4Img = new Image(LoadImgLV4.toURL().toString());
        ImageView LV4ImgV = new ImageView(LV4Img);
        LV4ImgV.setFitHeight(200); // set chieu cao
        LV4ImgV.setFitWidth(310); // set chiều rong
        LV4ImgV.setPickOnBounds(false);
        LV4ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV4ImgV.setLayoutY(400);
        HoverEffect.addHoverEffect(LV4ImgV);
        LV4ImgV.setOnMouseEntered(e -> {
            PreViewGame.PreViewGame(SelectLVPane,4);
        });
        LV4ImgV.setOnMouseExited(e -> {
            SelectLVPane.getChildren().removeIf(node ->"PreView".equals(node.getId()));
            SelectLVPane.getChildren().removeIf(node ->"FrameView".equals(node.getId()));
        });

        LV4ImgV.setOnMouseClicked(e -> {
            Stage stage4 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv4sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic4.wav");
            StartLevel s1 = new StartLevel();
            s1.startLevel(stage, 4,mainApp);
        });

        File LoadImgLV5 = new File("src/main/resources/com/example/arkanoid/images/Level5Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV5Img = new Image(LoadImgLV5.toURL().toString());
        ImageView LV5ImgV = new ImageView(LV5Img);
        LV5ImgV.setFitHeight(200); // set chieu cao
        LV5ImgV.setFitWidth(310); // set chiều rong
        LV5ImgV.setPickOnBounds(false);
        LV5ImgV.setLayoutX(30); // tọa độ X của đầu nút
        LV5ImgV.setLayoutY(500);
        HoverEffect.addHoverEffect(LV5ImgV);
        LV5ImgV.setOnMouseEntered(e -> {
            PreViewGame.PreViewGame(SelectLVPane,5);
        });
        LV5ImgV.setOnMouseExited(e -> {
            SelectLVPane.getChildren().removeIf(node ->"PreView".equals(node.getId()));
            SelectLVPane.getChildren().removeIf(node ->"FrameView".equals(node.getId()));
        });

        LV5ImgV.setOnMouseClicked(e -> {
            Stage stage5 = (Stage) ((Node)e.getSource()).getScene().getWindow();
            SoundManager lv5sound = new SoundManager();
            SoundManager.playBackgroundMusic("src/main/resources/com/example/arkanoid/sounds/gameMusic5.wav");
            StartLevel s1 = new StartLevel();
            s1.startLevel(stage, 5, mainApp);
        });


        File LoadImg4 = new File("src/main/resources/com/example/arkanoid/images/BackButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image MainMenuImg = new Image(LoadImg4.toURL().toString());
        ImageView MainMenuImgV = new ImageView(MainMenuImg);
        MainMenuImgV.setFitHeight(80); // set chieu cao
        MainMenuImgV.setFitWidth(160); // set chiều rong
        MainMenuImgV.setPickOnBounds(false);
        MainMenuImgV.setLayoutX(5); // tọa độ X của đầu nút
        MainMenuImgV.setLayoutY(5);
        HoverEffect.addHoverEffect(MainMenuImgV);
        MainMenuImgV.setOnMouseClicked(e -> {
            try {
                Arkanoid arkanoid = new Arkanoid();
                arkanoid.start(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        SelectLVPane.getChildren().addAll(LV1ImgV, LV2ImgV, LV3ImgV, LV4ImgV, LV5ImgV, MainMenuImgV);
        SelectLV.getChildren().addAll(imageView, SelectLVPane);
        Scene lvScene = new Scene(SelectLV, 920, 720);
        stage.setScene(lvScene);
    }
}
