package com.example.arkanoid.Controller.Utils;

import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
// class nay de them hieu ung khi di chuot vao trong cac button(ImageView da tao)

public class HoverEffect {

    // Hover effect chung
    public static void addHoverEffect(ImageView btn) {
        btn.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });
        btn.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
}
