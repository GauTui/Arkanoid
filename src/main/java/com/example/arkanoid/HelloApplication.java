package com.example.arkanoid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Button hu1 = new Button("start game");
        hu1.setLayoutX(205);
        hu1.setLayoutY(330);
        hu1.setPrefSize(230, 50);
        Button hu2 = new Button("select level");
        hu2.setPrefSize(230, 50);
        hu2.setLayoutX(205);
        hu2.setLayoutY(400);
        Button hu3 = new Button("thoat game");
        hu3.setPrefSize(230, 50);
        hu3.setLayoutX(205);
        hu3.setLayoutY(460);

        Button tutorial = new Button("huong dan");
        tutorial.setLayoutX(560);
        tutorial.setLayoutY(560);
        tutorial.setPrefSize(60, 60);

        File huhu = new File("\"C:\\Users\\Gold D Rophi\\Downloads\\huhu.png\"");
        Image anhnen = new Image(huhu.toURL().toString());


        Pane root = new Pane();
        root.getChildren().addAll(hu1, hu2, hu3, tutorial);

        Scene scene = new Scene(root, 640, 640);

        stage.setTitle("Arkanoid Game - Team 6");

        stage.setScene(scene);
        stage.show();
    }
}
