package com.example.arkanoid;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Ball extends MovableObject{
    public static final int BALL_SIZE = 20;
    public static final double BALL_DX = 3;
    public static final double BALL_DY = -3;

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
     * @param gm đối tượng GameManager gm quản lý trò chơi
     */
    public void collideWithWall(GameManager gm) {
        //Va cham trai va phai
        if(this.getX() < 0) {
            //Đảo ngược hướng di chuyển ngang
            this.setdx(-this.getdx());
            //Đặt lại ball mép trái màn hình
            this.setX(0);
        } else if(this.getX()>SCREEN_WIDTH){
            this.setdx(-this.getdx());
            //Đặt ball ở mép phải màn hình
            this.setX(SCREEN_WIDTH - this.getWidth());
        }

        //Va cham tren
        if(this.getY() < 0) {
            this.setdy(-this.getdy());
            this.setY(0);
        }
        //Xử lý va chạm dưới rớt xuống khỏi màn hình, ngoài ra hàm này thực hiện việc giảm máu và gameOver.
        else if(this.getY() + this.getHeight() > SCREEN_HEIGHT) {
            gm.loseLife();
            this.reset();
        }
    }

    /**
     * kiểm tra va chạm với thanh trượt paddle.
     * Ở đây chỉ kiểm tra va chạm, nếu có thì cho luôn bóng bật lên trên luôn.
     * Hướng bật phụ thuộc vào vùng bóng tác động bên trái hay phải paddle.
     * @param paddle thanh trượt
     */
    public void collideWithPaddle(Paddle paddle) {
        // tránh trường hợp null của paddle và rectangle
        if(paddle == null|| paddle.getView() == null) {
            return;
        }

        // Xử lý va chạm khi quả bóng đi xuống (dy>0), ở đây chỉ coi va chạm là chạm trên
        if(checkCollision(paddle)) {
            this.setdy(-this.getdy());

            //cho dx > 1.5
            int rdx = getRandomNumber(BALL_DX - 1.5, BALL_DX + 1);
            // Cho qua bong di chuyen sang trai hay phai (dx) dua tren diem va cham voi thanh paddle.
            // Neu va cham nua phai paddle thi ta cho bong di chuyen phai(dx<0), va nguoc lai (dx>0)

            // Diem va cham xet voi tam qua bong theo truc x
            double pointCollision = this.getX() + this.getWidth()/2;

            // Diem dua thanh paddle
            double midPaddle = paddle.getX() + paddle.getWidth()/2;

            // Neu bong nam ben trai ta dat lai van toc qua bong dx âm. TH còn lại giữ nguyên
            if(pointCollision < midPaddle) {
                rdx = -rdx;
            }

            // Đặt vận tốc dx cho bóng
            this.setdx(rdx);
        }

    }

    public void collideWithBrick(Brick brick) {
        /* tránh trường hợp null của brick và rectangle brick.
        khi ta xóa đi brick thì còn lưu trong Pane*/
        if(brick == null|| brick.getView() == null) {
            return;
        }

        // 1. Xác định hướng va chạm (dựa trên vị trí tương đối)
        double ballCenterX = this.getX() + this.getWidth() / 2;
        double ballCenterY = this.getY() + this.getHeight() / 2;

        double brickCenterX = brick.getX() + brick.getWidth() / 2;
        double brickCenterY = brick.getY() + brick.getHeight() / 2;

        double deltaX = ballCenterX - brickCenterX;
        double deltaY = ballCenterY - brickCenterY;

        // Xác định tỷ lệ xâm nhập theo X và Y
        double ratioX = Math.abs(deltaX) / (brick.getWidth() / 2 + this.getWidth() / 2);
        double ratioY = Math.abs(deltaY) / (brick.getHeight() / 2 + this.getHeight() / 2);

        // 2. Xử lý Phản xạ và Đẩy bóng ra khỏi gạch
        if (ratioX > ratioY) {
            // Va chạm Ngang (Trái/Phải)

            // Đảo dx
            this.setdx(-this.getdx());
            // Cho ball ra khỏi brick
            if (deltaX > 0) {
                this.setX(brick.getX() + brick.getWidth());
            } else {
                this.setX(brick.getX() - this.getWidth());
            }
        } else {
            // Va chạm Dọc (Trên/Dưới)

            // Đảo dy
            this.setdy(-this.getdy());
            // Cho ball ra khỏi brick
            if (deltaY > 0) {
                this.setY(brick.getY() + brick.getHeight());
            } else {
                this.setY(brick.getY() - this.getHeight());
            }
        }

        // 3. Phá hủy Gạch
        brick.setIsVisiable(false);

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
        this.setX(paddle.getX() + paddle.getWidth()/2 - this.getWidth()/2);
        this.setY(paddle.getY() - this.getHeight());
        this.setdx(BALL_DX);
        this.setdy(BALL_DY);
    }
}
