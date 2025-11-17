package com.example.arkanoid.Model.PowerUps;

/**
 * Enum định nghĩa các loại PowerUp trong game.
 * Mỗi loại có tỷ lệ rơi riêng.
 */
public enum PowerUpType {
    // Các loại PowerUp theo thứ tự từ hiếm đến phổ biến
    EXTRA_LIFE(0.02),      // 2% - Cộng thêm mạng
    SPLIT_BALL(0.08),      // 8% - Tách bóng thành 3
    EXPAND_PADDLE(0.10),   // 10% - Mở rộng paddle
    FAST_BALL(0.10),       // 10% - Tăng tốc bóng
    LASER_PADDLE(0.15),    // 15% - Paddle bắn laser
    BOMB_BALL(0.05);       // 5% - Biến bóng thành bom

    private final double dropChance;

    PowerUpType(double dropChance) {
        this.dropChance = dropChance;
    }

    /**
     * Lấy tỷ lệ rơi của PowerUp này
     * @return Tỷ lệ rơi (0.0 - 1.0)
     */
    public double getDropChance() {
        return dropChance;
    }

    /**
     * Tính tổng tỷ lệ rơi của TẤT CẢ PowerUps
     * @return Tổng tỷ lệ (dùng để random)
     */
    public static double getTotalDropChance() {
        double total = 0;
        for (PowerUpType type : values()) {
            total += type.dropChance;
        }
        return total;
    }
}
