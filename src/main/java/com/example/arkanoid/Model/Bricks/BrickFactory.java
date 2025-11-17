package com.example.arkanoid.Model.Bricks;

public class BrickFactory {

    private static final int BRICK_WIDTH = 60;
    private static final int BRICK_HEIGHT = 25;

    /**
     * Tạo một đối tượng Brick dựa trên mã ký tự.
     * @param brickType Ký tự từ file level ('1', '2', v.v.)
     * @param x Tọa độ X
     * @param y Tọa độ Y
     * @return Một đối tượng Brick, hoặc null nếu ký tự không hợp lệ.
     */
    public Brick createBrick(char brickType, double x, double y) {
        switch (brickType) {
            case '1':
                return new NormalBrick(x, y, BRICK_WIDTH, BRICK_HEIGHT);
            case '2':
                return new StrongBrick(x, y, BRICK_WIDTH, BRICK_HEIGHT);
            // Thêm các loại gạch khác (case '3', '4'...) ở đây hehe
            default:
                return null;
        }
    }
}
