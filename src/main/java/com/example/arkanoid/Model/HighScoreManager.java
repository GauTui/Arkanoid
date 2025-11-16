package com.example.arkanoid.Model;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class HighScoreManager {

    private static final String FILE_PATH = "highscore.txt";

    public static int loadHighscore() {
        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return 0;   // file chưa tồn tại, highscore = 0
            }

            Scanner scanner = new Scanner(file);
            int high = scanner.nextInt();
            scanner.close();
            return high;

        } catch (Exception e) {
            return 0; // lỗi thì trả về 0
        }
    }

    public static void saveHighscore(int newHighscore) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write(String.valueOf(newHighscore));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println("Lỗi khi ghi vào file highscore.txt");
        }
    }
}
