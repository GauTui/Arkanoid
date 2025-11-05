package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import com.example.arkanoid.Utils.SoundEffect;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.util.Random;

import static com.example.arkanoid.GameManager.SCREEN_HEIGHT;
import static com.example.arkanoid.GameManager.SCREEN_WIDTH;

public class Ball extends MovableObject {
    public static final int BALL_SIZE = 20;
    public static final double BALL_DX = 2;
    public static final double BALL_DY = -2;

    public boolean launched = false; // trạng thái quả bóng đã được phóng chưa

    /**
     * constructor 4 tham so, (x,y) la toa do qua bong goc tren cung ben trai.
     *
     * @param x      toa do theo truc x
     * @param y      toa do theo truc y
     * @param ballDx
     * @param ballDy
     */
    public Ball(double x, double y, double ballDx, double ballDy) {
        super(x, y, BALL_SIZE, BALL_SIZE, BALL_DX, BALL_DY);

        //khởi tạo hình chữ nhật chứa ball
        Rectangle Rball = new Rectangle(BALL_SIZE, BALL_SIZE);

        Rball.setTranslateX(x);
        Rball.setTranslateY(y);

        String iball = getClass().getResource("/com/example/arkanoid/images/ball.png").toExternalForm();
        Image imagineBallUrl = new Image(iball);
        Rball.setFill(new ImagePattern(imagineBallUrl));

        //Node view khoi tao la Rball
        this.view = Rball;
    }
    // ========= GETTER & SETTER =========

    public boolean isLaunched() {
        return launched;
    }

    // ========== PHƯƠNG THỨC ==========
    /**
     * phuong thuc lay so ngau nhien trong doan [a;b]
     * @param a so thuc a
     * @param b so b
     * @return so thuc ngau nhien thuoc [a;b]
     */
    public static double getRandomNumber(double a, double b) {
        Random rand = new Random();
        // Công thức chuẩn: a + (b - a) * rand.nextDouble()
        return a + (b - a) * rand.nextDouble();
    }

    public void launch() {
        if (!launched) {
            // Thiết lập vận tốc ban đầu với góc ngẫu nhiên
            double angle = getRandomNumber(-45, 45); // Góc từ -45 đến 45 độ
            double radians = Math.toRadians(angle);
            double speed = Math.sqrt(BALL_DX * BALL_DX + BALL_DY * BALL_DY);

            this.setDx(0);
            this.setDy(-speed * Math.cos(radians)); // Luôn hướng lên trên

            launched = true;
        }
    }

    /**
     * Ham kiem tra va cham voi cac object brick, paddle, ko co powerup....
     * @param other game object
     * @return tra ve true neu va cham, ko thi false
     */
    public boolean checkCollision(GameObject other) {
        if (other == null) return false;

        // hinh chữ nhật của đối tượng ball
        double bx = this.getX();
        double by = this.getY();
        double bw = this.getWidth();
        double bh = this.getHeight();

        // hình chữ nhật của đối tượng khác
        double ox = other.getX();
        double oy = other.getY();
        double ow = other.getWidth();
        double oh = other.getHeight();

        // Kiểm tra va chạm AABB
        boolean intersects = bx < ox + ow && bx + bw > ox && by < oy + oh && by + bh > oy;
        return intersects;
    }

    /**
     * Kiểm tra va chạm với tường.
     * Truyền tham số kiểu dữ liệu GameManager để thay đổi thuộc tính số mạng do nó quản lý.
     * Đối tượng GameManager gm quản lý trò chơi
     */
    public void collideWithWall() throws MalformedURLException {
        boolean sound = false;

        //Va cham trai va phai
        if(this.getX() < 0) {
            sound = true;
            //Đảo ngược hướng di chuyển ngang
            this.setDx(-this.getDx());
            //Đặt lại ball mép trái màn hình
            this.setX(0);
        } else if(this.getX() + this.getWidth() >SCREEN_WIDTH){
            sound = true;
            this.setDx(-this.getDx());
            //Đặt ball ở mép phải màn hình
            this.setX(SCREEN_WIDTH - this.getWidth());
        }

        //Va cham tren
        if(this.getY() < 0) {
            sound = true;
            this.setDy(-this.getDy());
            this.setY(0);
        }

        //hien thi am thanh
        if(sound) {
            SoundEffect WallCollideSound = new SoundEffect("/com/example/arkanoid/sounds/WallPaddle.wav");
            WallCollideSound.play(0.5);
            System.out.println("collide with wall " + this.getDx() + " , " + this.getDy());
        }
    }

    /**
     * kiểm tra va chạm với thanh trượt paddle.
     * Ở đây chỉ kiểm tra va chạm, nếu có thì cho luôn bóng bật lên trên luôn.
     * Hướng bật phụ thuộc vào vùng bóng tác động bên trái hay phải paddle.
     * @param paddle thanh trượt
     */
public void collideWithPaddle(Paddle paddle) throws MalformedURLException {
        // Kiểm tra paddle có tồn tại không, hoặc va chạm không
        if (paddle == null || paddle.getView() == null || !checkCollision(paddle)) {
            return;
        }

        // Tính toán vị trí tâm của bóng và paddle
        double ballCenterX = this.getX() + this.getWidth() / 2.0;
        double ballCenterY = this.getY() + this.getHeight() / 2.0;
        double paddleCenterX = paddle.getX() + paddle.getWidth() / 2.0;
        double paddleCenterY = paddle.getY() + paddle.getHeight() / 2.0;

        // Tính khoảng cách tương đối
        double deltaX = ballCenterX - paddleCenterX;
        double deltaY = ballCenterY - paddleCenterY;

        // Tính tỷ lệ va chạm theo cạnh X và Y
        double ratioX = Math.abs(deltaX) / (paddle.getWidth() / 2.0 + this.getWidth() / 2.0);
        double ratioY = Math.abs(deltaY) / (paddle.getHeight() / 2.0 + this.getHeight() / 2.0);

        // Xử lý va chạm theo hướng gần nhất
        if (ratioX > ratioY) {
            // Va chạm từ bên trái hoặc phải paddle
            this.setDx(-this.getDx());
            if (deltaX > 0) {
                this.setX(paddle.getX() + paddle.getWidth());
            } else {
                this.setX(paddle.getX() - this.getWidth());
            }
        } else {
            // Va chạm từ trên hoặc dưới paddle
            this.setDy(-this.getDy());
            if (deltaY > 0) {
                this.setY(paddle.getY() + paddle.getHeight());
            } else {
                this.setY(paddle.getY() - this.getHeight());
            }
        }

        // Điều chỉnh góc nảy khi va chạm từ trên
        if (deltaY < 0) {
            double relativeIntersectX = ballCenterX - paddleCenterX;
            double normalizedIntersect = relativeIntersectX / (paddle.getWidth() / 2);
            double angle = normalizedIntersect * 30; // Góc tối đa 60 độ
            double velocity = Math.sqrt(this.getDx() * this.getDx() + this.getDy() * this.getDy());

            this.setDx(velocity * Math.sin(Math.toRadians(angle)));
            this.setDy(-velocity * Math.cos(Math.toRadians(angle)));
        }

        // Phát âm thanh va chạm
        SoundEffect paddleCollideSound = new SoundEffect("/com/example/arkanoid/sounds/WallPaddle.wav");
        paddleCollideSound.play(0.5);
        System.out.println("collide with paddle " + this.getDx() + " , " + this.getDy());
    }

    /**
     * kiểm tra va chạm với brick.
     * xóa luôn brick va chạm nếu thỏa mãn máu của nó về 0
     * @param brick gach
     */
    public void collideWithBrick(Brick brick) throws MalformedURLException {
        /* tránh trường hợp null của brick và rectangle brick.
        khi ta xóa đi brick thì còn lưu trong Pane*/
        if(brick == null|| brick.getView() == null) {
            return;
        }

        // 1. Xác định hướng va chạm (dựa trên vị trí tương đối)
        double ballCenterX = this.getX() + this.getWidth() / 2.0;
        double ballCenterY = this.getY() + this.getHeight() / 2.0;

        double brickCenterX = brick.getX() + brick.getWidth() / 2.0;
        double brickCenterY = brick.getY() + brick.getHeight() / 2.0;

        double deltaX = ballCenterX - brickCenterX;
        double deltaY = ballCenterY - brickCenterY;

        // Xác định tỷ lệ xâm nhập theo X và Y
        double ratioX = Math.abs(deltaX) / (brick.getWidth() / 2.0 + this.getWidth() / 2.0);
        double ratioY = Math.abs(deltaY) / (brick.getHeight() / 2.0 + this.getHeight() / 2.0);


        // 2. Xử lý Phản xạ và Đẩy bóng ra khỏi gạch
        if (ratioX > ratioY) {
            // Va chạm Ngang (Trái/Phải)

            // Đảo dx
            this.setDx(-this.getDx());
            // Cho ball ra khỏi brick
            if (deltaX > 0) {
                this.setDx(Math.abs(this.getDx()));// đảm bảo đúng hướng ko có thì vẫn chạy đc
                this.setX(brick.getX() + brick.getWidth());
            } else {
                this.setDx(-Math.abs(this.getDx()));// đảm bảo đúng hướng
                this.setX(brick.getX() - this.getWidth());
            }
        } else {
            // Va chạm Dọc (Trên/Dưới)

            // Đảo dy
            this.setDy(-this.getDy());
            // Cho ball ra khỏi brick
            if (deltaY > 0) {
                this.setDy(Math.abs(this.getDy()));// đảm bảo đúng hướng
                this.setY(brick.getY() + brick.getHeight());
            } else {
                this.setDy(-Math.abs(this.getDy()));// đảm bảo đúng hướng
                this.setY(brick.getY() - this.getHeight());
            }
        }

        // giảm máu gạch, phá hủy nếu máu về không bằng cách kiểm tra IsVisiable
        brick.takeHit();


        if(brick.isDestroyed()) {
            GameManager gm = GameManager.getInstance();
            gm.getGamePane().getChildren().remove(brick.getView());
        }

        //hiển thị âm thanh
        SoundEffect BrickCollideSound = new SoundEffect("/com/example/arkanoid/sounds/collision.wav");
        BrickCollideSound.play(2);
        System.out.println("collide with brick " + this.getDx() + " , " + this.getDy());
    }

    /**
     * Hàm reset lại vị trí, vận tốc quả bóng khi bóng rơi ra khỏi đáy màn hình.
     * vị trí ở giữa thanh trượt paddle.
     * vân tốc dx,dy = 0
     * @param paddle thanh trượt để lấy vị trí tâm gậy
     */
    public void reset(Paddle paddle) {
        this.setX(paddle.getX() + paddle.getWidth() / 2.0 - this.getWidth() / 2.0);
        this.setY(paddle.getY() - this.getHeight());
        this.setDx(0);
        this.setDy(0);
        launched = false;
        updateView();
    }
}
