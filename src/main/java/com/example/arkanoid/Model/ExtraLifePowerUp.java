package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import javafx.scene.paint.Color;      // Import Color
import javafx.scene.shape.Rectangle;  // SỬA: Import Rectangle thay vì Circle

/**
 * Power-up này có chức năng cộng thêm một mạng cho người chơi khi được thu thập.
 * Đây là một hiệu ứng tức thời.
 * Nó được hiển thị bằng một hình vuông màu tím.
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

        // --- SỬA LỖI: Chuyển từ Circle sang Rectangle (hình chữ nhật/vuông) ---

        // Kích thước cạnh của hình vuông (kiểu int để tương thích)
        int size = 40;

        // Tạo một đối tượng Rectangle mới
        // Constructor này nhận vào tọa độ (x, y), chiều rộng và chiều cao.
        // Ta có thể bỏ qua x, y ở đây vì updateView() sẽ đặt lại vị trí.
        Rectangle powerUpShape = new Rectangle(size, size);

        // Tô màu tím cho hình vuông
        powerUpShape.setFill(Color.PURPLE);

        // (Tùy chọn) Thêm viền màu trắng cho đẹp mắt
        powerUpShape.setStroke(Color.WHITE);
        powerUpShape.setStrokeWidth(2);

        // Gán hình vuông này làm "view" (hình ảnh đại diện) của power-up
        this.view = powerUpShape;

        // Cập nhật lại chiều rộng và chiều cao của GameObject
        // Vì 'size' là int, nên sẽ không còn lỗi "lossy conversion"
        this.width = size;
        this.height = size;

        // Gọi phương thức của lớp cha để đặt vị trí hiển thị của hình vuông
        updateView();
    }

    /**
     * Phương thức này được gọi khi paddle thu thập được power-up.
     * @param gm Instance của GameManager.
     */
    @Override
    public void applyEffect(GameManager gm) {
        gm.increaseLives(1);
    }

    /**
     * Vì đây là hiệu ứng tức thời, phương thức này không cần làm gì cả.
     * @param gm Instance của GameManager.
     */
    @Override
    public void removeEffect(GameManager gm) {
        // Để trống.
    }
}