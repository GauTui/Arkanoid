package com.example.arkanoid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import java.io.IOException;


/**
 * cái này là để chạy start game nhé ae!!!!!
 */

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //set button 1 va tuong tu voi cac button con lai nhe!!!
        File LoadImg = new File("C:\\Users\\Gold D Rophi\\Downloads\\StartGameButton.png"); // ở đây sẽ thêm địa chỉ của ảnh muốn render ra khi mà vẽ
        Image StartImg = new Image(LoadImg.toURL().toString());
        ImageView StartImgV= new ImageView(StartImg);
        StartImgV.setFitHeight(50); // set chieu cao
        StartImgV.setFitWidth(230); // set chiều rong
        Button StartButton = new Button();
        StartButton.setGraphic(StartImgV); // set ảnh
        StartButton.setLayoutX(205); // tọa độ X của đầu nút
        StartButton.setLayoutY(330); // tọa độ Y của đầu nút



        Button hu2 = new Button("hướng dẫn");
        hu2.setPrefSize(230, 50);
        hu2.setLayoutX(205);
        hu2.setLayoutY(400);


        Button hu3 = new Button("thoát game");
        hu3.setPrefSize(230, 50);
        hu3.setLayoutX(205);
        hu3.setLayoutY(470);


        //tạo file để load ảnh nền
        File huhu = new File("C:\\Users\\Gold D Rophi\\Downloads\\nenStartGame.png"); //ảnh này là nền nhé, anh em chỉnh thành ảnh gì cũng được

        //tạo ảnh nền cho phần start game
        Image anhnen = new Image(huhu.toURL().toString());
        ImageView bgView = new ImageView (anhnen);
        //set độ dài rộng của ảnh nền
        bgView.setFitWidth(640);
        bgView.setFitHeight(600);
        //dòng này để chọn xem có bóp méo ảnh để lấy đúng tỷ lệ hay không!!
        bgView.setPreserveRatio(false);
        //

        StackPane root = new StackPane();
        Pane bt = new Pane();
        //thêm các nút button làm con của bt
        bt.getChildren().addAll(StartButton, hu2, hu3);
        //thêm bt làm con của root để chỉ cần chạy root
        root.getChildren().addAll(bgView, bt);

        //load khung hình start game với từng tỷ lệ
        Scene scene = new Scene(root, 640, 640);
        // tiêu đề của game
        stage.setTitle("Arkanoid Game - Team 6");

        stage.setScene(scene);
        stage.show();
    }
}
