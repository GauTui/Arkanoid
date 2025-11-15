package com.example.arkanoid.core;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationGame extends Transition {
    private final ImageView imageView;
    private final int FrameCount;
    private final int FrameHeight;
    private final int FrameWidth;
    private int lastframe = 0;

    public AnimationGame(ImageView imageView, int FrameCount, int FrameWidth, int FrameHeight, double durationMillis) {
        this.FrameWidth = FrameWidth;
        this.FrameCount = FrameCount;
        this.FrameHeight = FrameHeight;
        this.imageView = imageView;
        setCycleDuration(Duration.millis(durationMillis));
        setCycleCount(Animation.INDEFINITE);
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * FrameCount);
        if (frame != lastframe) {
            int columns = 2; // vì sprite 2x2
            int x = (frame % columns) * FrameWidth;
            int y = (frame / columns) * FrameHeight;
            imageView.setViewport(new javafx.geometry.Rectangle2D(x, y, FrameWidth, FrameHeight));
            lastframe = frame;
        }
    }
}
