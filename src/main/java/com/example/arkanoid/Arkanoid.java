package com.example.arkanoid;

/**
 * Đây là class chính để chạy game.
 * Nó kế thừa toàn bộ logic giao diện từ HelloApplication
 * và giải quyết lỗi "cannot find symbol" trong GameManager.
 */
public class Arkanoid extends HelloApplication {

    public static void main(String[] args) {
        // Phương thức launch() sẽ tìm và gọi đến phương thức start()
        // được kế thừa từ HelloApplication để bắt đầu game.
        launch(args);
    }
}