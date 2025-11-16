package com.example.arkanoid.Controller.Utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class BackgroundMusic {
    private static MediaPlayer bgmPlayer;
    private static String currentBgmFile = null;

    public static void playBackgroundMusic(String filePath) {
        currentBgmFile = filePath;
        if (bgmPlayer != null) {
            bgmPlayer.stop(); // dừng nhạc cũ nếu có
        }

        Media media = new Media(new File(filePath).toURI().toString());
        bgmPlayer = new MediaPlayer(media);
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE); // lặp vô hạn
        bgmPlayer.setVolume(0.3); // âm lượng 30%
        bgmPlayer.play();
    }

    public static void stopBackgroundMusic() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
        }
    }
    public static void pauseMusic() {
        if (bgmPlayer != null) {
            bgmPlayer.pause();
        }
    }
    public static void resumeMusic() {
        if(bgmPlayer != null) {
            bgmPlayer.play();
        }
    }
    public static String getCurrentBgmFile() {
        return currentBgmFile;
    }
}
