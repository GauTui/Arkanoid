package com.example.arkanoid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.File;

import java.io.IOException;


/**
 * cái này là để chạy start game nhé ae!!!!!
 */

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //set button 1 va tuong tu voi cac button con lai nhe!!!
        File LoadImg = new File("C:\\Users\\Gold D Rophi\\Desktop\\imageGame\\StartGameBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image StartImg = new Image(LoadImg.toURL().toString());
        ImageView StartImgV = new ImageView(StartImg);
        StartImgV.setFitHeight(70); // set chieu cao
        StartImgV.setFitWidth(230); // set chiều rong
        Button StartButton = new Button();
        StartButton.setStyle("-fx-background-color: transparent;");
        StartButton.setGraphic(StartImgV); // set ảnh
        StartButton.setLayoutX(245); // tọa độ X của đầu nút
        StartButton.setLayoutY(330); // tọa độ Y của đầu nút
        //chuc nang cho start button

        File LoadImg2 = new File("C:\\Users\\Gold D Rophi\\Desktop\\imageGame\\TutorialBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image TutorialImg = new Image(LoadImg2.toURL().toString());
        ImageView TutorialImgV = new ImageView(TutorialImg);
        TutorialImgV.setFitHeight(70); // set chieu cao
        TutorialImgV.setFitWidth(230); // set chiều rong
        Button TutorialButton = new Button();
        TutorialButton.setStyle("-fx-background-color: transparent;");
        TutorialButton.setGraphic(TutorialImgV); // set ảnh
        TutorialButton.setLayoutX(245); // tọa độ X của đầu nút
        TutorialButton.setLayoutY(420); // tọa độ Y của đầu nút


        File LoadImg3 = new File("src/image/ExitGameBt.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image ExitImg = new Image(LoadImg3.toURL().toString());
        ImageView ExitImg3 = new ImageView(ExitImg);
        ExitImg3.setFitHeight(70); // set chieu cao
        ExitImg3.setFitWidth(230); // set chiều rong
        Button ExitButton = new Button();
        ExitButton.setStyle("-fx-background-color: transparent;");
        ExitButton.setGraphic(ExitImg3); // set ảnh
        ExitButton.setLayoutX(245); // tọa độ X của đầu nút
        ExitButton.setLayoutY(510); // tọa độ Y của đầu nút


        //tạo file để load ảnh nền
        File StartBackground = new File("C:\\Users\\Gold D Rophi\\Desktop\\imageGame\\nenStartGame.png"); //ảnh này là nền nhé, anh em chỉnh thành ảnh gì cũng được

        //tạo ảnh nền cho phần start game
        Image anhnen = new Image(StartBackground.toURL().toString());
        ImageView bgView = new ImageView(anhnen);
        //set độ dài rộng của ảnh nền
        bgView.setFitWidth(720);
        bgView.setFitHeight(800);
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
        Scene scene = new Scene(root, 720, 800);
        // tiêu đề của game
        StartButton.setOnAction(e -> {
            try {
                openLevelSelect(stage, scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        stage.setTitle("Arkanoid Game - Team 6");

        stage.setScene(scene);
        stage.show();
    }

    public void openLevelSelect(Stage stage, Scene menuScene) throws IOException {
        Pane SelectLVButton = new Pane();
        StackPane SelectLV = new StackPane();
        File PauseBackground = new File("C:\\Users\\Gold D Rophi\\Desktop\\imageGame\\SelectLVBG.png");// nhap dia chi;
        Image anhpause = new Image(PauseBackground.toURL().toString());
        ImageView pbgView = new ImageView(anhpause);
        pbgView.setFitWidth(720);
        pbgView.setFitHeight(800);


        File LoadImgLV1 = new File("src/image/Level1Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV1Img = new Image(LoadImgLV1.toURL().toString());
        ImageView LV1ImgV = new ImageView(LV1Img);
        LV1ImgV.setFitHeight(80); // set chieu cao
        LV1ImgV.setFitWidth(80); // set chiều rong
        Button LV1Button = new Button();
        LV1Button.setStyle("-fx-background-color: transparent;");
        LV1Button.setGraphic(LV1ImgV); // set ảnh
        LV1Button.setLayoutX(110); // tọa độ X của đầu nút
        LV1Button.setLayoutY(260);

        File LoadImgLV2 = new File("src/image/Level2Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV2Img = new Image(LoadImgLV2.toURL().toString());
        ImageView LV2ImgV = new ImageView(LV2Img);
        LV2ImgV.setFitHeight(80); // set chieu cao
        LV2ImgV.setFitWidth(80); // set chiều rong
        Button LV2Button = new Button();
        LV2Button.setStyle("-fx-background-color: transparent;");
        LV2Button.setGraphic(LV2ImgV); // set ảnh
        LV2Button.setLayoutX(250); // tọa độ X của đầu nút
        LV2Button.setLayoutY(260);

        File LoadImgLV3 = new File("src/image/Level3Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV3Img = new Image(LoadImgLV3.toURL().toString());
        ImageView LV3ImgV = new ImageView(LV3Img);
        LV3ImgV.setFitHeight(80); // set chieu cao
        LV3ImgV.setFitWidth(80); // set chiều rong
        Button LV3Button = new Button();
        LV3Button.setStyle("-fx-background-color: transparent;");
        LV3Button.setGraphic(LV3ImgV); // set ảnh
        LV3Button.setLayoutX(390); // tọa độ X của đầu nút
        LV3Button.setLayoutY(260);

        File LoadImgLV4 = new File("src/image/Level4Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV4Img = new Image(LoadImgLV4.toURL().toString());
        ImageView LV4ImgV = new ImageView(LV4Img);
        LV4ImgV.setFitHeight(80); // set chieu cao
        LV4ImgV.setFitWidth(80); // set chiều rong
        Button LV4Button = new Button();
        LV4Button.setStyle("-fx-background-color: transparent;");
        LV4Button.setGraphic(LV4ImgV); // set ảnh
        LV4Button.setLayoutX(530); // tọa độ X của đầu nút
        LV4Button.setLayoutY(260);

        File LoadImgLV5 = new File("src/image/Level5Button.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image LV5Img = new Image(LoadImgLV5.toURL().toString());
        ImageView LV5ImgV = new ImageView(LV5Img);
        LV5ImgV.setFitHeight(80); // set chieu cao
        LV5ImgV.setFitWidth(80); // set chiều rong
        Button LV5Button = new Button();
        LV5Button.setStyle("-fx-background-color: transparent;");
        LV5Button.setGraphic(LV5ImgV); // set ảnh
        LV5Button.setLayoutX(320); // tọa độ X của đầu nút
        LV5Button.setLayoutY(400);

        File LoadImg4 = new File("C:\\Users\\Gold D Rophi\\Desktop\\imageGame\\MainMenuButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image MainMenuImg = new Image(LoadImg4.toURL().toString());
        ImageView MainMenuImgV = new ImageView(MainMenuImg);
        MainMenuImgV.setFitHeight(70); // set chieu cao
        MainMenuImgV.setFitWidth(70); // set chiều rong
        Button MainMenuButton = new Button();
        MainMenuButton.setStyle("-fx-background-color: transparent;");
        MainMenuButton.setGraphic(MainMenuImgV); // set ảnh
        MainMenuButton.setLayoutX(20); // tọa độ X của đầu nút
        MainMenuButton.setLayoutY(20);

        MainMenuButton.setOnAction(e -> {
            try {
                start(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        SelectLVButton.getChildren().addAll(LV1Button, LV2Button, LV3Button, LV4Button, LV5Button, MainMenuButton);
        SelectLV.getChildren().addAll(pbgView, SelectLVButton);
        Scene lvScene = new Scene(SelectLV, 720, 800);
        stage.setScene(lvScene);
    }
}
