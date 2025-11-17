package com.example.arkanoid.Controller.Utils; // Hãy chắc chắn package này đúng

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

/**
 * Class này quản lý việc hiển thị một hoạt ảnh nổ từ một file sprite sheet.
 * Nó sẽ tự động ẩn đi sau khi hoạt ảnh kết thúc.
 */
public class ExplosionEffect {

    private final ImageView imageView;
    private final Animation animation;

    // --- CẤU HÌNH SPRITE SHEET (ĐÃ CẬP NHẬT CHO FILE ẢNH CỦA BẠN) ---

    // Đảm bảo tên file này khớp với tên file bạn đã lưu trong thư mục images
    private static final String IMAGE_PATH = "src/main/resources/com/example/arkanoid/images/explosion_spritesheet.png";

    private static final int COLUMNS = 4;      // 4 cột trong ảnh
    private static final int COUNT = 7;        // 7 khung hình có nội dung
    private static final int FRAME_WIDTH = 240;  // Chiều rộng một khung hình (960 / 4)
    private static final int FRAME_HEIGHT = 192; // Chiều cao một khung hình (384 / 2)
    private static final int DURATION_MS = 140;  // Tổng thời gian hoạt ảnh (0.7 giây)

    /**
     * Tạo một hiệu ứng nổ mới.
     * @param centerX Tọa độ X của tâm vụ nổ.
     * @param centerY Tọa độ Y của tâm vụ nổ.
     */
    public ExplosionEffect(double centerX, double centerY) {
        // Tải ảnh sprite sheet
        Image image = new Image(new File(IMAGE_PATH).toURI().toString());
        this.imageView = new ImageView(image);

        // Đặt vị trí cho hiệu ứng (góc trên bên trái của ImageView)
        this.imageView.setX(centerX - FRAME_WIDTH / 2.0);
        this.imageView.setY(centerY - FRAME_HEIGHT / 2.0);

        // Thiết lập "viewport" - cửa sổ nhìn chỉ hiển thị khung hình đầu tiên
        this.imageView.setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));

        // Tạo đối tượng hoạt ảnh
        this.animation = new SpriteAnimation(
                imageView,
                Duration.millis(DURATION_MS),
                COUNT, COLUMNS,
                0, 0, // offset X, Y (thường là 0)
                FRAME_WIDTH, FRAME_HEIGHT
        );
    }

    /**
     * Bắt đầu chạy hoạt ảnh và thiết lập để tự ẩn sau khi kết thúc.
     */
    public void play() {
        animation.play();

        // Sau khi hoạt ảnh kết thúc, đặt ImageView thành vô hình.
        // GameManager sẽ dựa vào đây để dọn dẹp nó khỏi màn hình.
        animation.setOnFinished(event -> {
            imageView.setVisible(false);
        });
    }

    /**
     * Lấy đối tượng ImageView để có thể thêm vào Pane của game.
     * @return ImageView của hiệu ứng nổ.
     */
    public ImageView getView() {
        return imageView;
    }

    /**
     * Lớp nội (inner class) để quản lý việc chuyển đổi giữa các khung hình của sprite sheet.
     */
    private static class SpriteAnimation extends Transition {
        private final ImageView imageView;
        private final int count;
        private final int columns;
        private final int offsetX;
        private final int offsetY;
        private final int width;
        private final int height;

        public SpriteAnimation(ImageView imageView, Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height) {
            this.imageView = imageView;
            this.count = count;
            this.columns = columns;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.width = width;
            this.height = height;
            setCycleDuration(duration);
            setInterpolator(Interpolator.LINEAR); // Chạy hoạt ảnh đều, không nhanh không chậm
        }

        /**
         * Phương thức này được gọi liên tục bởi JavaFX trong suốt thời gian hoạt ảnh.
         * @param frac một giá trị từ 0.0 (bắt đầu) đến 1.0 (kết thúc).
         */
        @Override
        protected void interpolate(double frac) {
            // Tính toán chỉ số của khung hình hiện tại dựa trên tiến độ (frac)
            final int index = Math.min((int) Math.floor(frac * count), count - 1);

            // Tính toán tọa độ (x, y) của khung hình đó trong file sprite sheet
            final int x = (index % columns) * width + offsetX;
            final int y = (index / columns) * height + offsetY;

            // Di chuyển "viewport" đến đúng khung hình cần hiển thị
            imageView.setViewport(new Rectangle2D(x, y, width, height));
        }
    }
}