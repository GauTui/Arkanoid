package com.example.arkanoid.Utils;

import com.example.arkanoid.GameManager;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


//class nay de hien thi ra cac chi so cua game
// the hien cho nguoi choi biet thong tin ve score. highscore
public class GameHUD {
    private GameManager gm;
    private Label score;
    private Label lives;
    //private Label highscore;

    public GameHUD(Pane root,GameManager gm) {
        this.gm = gm;
        score = new Label();
        score.setFont(Font.font("Arial", 24));
        score.setTextFill(Color.WHITE);
        score.setLayoutX(820);
        score.setLayoutY(40);

        lives = new Label();
        lives.setFont(Font.font("Arial", 24));
        lives.setTextFill(Color.WHITE);
        lives.setLayoutX(820);
        lives.setLayoutY(80);
        /*
        highscore = new Label();
        highscore.setFont(Font.font("Arial", 24));
        highscore.setTextFill(Color.WHITE);
        highscore.setLayoutX(820);
        highscore.setLayoutY(120);

         */

        root.getChildren().addAll(score, lives);
    }
    public void render() {
        score.setText("Score: " + gm.score);
        lives.setText("Lives: " + gm.lives);
        //highscore.setText("High Score: " + gm.highscore);
    }
}
