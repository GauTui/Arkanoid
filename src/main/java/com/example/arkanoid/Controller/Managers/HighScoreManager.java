package com.example.arkanoid.Controller.Managers;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class HighScoreManager {
    public static String HIGH_SCORE_FILE = "src/main/resources/com/example/arkanoid/highscore/highscore.txt";
    public static int loadHighscore() {
        try {
            File file = new File(HIGH_SCORE_FILE);
            Scanner scanner = new Scanner(file);

            int high = scanner.nextInt();
            scanner.close();
            return high;
        } catch (Exception e) {
            System.out.println("không mở được file để đọc");
            return 0; // nếu lỗi thì highscore mặc định = 0
        }
    }
    public static void saveHighscore(int newHighscore) {
        try {
            FileWriter writer = new FileWriter(HIGH_SCORE_FILE);
            writer.write(String.valueOf(newHighscore));
            writer.close();
        } catch (Exception e) {
            System.out.println("không mở được file để lưu");
        }
    }
}