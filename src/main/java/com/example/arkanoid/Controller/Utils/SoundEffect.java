package com.example.arkanoid.Controller.Utils;

import javafx.scene.media.AudioClip;

public class SoundEffect {
    private AudioClip clip;

    public SoundEffect(String resourcePath) {
        try {
            // resourcePath ví dụ: "/com/example/arkanoid/sounds/WallPaddle.wav"
            var url = getClass().getResource(resourcePath);
            if (url == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            clip = new AudioClip(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
            clip = null;
        }
    }

    public void play(double volume) {
        if (clip != null) {
            volume = Math.max(0, Math.min(1, volume));
            clip.setVolume(volume);
            clip.play();
        } else {
            System.err.println("AudioClip chưa được khởi tạo!");
        }
    }

    public void stop() {
        if (clip != null) clip.stop();
    }
}
