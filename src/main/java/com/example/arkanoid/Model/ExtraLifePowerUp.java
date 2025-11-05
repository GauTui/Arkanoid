package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Power-up này có chức năng cộng thêm một mạng cho người chơi khi được thu thập.
 * Đây là một hiệu ứng tức thời.
 */
public class ExtraLifePowerUp extends PowerUp {

    /**
     * Constructor để tạo đối tượng power-up tại một vị trí cụ thể.
     * @param x Tọa độ X ban đầu.
     * @param y Tọa độ Y ban đầu.
     */
    public ExtraLifePowerUp(double x, double y) {
        // Gọi constructor cơ bản của lớp cha
        super(x, y);

        // Tải và gán hình ảnh cho power-up này
        try {
            // Nhớ tạo một file ảnh tên là "extra_life_powerup.png" (ví dụ: hình trái tim)
            // và đặt nó vào thư mục images nhé!
            Image image = new Image(new File("src/main/resources/com/example/arkanoid/images/ExtraLifePowerUp.png").toURI().toString());
            this.view = new ImageView(image);

            // Ép kiểu để đặt kích thước cho ảnh
            ((ImageView) this.view).setFitWidth(POWERUP_WIDTH);
            ((ImageView) this.view).setFitHeight(POWERUP_WIDTH);

            updateView(); // Cập nhật vị trí hiển thị
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh cho ExtraLifePowerUp: " + e.getMessage());
        }
    }

    /**
     * Phương thức này được gọi khi paddle thu thập được power-up.
     * Logic chính: gọi GameManager để tăng số mạng của người chơi lên 1.
     * @param gm Instance của GameManager.
     */
    @Override
    public void applyEffect(GameManager gm) {
        gm.increaseLives(1); // Gọi phương thức trong GameManager để tăng mạng
        // Gợi ý: bạn có thể chơi một âm thanh "ting" ở đây để báo hiệu người chơi đã nhận được mạng.
    }

    /**
     * Vì đây là hiệu ứng tức thời và vĩnh viễn (cho đến khi người chơi thua),
     * chúng ta không cần làm gì khi hiệu ứng "kết thúc".
     * Tuy nhiên, ta vẫn phải override phương thức này vì nó là abstract ở lớp cha.
     * @param gm Instance của GameManager.
     */
    @Override
    public void removeEffect(GameManager gm) {
        // Để trống, không cần làm gì cả.
    }
}