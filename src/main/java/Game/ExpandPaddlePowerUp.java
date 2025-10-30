package Game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Nới rộng Paddle */
public class ExpandPaddlePowerUp extends PowerUp {
    private static final int EXTRA_WIDTH = 60;   // số px cộng thêm
    private Integer originalWidth = null;        // lưu để hoàn tác

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y); // rơi thẳng với dy = POWERUP_GRAVITY
        // skin riêng (màu xanh lá để phân biệt)
        this.view = new Rectangle(POWERUP_WIDTH, POWERUP_HEIGHT, Color.LIME);
        updateView();
    }

    @Override
    public void applyEffect(GameManager gm) {
        if (isActive) return; // tránh áp lặp
        isActive = true;

        Paddle pad = gm.getPaddle();
        // lưu kích thước cũ 1 lần
        if (originalWidth == null) originalWidth = pad.getWidth();

        pad.setWidth(pad.getWidth() + EXTRA_WIDTH);
        pad.updateView(); // đồng bộ hiển thị (nếu Paddle tự cập nhật width cho Rectangle thì vẫn an toàn)
    }

    @Override
    public void removeEffect(GameManager gm) {
        if (!isActive) return;
        isActive = false;

        if (originalWidth != null) {
            Paddle pad = gm.getPaddle();
            pad.setWidth(originalWidth);
            pad.updateView();
        }
    }
}
