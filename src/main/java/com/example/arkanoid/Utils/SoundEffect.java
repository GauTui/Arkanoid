package com.example.arkanoid.Utils;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SoundEffect {
    private AudioClip clip;

    public SoundEffect(String path) throws MalformedURLException { // bắt url ko hợp lệ
        URL resource = new File(path).toURI().toURL();
        clip = new AudioClip(resource.toString());
    }

    public void play(double volume) {
        if (clip != null) {
            clip.setVolume(volume); // 0.0 → 1.0
            clip.play();
        }
    }
}
