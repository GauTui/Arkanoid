package com.example.arkanoid.model.powerup;

import com.example.arkanoid.core.GameManager;
import com.example.arkanoid.model.Paddle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/** Nới rộng Paddle */
public class ExpandPaddlePowerUp extends PowerUp {
    private static final int EXTRA_WIDTH = 60;   // số px cộng thêm
    private Integer originalWidth = null;        // lưu để hoàn tác

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y); // rơi thẳng với dy = POWERUP_GRAVITY

        // Đặt ảnh powerup
        Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/ExpandPaddlePowerUp.png").toURI().toString());
        this.view = new ImageView(image);

        // Ép kiểu để đặt kích thước cho ảnh
        ((ImageView) this.view).setFitWidth(POWERUP_WIDTH);
        ((ImageView) this.view).setFitHeight(POWERUP_WIDTH);

        updateView();
    }

    @Override
    public void applyEffect(GameManager gm) {
        if (isActive()) return; // tránh áp lặp
        setActive(true);

        Paddle pad = gm.getPaddle();
        // lưu kích thước cũ 1 lần
        if (originalWidth == null) originalWidth = pad.getWidth();

        pad.setWidth(pad.getWidth() + EXTRA_WIDTH);
        pad.updateView(); // đồng bộ hiển thị (nếu Paddle tự cập nhật width cho Rectangle thì vẫn an toàn)
        //cập nhật ảnh paddle
        try {
            // Ép kiểu để đặt kích thước cho ảnh
            ((ImageView) pad.getView()).setFitWidth(pad.getWidth());
        } catch (Exception e) {
            System.err.println("Không thể mở rộng độ rộng ảnh paddle: " + e.getMessage());
        }
    }


    @Override
    public void removeEffect(GameManager gm) {
        if (!isActive()) return;
        setActive(false);

        if (originalWidth != null) {
            Paddle pad = gm.getPaddle();
            pad.setWidth(originalWidth);
            pad.updateView();
            //cập nhật ảnh paddle
            try {
                // Ép kiểu để đặt kích thước cho ảnh
                ((ImageView) pad.getView()).setFitWidth(pad.getWidth());
            } catch (Exception e) {
                System.err.println("Không thể thu hẹp độ rộng ảnh paddle: " + e.getMessage());
            }
        }
    }
}