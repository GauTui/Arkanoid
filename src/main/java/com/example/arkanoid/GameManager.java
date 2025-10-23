package com.example.arkanoid;

import com.example.arkanoid.Brick.Brick;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.example.arkanoid.Ball.*;

public class GameManager {
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 800;
    public static final int INITIAL_LIVES = 3;
    public static final int MAP_NUMBERS = 5;
    public static final int INCREASE_POINTS = 10;

    public static final int SCORE_X = 640;
    public static final int SCORE_Y = 10;

    public static final int LIVES_X = 10;
    public static final int LIVES_Y = 720;

    // Singleton GameManager
    private static GameManager instance;

    private Paddle paddle;
    private List<Ball> balls;
    private List<Brick> bricks;
    private Pane gamePane;
    //danh sach cac PowerUp co the sinh ra
    private List<PowerUp> acPowerUp;
    //danh sach cac PowerUp dang hoat dong trong chuong trinh
    private List<PowerUp> activePowerUp;
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
    public Paddle getPaddle() {
        return paddle;
    }
    public List<Ball> getBalls() { return balls; }

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



    public GameManager() {
        // Khởi tạo các danh sách
        balls = new ArrayList<>();
        bricks = new ArrayList<>();
        acPowerUp = new ArrayList<>();
        activePowerUp = new ArrayList<>();

        // Khởi tạo giá trị ban đầu
        score = 0;
        lives = INITIAL_LIVES;

    }

    /**
     * Singleton Accessor
     * @return 1 con trỏ GameManager chỉ vào 1 giá trị duy nhất được dùng chung cho toàn cục
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void init(Pane gamePane, Arkanoid mainApp) {
        this.gamePane = gamePane;
        this.mainApp = mainApp;

        // Paddle
        paddle = new Paddle(SCREEN_WIDTH/2 - PADDLE_WIDTH/2, SCREEN_HEIGHT - PADDLE_HEIGHT - 15,
                            PADDLE_WIDTH, PADDLE_HEIGHT);
        gamePane.getChildren().add(paddle.getView());

        // Ball
        Ball ball = new Ball(0,0, BALL_DX, BALL_DY);
        // Đặt lại vị trí quả bóng trên thanh paddle, node view cập nhật vị trí hiển thị.
        ball.reset(getPaddle());
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
        currentLevel = 1; // reset về level 1 khi bắt đầu game mới
        loadLevel(currentLevel);
    }
    public void loadLevel(int levelNumber) {
        //  dọn các viên gạch của màn cũ
        bricks.forEach(brick -> gamePane.getChildren().remove(brick.getView()));
        bricks.clear();

        //   đường dẫn đến file level
        String levelFile = "/levels/level" + levelNumber + ".txt";

        try (InputStream is = getClass().getResourceAsStream(levelFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            int brickWidth = 60;
            int brickHeight = 20;
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
                    currentX += brickWidth ; // di chuyển sang phải cho viên gạch tiếp theo
                }
                currentY += brickHeight ; // di chuyển xuống dưới cho hàng gạch tiếp theo
            }
        } catch (Exception e) {
            // nếu không tìm thấy file level (ví dụ: level3.txt không tồn tại),
            // có nghĩa là đã qua hết level
            System.out.println("YOU WIN! CONGRATULATIONS!");
            // có thể thêm logic hiển thị màn hình chiến thắng ở đây ok
        }
    }

    public void update() {
        //  code cập nhật vị trí và va chạm
           paddle.update();
        for (Ball ball : balls) {
            ball.update();
        }
        for (PowerUp aPowerUp : activePowerUp) {
            aPowerUp.update();
        }
        checkCollisions();


        // xóa các viên gạch , vật phẩm đã bị phá hủy khỏi danh sách
        activePowerUp.removeIf(pu -> !pu.isVisible() || pu.getY() > SCREEN_HEIGHT);
        bricks.removeIf(Brick::isDestroyed);

        // kiểm tra chuyển màn
        if (bricks.isEmpty()) {
            System.out.println("Level " + currentLevel + " cleared!");
            currentLevel++; // tăng level

            // reset bóng và thanh đỡ cho màn mới
            paddle.reset(instance);
            for (Ball ball : balls) {
                ball.reset(paddle);
            }

            loadLevel(currentLevel);// tải màn chơi tiếp theo
            return;
        }
        // cập nhật vị trí hình ảnh trên màn hình (cập nhật view)
        paddle.updateView();
        balls.forEach(GameObject::updateView);
        activePowerUp.forEach(GameObject::updateView);

        //   cập nhật text
        scoreText.setText("Score: " + score);
        livesText.setText("Lives: " + lives);


    }

    private void spawnPowerUp(double x, double y) {

            PowerUp newPowerUp;
            if (random.nextBoolean()) {
                newPowerUp = new ExpandPaddlePowerUp(x, y);
            } else {
                newPowerUp = new FastBallPowerUp(x, y);
            }
            activePowerUp.add(newPowerUp);
            gamePane.getChildren().add(newPowerUp.getView());
        }


    public void loseLife() {
        lives = lives - 1;

    }
}
