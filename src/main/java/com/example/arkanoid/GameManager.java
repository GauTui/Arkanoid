package com.example.arkanoid;

import com.example.arkanoid.Model.*;
import com.example.arkanoid.Utils.SoundEffect;
import com.example.arkanoid.Utils.SoundManager;
import com.example.arkanoid.Model.HighScoreManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
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

    public static final int SCORE_X = 1080-180-60/2;
    public static final int SCORE_Y = 185;
    public static final int LIVES_Y = 640;
    public static final int HIGHSCORE_X = (SCORE_Y + LIVES_Y)/2;
    // số bóng tối đa trong game
    public static final int MAX_BALLS = 10;

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
    private Text highscoreText;
    public int score;
    public int lives;
    public int highscore = HighScoreManager.loadHighscore();
    private int currentLevel;
    private Arkanoid mainApp;
    private List<LaserBeam> laserBeams = new ArrayList<>();

    // Biến để quản lý thời gian giữa các lần bắn (QUAN TRỌNG)
    private long lastLaserShotTime = 0;
    // Hằng số thời gian chờ giữa các lần bắn (300ms = 0.3 giây) (QUAN TRỌNG)
    private static final long LASER_COOLDOWN = 300;
    

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
                int LineW = line.length()*brickWidth;
                int currentX = (SCREEN_WIDTH-LineW)/2; // tọa độ X ban đầu cho mỗi hàng
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
        highscore = HighScoreManager.loadHighscore();
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
        highscore = HighScoreManager.loadHighscore();

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
        scoreText = new Text(score +"");
        scoreText.setFont(Font.font("Arial", 60));
        scoreText.setFill(Color.WHITE);
        scoreText.setX(SCORE_X);
        scoreText.setY(SCORE_Y);

        livesText = new Text(lives + "");
        livesText.setFont(Font.font("Arial", 60));
        livesText.setFill(Color.WHITE);
        livesText.setX(SCORE_X);
        livesText.setY(LIVES_Y);

        highscoreText = new Text( highscore + "");
        highscoreText.setFont(Font.font("Arial", 60));
        highscoreText.setFill(Color.WHITE);
        highscoreText.setX(HIGHSCORE_X);
        highscoreText.setY(LIVES_Y);

        gamePane.getChildren().addAll(scoreText, livesText);

        // Load level bricks
        currentLevel = LevelNumber; // reset về level 1 khi bắt đầu game mới
        loadLevel(LevelNumber);
    }

    public void update() throws MalformedURLException {
        // Check nếu còn bóng nào đang bay
        boolean anyLaunched = false;
        for (Ball ball : balls) {
            if (ball.isLaunched()) {
                anyLaunched = true;
            }
        }

        // Nếu không còn bóng nào đang bay → reset bóng trên paddle
        if (!anyLaunched) {
            for (Ball ball : balls) {
                ball.reset(paddle); // stop và đặt lại vị trí
            }
        }
        // --- Phần code dưới đây chỉ chạy KHI GAME ĐÃ BẮT ĐẦU ---

        paddle.update();
        for (Ball ball : balls) {
            ball.update();
        }
        for (PowerUp aPowerUp : fallingPowerups) {
            aPowerUp.update();
        }
        checkCollisions();

        handleLaserShooting();
        updateLaserBeams();

        //xử lý bóng rơi khỏi đáy màn hình
        handleBallFallingBottom();

        // nếu activatePowerup hết hiệu lực, xóa hiệu ứng powerUp, xóa khỏi danh sách.
        handleRemoveActivePowerUp();

        // xóa các viên gạch , vật phẩm đã bị phá hủy khỏi danh sách
        fallingPowerups.removeIf(pu -> !pu.isVisible() || pu.getY() > SCREEN_HEIGHT);
        bricks.removeIf(Brick::isDestroyed);

        // kiểm tra chuyển màn
        if (bricks.isEmpty()) {
            System.out.println("Level " + currentLevel + " cleared!");
            currentLevel++; // tăng level

            if (currentLevel > MAP_NUMBERS) {
                Platform.runLater(() -> {
                    gameLoop.stop();
                    SoundManager.stopBackgroundMusic();
                    try {
                        Stage stage = (Stage) gamePane.getScene().getWindow();
                        Pane winPane = mainApp.createGameWin(stage, score);
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
            paddle.reset();

            //màn mới chỉ nên có 1 bóng, xóa các bóng còn thừa
            for (int i = balls.size() - 1; i >= 1; i--) {
                removeBall(balls.get(i));
            }

            //xóa tất cả powerup rơi còn thừa trên màn hình
            for (PowerUp pu : fallingPowerups) {
                gamePane.getChildren().remove(pu.getView());
            }
            fallingPowerups.clear();

            //xóa tất cả powerup đang hoạt động
            for (PowerUp apu : activePowerups) {
                apu.removeEffect(this);
            }
            activePowerups.clear();

            //xóa tất cả tia laser còn hoạt động
            for (LaserBeam beam : laserBeams) {
                gamePane.getChildren().remove(beam.getView());
            }
            laserBeams.clear();

            // đặt lại vị trí quả bóng trên thanh paddle, node view cập nhật vị trí hiển thị.
            for (Ball ball : balls) {
                ball.reset(paddle);
            }

            // tải màn chơi tiếp theo
            loadLevel(currentLevel);
            return;
        }

        // cập nhật vị trí hình ảnh trên màn hình (cập nhật view)
        paddle.updateView();
        balls.forEach(Ball::updateView);
        fallingPowerups.forEach(GameObject::updateView);

        //   cập nhật text
        scoreText.setText(score + "");
        livesText.setText(lives + "");

    }

    private void handleBallFallingBottom() throws MalformedURLException {

        for (int i = balls.size() - 1; i >= 0; i--) {
            Ball b = balls.get(i);
            if (b.getY() + b.getHeight() > SCREEN_HEIGHT) {
                gamePane.getChildren().remove(b.getView());
                balls.remove(i);
            }
        }
        if (balls.isEmpty()) {
            loseLife();
            // Ball
            Ball newBall = new Ball(0, 0, BALL_DX, BALL_DY);
            // Đặt lại vị trí quả bóng trên thanh paddle, node view cập nhật vị trí hiển thị.
            newBall.reset(this.getPaddle());
            addBall(newBall);
        }
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
                    //xu ly va cham voi bong, đặt trạng thái bị phá hủy nếu máu về 0
                    ball.collideWithBrick(brick);
                    if (brick.isDestroyed()) {
                        if(brick instanceof StrongBrick){
                            score += INCREASE_POINTS * 2;
                            if(score > highscore) {
                                highscore = score;
                                HighScoreManager.saveHighscore(highscore);
                            }
                        } else {
                            score += INCREASE_POINTS;
                            if(score > highscore) {
                                highscore = score;
                                HighScoreManager.saveHighscore(highscore);
                            }
                        }
                        spawnPowerUp(brick.getX(), brick.getY());
                        gamePane.getChildren().remove(brick.getView());
                    }
                    // chỉ xử lý một va chạm gạch mỗi khung hình cho mỗi quả bóng
                    break;
                }
            }
            // Xử ly nốt nếu bóng rơi khỏi màn hình, kiểm tra máu còn lại.
            // Ông đức viết code chuyển màn hình game over khi máu về 0
            if (lives <= 0) {
                gameLoop.stop();
                Platform.runLater(() -> {
                    try {
                        Stage stage = (Stage) gamePane.getScene().getWindow();
                        Pane losePane = mainApp.createGameLose(stage, score,currentLevel);
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
        // Trong checkCollisions()

        Iterator<LaserBeam> laserIterator = laserBeams.iterator();
        while (laserIterator.hasNext()) {
            LaserBeam beam = laserIterator.next();

            // Với mỗi tia laser, ta duyệt qua danh sách gạch
            // Dùng Iterator để có thể xóa gạch một cách an toàn
            Iterator<Brick> brickIterator = bricks.iterator();
            while (brickIterator.hasNext()) {
                Brick brick = brickIterator.next();

                // Nếu tia laser va chạm với viên gạch
                if (beam.checkCollision(brick)) {
                    // phá gạch
                    brick.destroyed();

                    // Nếu gạch bị phá hủy, xử lý điểm, xóa gạch, và tạo power-up
                    if (brick.isDestroyed()) {
                        score += INCREASE_POINTS;
                        gamePane.getChildren().remove(brick.getView());
                        brickIterator.remove(); // Xóa gạch
                        spawnPowerUp(brick.getX(), brick.getY());
                    }

                    // --- PHẦN SỬA LỖI QUAN TRỌNG NHẤT ---
                    // Ngay sau khi va chạm, xóa tia laser và dừng kiểm tra
                    gamePane.getChildren().remove(beam.getView());
                    laserIterator.remove(); // Xóa tia laser

                    // Thoát khỏi vòng lặp gạch (vì tia laser đã biến mất)
                    break;
                }
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

    /*
     sinh powerup ngẫu nhiên tại vị trí (x,y) khi viên gạch bị phá hủy
     */
    private void spawnPowerUp(double x, double y) {
        // Lấy một số ngẫu nhiên từ 0.0 (bao gồm) đến 1.0 (không bao gồm)
        double chance = random.nextDouble();

        PowerUp newPowerUp = null; // Khởi tạo là null

        // --- ĐÂY LÀ NƠI CHÚNG TA ĐỊNH NGHĨA TỈ LỆ RƠI ---

        // 2% cơ hội rơi ra Extra Life (khi chance < 0.02)
        if (chance < 0.02) {
            newPowerUp = new ExtraLifePowerUp(x, y);

            // 8% cơ hội rơi ra Split Ball (khi chance >= 0.02 và < 0.10)
        } else if (chance < 0.10) {
            newPowerUp = new SplitBallPowerUp(x, y);

            // 10% cơ hội rơi ra Expand Paddle (khi chance >= 0.10 và < 0.20)
        } else if (chance < 0.20) {
            newPowerUp = new ExpandPaddlePowerUp(x, y);

            // 10% cơ hội rơi ra Fast Ball (khi chance >= 0.20 và < 0.30)
        } else if (chance < 0.30) {
            newPowerUp = new FastBallPowerUp(x, y);
        }
            // 20% cơ hội rơi ra Laser Paddle (khi chance >= 0.30 và < 0.5)
        else if (chance < 0.50) {
            newPowerUp = new LaserPaddlePowerUp(x, y);
        }

        // Nếu không rơi vào các trường hợp trên (chance >= 0.55), sẽ không có power-up nào được tạo ra.

        // Chỉ thêm power-up vào game nếu nó đã được tạo (không phải là null)
        if (newPowerUp != null) {
            fallingPowerups.add(newPowerUp);
            gamePane.getChildren().add(newPowerUp.getView());
        }
    }

    public void loseLife() throws MalformedURLException {
        lives = lives - 1;
        SoundEffect loseLifeSound = new SoundEffect("/com/example/arkanoid/sounds/loseLife.wav");
        loseLifeSound.play(1);
    }

    public void addBall(Ball ball) {
        if (ball == null) return;
        if (this.balls != null) {
            this.balls.add(ball);
        }
        if (this.gamePane != null && ball.getView() != null) {
            if (Platform.isFxApplicationThread()) {
                this.gamePane.getChildren().add(ball.getView());
            } else {
                Platform.runLater(() -> this.gamePane.getChildren().add(ball.getView()));
            }
        }
    }

    public void removeBall(Ball ball) {
        if (ball == null) return;

        if (this.balls != null) {
            this.balls.remove(ball);
        }

        if (this.gamePane != null && ball.getView() != null) {
            if (Platform.isFxApplicationThread()) {
                this.gamePane.getChildren().remove(ball.getView());
            } else {
                Platform.runLater(() -> this.gamePane.getChildren().remove(ball.getView()));
            }
        }
    }
    public void increaseLives(int amount) {
        this.lives += amount;

        // Cập nhật giao diện người dùng (UI) để hiển thị số mạng mới
        updateLivesDisplay();
        System.out.println("Mạng đã tăng lên: " + this.lives); // In ra console để kiểm tra
    }

    // Một phương thức helper để cập nhật Text hiển thị số mạng
    // Bạn cần gọi phương thức này ở hàm init() để hiển thị số mạng ban đầu
    public void updateLivesDisplay() {
        if (livesText != null) {
            livesText.setText("Lives: " + this.lives);
        }
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
        fallingPowerups.clear();
        activePowerups.clear();
        laserBeams.clear();
        score = 0;
        lives = 3;
    }
    /**
     * Xử lý logic bắn laser.
     * Kiểm tra xem paddle có power-up không và đã đủ thời gian cooldown chưa.
     */
    private void handleLaserShooting() {
        // Kiểm tra xem paddle có đang sở hữu power-up laser không
        if (paddle.getHasLaser()) {
            long currentTime = System.currentTimeMillis();
            // Kiểm tra xem đã đủ 0.3 giây kể từ lần bắn trước chưa
            if (currentTime - lastLaserShotTime > LASER_COOLDOWN) {

                // Tính toán vị trí chính giữa paddle để bắn
                double laserX = paddle.getX() + (paddle.getWidth() / 2) - (5 / 2.0); // 5 là chiều rộng của LaserBeam
                double laserY = paddle.getY();

                // Tạo một tia laser duy nhất
                LaserBeam beam = new LaserBeam(laserX, laserY);
                beam.getLaserSound().play(0.5); // Phát âm thanh bắn laser
                // Thêm tia laser vào game
                laserBeams.add(beam);
                gamePane.getChildren().add(beam.getView());

                // Cập nhật lại thời gian của lần bắn cuối cùng
                lastLaserShotTime = currentTime;
            }
        }
    }
    private void updateLaserBeams() {
        // Duyệt ngược danh sách để có thể xóa phần tử một cách an toàn
        for (int i = laserBeams.size() - 1; i >= 0; i--) {
            LaserBeam beam = laserBeams.get(i);
            beam.update(); // Di chuyển tia laser

            // Xóa tia laser nếu nó bay ra khỏi màn hình
            if (beam.getY() < 0) {
                gamePane.getChildren().remove(beam.getView());
                laserBeams.remove(i); // Xóa bằng chỉ số i
            }
        }
    }

    public void launchBall() {
        for (Ball ball : balls) {
            if (!ball.isLaunched()) {
                ball.launch();
            }
        }
    }
}