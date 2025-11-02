package com.example.arkanoid.Model;

import com.example.arkanoid.GameManager;
import com.example.arkanoid.Utils.SoundEffect;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

import java.net.MalformedURLException;
import java.util.Random;

import static com.example.arkanoid.GameManager.SCREEN_HEIGHT;
import static com.example.arkanoid.GameManager.SCREEN_WIDTH;

public class Ball extends MovableObject {
    private boolean attached = false;
    private Paddle attachedTo = null;
// cái này để cho thêm phần anti-dead-horizontal
    // min_vy: biên dưới cho độ lớn vận tốc dọc, sau khi chạm paddle, nếu vy nhỏ hơn
    // ngưỡng đã đặt thì ta đẩy nó lên 2.0 để quỹ đạo ko bị phẳng
    //max speed để game ko mất kiểm soát
    private static final double MIN_VY = 2.0;
    private static final double MAX_SPEED = 9.5;

    public static final double LAUNCH_SPEED = 4.5;
    public static final int BALL_SIZE = 20;
    public static final double BALL_DX = 2;
    public static final double BALL_DY = -2;
//    public static final double DEFAULT_DX = BALL_DX;
    public static final double DEFAULT_DY = BALL_DY;

    private static double rand(double a, double b) {
        return ThreadLocalRandom.current().nextDouble(a, b); // [a, b)
    }

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

    public void attachTo(Paddle p) {
        attached = true;
        attachedTo = p;
        setDx(0);
        setDy(0);
        // đặt bóng ở tâm paddle, ngay phía trên
        setX(p.getX() + p.getWidth()/2.0 - getWidth()/2.0);
        setY(p.getY() - getHeight());
        updateView();
    }

    public void launch() {
        if (!attached) return;
        attached = false;
        attachedTo = null;
        // bấm space/chuột thì bóng bay thẳng lên
        setDx(0);                      // ← thẳng đứng
        setDy(-Math.abs(LAUNCH_SPEED)); // ← bay lên
        // đẩy bóng lên cao hơn 1px để tránh "dính" paddle khung đầu
        setY(getY() - 1);
        updateView();
    }

    public boolean isAttached() { return attached; }

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

        GameManager gm = GameManager.getInstance();
        //Xử lý va chạm dưới rớt xuống khỏi màn hình, ngoài ra hàm này thực hiện việc giảm máu và gameOver.
        if(!attached && this.getY() + this.getHeight() > SCREEN_HEIGHT) {
            gm.loseLife();
            this.reset(gm.getPaddle());
        }
    }

    /**
     * kiểm tra va chạm với thanh trượt paddle.
     * Ở đây chỉ kiểm tra va chạm, nếu có thì cho luôn bóng bật lên trên luôn.
     * Hướng bật phụ thuộc vào vùng bóng tác động bên trái hay phải paddle.
     * @param paddle thanh trượt
     */
    public void collideWithPaddle(Paddle paddle) throws MalformedURLException {
        // tránh trường hợp null của paddle và rectangle
        if (attached) return;

        if(paddle == null|| paddle.getView() == null) {
            return;
        }

        // Xử lý va chạm khi quả bóng đi xuống (dy>0), ở đây chỉ coi va chạm là chạm trên
        if(getDy() > 0 && checkCollision(paddle)) {
            this.setDy(-this.getDy());

            //cho dx > 1.5
            //mỗi lần va chạm sẽ tạo object mới(new Random())-> tốn cấp phát
            //sửa để tăng hiệu năng, giảm xác suất lặp, góc bật đa dạng hơn
            //double rdx = getRandomNumber(BALL_DX - 1.5, BALL_DX + 1);
            double rdx = rand(BALL_DX - 1.5, BALL_DX + 1.0);
            // Cho qua bong di chuyen sang trai hay phai (dx) dua tren diem va cham voi thanh paddle.
            // Neu va cham nua phai paddle thi ta cho bong di chuyen phai(dx<0), va nguoc lai (dx>0)

            // Diem va cham xet voi tam qua bong theo truc x
            double pointCollision = this.getX() + this.getWidth()/2.0;

            // Diem dua thanh paddle
            double midPaddle = paddle.getX() + paddle.getWidth()/2.0;

            // Neu bong nam ben trai ta dat lai van toc qua bong dx âm. TH còn lại giữ nguyên
            if(pointCollision < midPaddle) {
                rdx = -rdx;
            }

            // Đặt vận tốc dx cho bóng
            this.setDx(rdx);
            //gọi hàm để tránh bóng chạy ngang
            stabilizeAfterPaddleBounce();

            // Hiển thị âm thanh
            SoundEffect PaddleCollideSound = new SoundEffect("/com/example/arkanoid/sounds/WallPaddle.wav");
            PaddleCollideSound.play(0.5);
        }

    }
    // --- Anti-dead-horizontal sau khi nảy paddle ---
    private void stabilizeAfterPaddleBounce() {
        // ép |vy| tối thiểu
        if (Math.abs(getDy()) < MIN_VY) {
            setDy(Math.copySign(MIN_VY, getDy()));
        }
        // clamp tốc độ tổng
        double vx = getDx(), vy = getDy();
        double sp = Math.hypot(vx, vy);
        if (sp > MAX_SPEED) {
            double k = MAX_SPEED / sp;
            setDx(vx * k);
            setDy(vy * k);
        }
    }

    /**
     * kiểm tra va chạm với brick.
     * xóa luôn brick va chạm nếu thỏa mãn máu của nó về 0
     * @param brick gach
     */
    public void collideWithBrick(Brick brick) throws MalformedURLException {
        /* tránh trường hợp null của brick và rectangle brick.
        khi ta xóa đi brick thì còn lưu trong Pane*/
        if (attached) return;

        if(brick == null|| brick.getView() == null) {
            return;
        }
        // 🚫 Nếu không giao nhau thì thoát ngay
        Bounds ballB  = this.view.getBoundsInParent();
        Bounds brickB = brick.getView().getBoundsInParent();
        if (!ballB.intersects(brickB)) return;

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
//        this.setX(paddle.getX()  + paddle.getWidth()/2.0 - this.getWidth()/2.0);
//        this.setY(paddle.getY() - this.getHeight());
//        this.setDx(BALL_DX);
//        this.setDy(BALL_DY);
//        updateView();
          attachTo(paddle);   // <-- thay toàn bộ nội dung cũ bằng dòng này
    }

    /**
     * Cap nhat vi tri theo van toc, diem hien thi cua rectangle chua ball.
     */
    @Override
    public void update() {
        if (attached && attachedTo != null) {
            setX(attachedTo.getX() + attachedTo.getWidth()/2.0 - getWidth()/2.0);
            setY(attachedTo.getY() - getHeight());
            updateView();
            return; // không chạy vật lý khi đang dính
        }
        // như cũ
        setX(getX() + getDx());
        setY(getY() + getDy());
        updateView();
    }
}
