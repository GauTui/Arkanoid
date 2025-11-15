package com.example.arkanoid.utils.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class BackgroundMusic {
    private MediaPlayer mediaPlayer;

    public BackgroundMusic(String path) {
        // Đường dẫn tuyệt đối tới file nhạc
        Media sound = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
        mediaPlayer.setVolume(0.5); // Âm lượng 0.0 → 1.0
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public void play() {
        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void pause() {
        mediaPlayer.pause();
    }
}
