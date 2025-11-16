package com.example.arkanoid.Controller.Managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HighScoreManagerTest {
    @Test
    void testLoadHighscore() {
        int newHighscore = 100;
        HighScoreManager.saveHighscore(newHighscore);
        int loadedScore = HighScoreManager.loadHighscore();
        Assertions.assertEquals(newHighscore, loadedScore, "Lưu điểm số mới phải ghi đè lên điểm số cũ.");
    }

    @Test
    void testSaveHighscore2() {
        int newHighscore = 0;
        HighScoreManager.saveHighscore(newHighscore);
        int loadedScore = HighScoreManager.loadHighscore();
        Assertions.assertEquals(newHighscore, loadedScore, "Lưu điểm số mới phải ghi đè lên điểm số cũ.");
    }
}
