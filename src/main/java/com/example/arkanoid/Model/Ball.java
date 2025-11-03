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

    /**
     * constructor 4 tham so, (x,y) la toa do qua bong goc tren cung ben trai.
     * @param x toa do theo truc x
     * @param y toa do theo truc y
     * @param dx van toc ban dau dx
     * @param dy van toc ban dau dy
     */
    public Ball(double x, double y, double dx, double dy) {
        super(x, y, BALL_SIZE, BALL_SIZE, dx, dy);

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

    /**
     * Ham kiem tra va cham voi cac object brick, paddle, ko co powerup....
     * @param other game object
     * @return tra ve true neu va cham, ko thi false
     */
    public boolean checkCollision(GameObject other) {
        if (other == null || other.getView() == null) return false;
        // Lấy ranh giới hiển thị thực tế của bóng : viền của node chứa bóng
        Bounds ballBounds = this.view.getBoundsInParent();

        if (other instanceof Brick) {
            if (ballBounds.intersects(other.getView().getBoundsInParent())) {
                return true;
            }
        }

        //kiem tra va cham voi paddle
        if (other instanceof Paddle) {
            if (ballBounds.intersects(other.getView().getBoundsInParent())) {
                return true;
            }
        }

        return false;
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
        }

    }

    /**
     * kiểm tra va chạm với thanh trượt paddle.
     * Ở đây chỉ kiểm tra va chạm, nếu có thì cho luôn bóng bật lên trên luôn.
     * Hướng bật phụ thuộc vào vùng bóng tác động bên trái hay phải paddle.
     * @param paddle thanh trượt
     */
public void collideWithPaddle(Paddle paddle) throws MalformedURLException {
        // Kiểm tra paddle có tồn tại không
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
            double angle = normalizedIntersect * 60; // Góc tối đa 60 độ
            double velocity = Math.sqrt(this.getDx() * this.getDx() + this.getDy() * this.getDy());

            this.setDx(velocity * Math.sin(Math.toRadians(angle)));
            this.setDy(-velocity * Math.cos(Math.toRadians(angle)));
        }

        // Phát âm thanh va chạm
        SoundEffect paddleCollideSound = new SoundEffect("/com/example/arkanoid/sounds/WallPaddle.wav");
        paddleCollideSound.play(0.5);
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
    }

    /**
     * Hàm cập nhật điểm hiện thị node view chứa quả bóng.
     * điểm hiện thị cập nhật thông qua thuộc tính x, y do class quản lý
     */
    public void updateView() {
        // hiện thị tại tọa độ theo kiểu (rectangle ball). getX() + this.getX()
        // nghĩa là di chuyển tại tọa độ gốc x và cộng thêm 1 đoạn this.getX()
        this.view.setTranslateX(this.getX());
        this.view.setTranslateY(this.getY());
    }

    /**
     * Hàm reset lại vị trí, vận tốc quả bóng khi bóng rơi ra khỏi đáy màn hình.
     * vị trí ở giữa thanh trượt paddle.
     * @param paddle thanh trượt để lấy vị trí tâm gậy
     */
    public void reset(Paddle paddle) {
        this.setX(paddle.getX()  + paddle.getWidth()/2.0 - this.getWidth()/2.0);
        this.setY(paddle.getY() - this.getHeight()-1);
        this.setDx(BALL_DX);
        this.setDy(BALL_DY);
        updateView();
    }

    /**
     * Cap nhat vi tri theo van toc, diem hien thi cua rectangle chua ball.
     */
    @Override
    public void update() {
        setX(getX() + getDx());
        setY(getY() + getDy());
        updateView();
    }
}
