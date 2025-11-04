package com.example.arkanoid;

import com.example.arkanoid.Model.*;
import com.example.arkanoid.Utils.SoundEffect;
import com.example.arkanoid.Utils.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.example.arkanoid.Model.Paddle.PADDLE_HEIGHT;
import static com.example.arkanoid.Model.Paddle.PADDLE_WIDTH;
import static com.example.arkanoid.Model.Ball.*;

public class GameManager {
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 720;
    public static final int INITIAL_LIVES = 3;
    public static final int MAP_NUMBERS = 6;
    public static final int INCREASE_POINTS = 10;

    public static final int SCORE_X = 20;
    public static final int SCORE_Y = 30;

    public static final int LIVES_X = 20;
    public static final int LIVES_Y = 60;

    // Singleton GameManager
    private static GameManager instance;
    private javafx.animation.AnimationTimer gameLoop;


    private Paddle paddle;

    private List<Ball> balls;
    private List<Brick> bricks;
    private Pane gamePane;
    //danh sach cac PowerUp dang roi
    private List<PowerUp> fallingPowerups;
    //danh sach cac PowerUp da cham vao paddle va dang gay hieu ung
    private List<PowerUp> activePowerups;
    //dung de sinh ngau nhien PowerUp
    private Random random = new Random();
    //text hien thi so diem ra man hinh voi dinh nghia : "Score : " + score
    private Text scoreText;
    private Text livesText;
    private int score;
    private int lives;
    private int currentLevel;
    private Arkanoid mainApp;

    /*====Getter/setter====*/
    public List<Ball> getBalls() {
        return balls;
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Pane getGamePane() {
        return gamePane;
    }

    public void setGamePane(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }
    /*====phuong thuc====*/

    //ho tro tam dung game khi nhan nut
    public void setGameLoop(AnimationTimer gameLoop) {
        this.gameLoop = gameLoop;
    }
    public AnimationTimer getGameLoop() {
        return this.gameLoop;
    }
    //doc map
    public void loadLevel(int levelNumber) {
        //  dọn các viên gạch của màn cũ
        bricks.forEach(brick -> gamePane.getChildren().remove(brick.getView()));
        bricks.clear();

        //   đường dẫn đến file level
        String levelFile = "/com/example/arkanoid/levels/level" + levelNumber + ".txt";

        try (InputStream is = getClass().getResourceAsStream(levelFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            int brickWidth = 60;
            int brickHeight = 25;
            int currentY = 50; // tọa độ Y ban đầu cho hàng gạch đầu tiên

            String line;
            while ((line = reader.readLine()) != null) {
                int currentX = 0; // tọa độ X ban đầu cho mỗi hàng
                for (char brickType : line.toCharArray()) {
                    Brick newBrick = null;
                    //  đọc từng ký tự và tạo loại gạch tương ứng
                    switch (brickType) {
                        case '1':
                            newBrick = new NormalBrick(currentX, currentY, brickWidth, brickHeight);
                            break;
                        case '2':
                            newBrick = new StrongBrick(currentX, currentY, brickWidth, brickHeight);
                            break;
                        // sau này thêm case cho các loại gạch khác ở đây
                    }

                    if (newBrick != null) {
                        bricks.add(newBrick);
                        gamePane.getChildren().add(newBrick.getView());
                    }
                    currentX += brickWidth; // di chuyển sang phải cho viên gạch tiếp theo
                }
                currentY += brickHeight; // di chuyển xuống dưới cho hàng gạch tiếp theo
            }
        } catch (Exception e) {
            // nếu không tìm thấy file level (ví dụ: level3.txt không tồn tại),
            // có nghĩa là đã qua hết level
            System.out.println("YOU WIN! CONGRATULATIONS!");
            // có thể thêm logic hiển thị màn hình chiến thắng ở đây ok
        }
    }

    public GameManager() {
        // Khởi tạo các danh sách
        balls = new ArrayList<>();
        bricks = new ArrayList<>();
        activePowerups = new ArrayList<>();
        fallingPowerups = new ArrayList<>();

        // Khởi tạo giá trị ban đầu
        score = 0;
        lives = INITIAL_LIVES;
    }

    /**
     * Singleton Accessor
     *
     * @return 1 con trỏ GameManager chỉ vào 1 giá trị duy nhất được dùng chung cho toàn cục
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void init(Pane gamePane, Arkanoid mainApp, int LevelNumber) {
        this.gamePane = gamePane;
        this.mainApp = mainApp;

        // Khởi tạo điểm và máu
        score = 0;
        lives = INITIAL_LIVES;

        // Paddle
        paddle = new Paddle(SCREEN_WIDTH / 2.0 - PADDLE_WIDTH / 2.0, SCREEN_HEIGHT - PADDLE_HEIGHT - 15);
        gamePane.getChildren().add(paddle.getView());

        // Ball
        Ball ball = new Ball(0, 0, BALL_DX, BALL_DY);
        // Đặt lại vị trí quả bóng trên thanh paddle, node view cập nhật vị trí hiển thị.
        ball.reset(this.getPaddle());
        balls.add(ball);
        gamePane.getChildren().add(ball.getView());

        // UI text
        scoreText = new Text("Score: " + score);
        scoreText.setFont(Font.font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setX(SCORE_X);
        scoreText.setY(SCORE_Y);

        livesText = new Text("Lives: " + lives);
        livesText.setFont(Font.font("Arial", 20));
        livesText.setFill(Color.WHITE);
        livesText.setX(LIVES_X);
        livesText.setY(LIVES_Y);

        gamePane.getChildren().addAll(scoreText, livesText);

        // Load level bricks
        currentLevel = LevelNumber; // reset về level 1 khi bắt đầu game mới
        loadLevel(LevelNumber);
    }

    public void update() throws MalformedURLException {
        //  code cập nhật vị trí và va chạm

        paddle.update();
        for (Ball ball : balls) {
            ball.update();
        }
        for (PowerUp aPowerUp : fallingPowerups) {
            aPowerUp.update();
        }
        checkCollisions();

        // di chuyển, cập nhật vị trí powerup rơi
        for(PowerUp fPowerup : fallingPowerups) {
            fPowerup.update();
        }

        // nếu activatePowerup hết hiệu lực, xóa hiệu ứng powerUp, xóa khỏi danh sách.
        handleRemoveActivePowerUp();

        // xóa các viên gạch , vật phẩm đã bị phá hủy khỏi danh sách
        fallingPowerups.removeIf(pu -> !pu.isVisible() || pu.getY() > SCREEN_HEIGHT);
        bricks.removeIf(Brick::isDestroyed);

        // kiểm tra chuyển màn
        if (bricks.isEmpty()) {
            currentLevel++; // tăng level
            // Để tạm, vượt quá map tạo đc thì quay lại level đầu
            if (currentLevel > MAP_NUMBERS) {
                Platform.runLater(() -> {
                    gameLoop.stop();
                    SoundManager.stopBackgroundMusic();
                    try {
                        Stage stage = (Stage) gamePane.getScene().getWindow();
                        Pane winPane = mainApp.GameWin(stage, score);
                        winPane.setPrefSize(720,720);
                        winPane.setClip(new javafx.scene.shape.Rectangle(720, 720));
                        javafx.geometry.Point2D gamePanePos = gamePane.localToScreen(0, 0);
                        winPane.setStyle("-fx-background-color: rgba(0,0,0,0.3);");
                        Scene transparentScene = new Scene(winPane, 720, 700);
                        transparentScene.setFill(null);
                        Stage overlayStage = new Stage();
                        overlayStage.initOwner(stage);
                        overlayStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
                        overlayStage.setScene(transparentScene);
                        overlayStage.setX(gamePanePos.getX());
                        overlayStage.setY(gamePanePos.getY());
                        overlayStage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                return;
            }

            // reset bóng và thanh đỡ cho màn mới
            balls.clear();
            paddle.reset();

            // tạo lại bóng mới
            Ball newBall = new Ball(0, 0, BALL_DX, BALL_DY);
            newBall.reset(paddle);
            balls.add(newBall);
            gamePane.getChildren().add(newBall.getView());

            // tải màn chơi tiếp theo
            loadLevel(currentLevel);
            return;
        }
        // cập nhật vị trí hình ảnh trên màn hình (cập nhật view)
        paddle.update(); //có thể bỏ dòng trên
        balls.forEach(Ball::update);
        fallingPowerups.forEach(GameObject::updateView);

        //   cập nhật text
        scoreText.setText("Score: " + score);
        livesText.setText("Lives: " + lives);

    }

    // ====== KIỂM TRA VA CHẠM ======
    public void checkCollisions() throws MalformedURLException {
        for (Ball ball : balls) {
            ball.collideWithWall();
            ball.collideWithPaddle(this.getPaddle());

            // Xử lý va chạm bóng với gạch
            for (int i = 0; i < bricks.size(); i++) {
                Brick brick = bricks.get(i);
                if (brick.isDestroyed()) continue;

                if (ball.checkCollision(brick)) {
                    //xu ly va cham voi bong, xoa brick ra list va ra pane, tang diem,.
                    ball.collideWithBrick(brick);
                    score += INCREASE_POINTS;

                    // Có thể sinh power-up
                    if (random.nextDouble() < 0.2) {
                        spawnPowerUp(brick.getX(), brick.getY());
                    }

                    if (brick.isDestroyed()) {
                        gamePane.getChildren().remove(brick.getView());
                    }
                }
            }

            // Xử ly nốt nếu bóng rơi khỏi màn hình, kiểm tra máu còn lại.
            // Ông đức viết code chuyển màn hình game over khi máu về 0
            if (lives <= 0) {
                gameLoop.stop();
                Platform.runLater(() -> {
                    try {
                        Stage stage = (Stage) gamePane.getScene().getWindow();
                        Pane losePane = mainApp.GameLoseSc(stage, score,currentLevel);
                        losePane.setStyle("-fx-background-color: rgba(0,0,0,0.3);");
                        javafx.geometry.Point2D gamePanePos = gamePane.localToScreen(0, 0);
                        Scene transparentScene = new Scene(losePane, 720, SCREEN_HEIGHT);
                        transparentScene.setFill(null);
                        Stage overlayStage = new Stage();
                        overlayStage.initOwner(stage);
                        overlayStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
                        overlayStage.setScene(transparentScene);
                        overlayStage.setX(gamePanePos.getX());
                        overlayStage.setY(gamePanePos.getY());
                        overlayStage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                //mainApp.showEndGameScreen(score);
            }
        }

        // Xử lý va chạm paddle với fallingPowerUp (duyệt ngược)
        for (int i = fallingPowerups.size() - 1; i >= 0; i--) {
            PowerUp p = fallingPowerups.get(i);

            // Nếu va chạm giữa Paddle và fallingPowerUp thêm fallingPowerUp vào list activePowerups
            // Xóa fallingPowerUp chạm vào paddle khỏi danh sách hiển thị và list fallingPowerups
            if (p.getView().getBoundsInParent().intersects(paddle.getView().getBoundsInParent())) {
                handlePowerUpCollision(p);
            }
        }
    }

    public void handleRemoveActivePowerUp() {
        for (int i = activePowerups.size() - 1; i >= 0; i--) {
            PowerUp aPowerup = activePowerups.get(i);
            long elapsed = System.currentTimeMillis() - aPowerup.getActivationTime();
            long duration = aPowerup.getDuration();

            // Làm mờ dần trong suốt thời gian hiệu ứng
            double remaining = 1.0 - (double) elapsed / duration;
            aPowerup.getView().setOpacity(Math.max(0.3, remaining));

            if (elapsed > duration) {
                aPowerup.removeEffect(this);
                activePowerups.remove(i);
            }
        }
    }

    // xử lý khi fallingPowerups chạm vào paddle
    private void handlePowerUpCollision(PowerUp p) {

        p.setDy(0);
        // kiểm tra trong danh sách activatePowerup có powerup cùng loại với p không
        // Nếu có reset thời gian bắt đầu hiệu ứng của powerup có trong list activatePowerups

        // Thời gian bắt đầu hiệu ứng
        long now = System.currentTimeMillis();
        p.setActivationTime(now);
        boolean isDuplicate = false;
        for (PowerUp iAcPowerup : activePowerups) {
            if (iAcPowerup.getClass().equals(p.getClass())) {
                isDuplicate = true;
                iAcPowerup.setActivationTime(now);
                break;
            }
        }

        // Nếu không có thì thêm vào list activatePowerups, đặt hiệu ứng lên các đối tượng
        if (!isDuplicate) {
            activePowerups.add(p);
            p.applyEffect(this);
        }

        // Xóa fallingPowerUp khỏi màn hình và danh sách
        gamePane.getChildren().remove(p.getView());
        fallingPowerups.remove(p);

    }

    private void spawnPowerUp(double x, double y) {

        PowerUp newPowerUp;
        if (random.nextBoolean()) {
            newPowerUp = new ExpandPaddlePowerUp(x, y);
        } else {
            newPowerUp = new FastBallPowerUp(x, y);
        }
        fallingPowerups.add(newPowerUp);
        gamePane.getChildren().add(newPowerUp.getView());
    }

    public void loseLife() throws MalformedURLException {
        lives = lives - 1;
        SoundEffect loseLifeSound = new SoundEffect("/com/example/arkanoid/sounds/loseLife.wav");
        SoundManager.stopBackgroundMusic();
        loseLifeSound.play(1);
    }
    //reset lai trang thai game tu ban dau, chu neu khong thi lai khoai:))

    public void reset() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        if (gamePane != null) {
            gamePane.getChildren().clear();
        }
        balls.clear();
        bricks.clear();
        score = 0;
        lives = 3;
    }
}