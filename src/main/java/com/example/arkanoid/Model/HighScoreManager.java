package com.example.arkanoid.Model;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class HighScoreManager {
    public static int loadHighscore() {
        try {
            File file = new File("src/main/resources/com/example/arkanoid/highscore/highscore.txt");
            Scanner scanner = new Scanner(file);

            int high = scanner.nextInt();
            scanner.close();
            return high;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // nếu lỗi thì highscore mặc định = 0
        }
    }
    public static void saveHighscore(int newHighscore) {
        try {
            FileWriter writer = new FileWriter("src/main/resources/com/example/arkanoid/highscore/highscore.txt");
            writer.write(String.valueOf(newHighscore));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
