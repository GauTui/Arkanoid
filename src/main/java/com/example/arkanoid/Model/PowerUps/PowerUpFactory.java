package com.example.arkanoid.Model.PowerUps;

import java.util.Random;

/**
 * Factory Pattern để tạo các PowerUp objects.
 * Tập trung logic tạo PowerUp vào một nơi duy nhất.
 */
public class PowerUpFactory {

    private static final Random random = new Random();

    /**
     * Tạo một PowerUp cụ thể dựa trên type
     * @param type Loại PowerUp cần tạo
     * @param x Tọa độ X
     * @param y Tọa độ Y
     * @return Đối tượng PowerUp tương ứng
     */
    public static PowerUp createPowerUp(PowerUpType type, double x, double y) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case EXTRA_LIFE:
                return new ExtraLifePowerUp(x, y);

            case SPLIT_BALL:
                return new SplitBallPowerUp(x, y);

            case EXPAND_PADDLE:
                return new ExpandPaddlePowerUp(x, y);

            case FAST_BALL:
                return new FastBallPowerUp(x, y);

            case LASER_PADDLE:
                return new LaserPaddlePowerUp(x, y);

            case BOMB_BALL:
                return new BombBallPowerUp(x, y);

            default:
                System.err.println("Unknown PowerUpType: " + type);
                return null;
        }
    }

    /**
     * Random một loại PowerUp dựa trên tỷ lệ rơi đã định nghĩa.
     * Phương thức này thay thế logic random cũ trong GameManager.
     *
     * @return PowerUpType được chọn ngẫu nhiên, hoặc null nếu không rơi PowerUp
     */
    public static PowerUpType getRandomPowerUpType() {
        double chance = random.nextDouble();
        double cumulative = 0.0;

        // Duyệt qua từng loại PowerUp theo thứ tự
        for (PowerUpType type : PowerUpType.values()) {
            cumulative += type.getDropChance();
            if (chance < cumulative) {
                return type;
            }
        }

        // Nếu chance >= tổng tỷ lệ rơi → không rơi PowerUp
        return null;
    }

    /**
     * Tạo một PowerUp ngẫu nhiên tại vị trí cho trước.
     * Đây là phương thức chính mà GameManager sẽ gọi.
     *
     * @param x Tọa độ X (thường là vị trí brick bị phá)
     * @param y Tọa độ Y (thường là vị trí brick bị phá)
     * @return PowerUp object, hoặc null nếu không rơi
     */
    public static PowerUp createRandomPowerUp(double x, double y) {
        PowerUpType type = getRandomPowerUpType();

        if (type == null) {
            return null; // Không rơi PowerUp lần này
        }

        return createPowerUp(type, x, y);
    }

    /**
     * Phương thức test - tạo PowerUp với tỷ lệ rơi tùy chỉnh.
     * Dùng cho debug hoặc special events.
     *
     * @param x Tọa độ X
     * @param y Tọa độ Y
     * @param forceDropChance Tỷ lệ bắt buộc rơi (0.0 - 1.0)
     * @return PowerUp object hoặc null
     */
    public static PowerUp createRandomPowerUpWithChance(double x, double y, double forceDropChance) {
        if (random.nextDouble() > forceDropChance) {
            return null;
        }
        return createRandomPowerUp(x, y);
    }
}