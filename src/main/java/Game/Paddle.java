package Game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends MovableObject{

    private PowerUp currentPowerUp;
    private int initialWidth;
    public static final int PADDLE_WIDTH = 160;
    public static final int PADDLE_HEIGHT = 40;
    private static final double PADDLE_SPEED = 8.0;

    public Paddle(double x; double y;){
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT, 0, 0);
        this.initialWidth = PADDLE_WIDTH;
        this.currentPowerUp = null; // Ban đầu không có power-up nào

        // Khởi tạo giao diện
        Rectangle paddleShape = new Rectangle(width, height);
        paddleShape.setFill(Color.AQUAMARINE);
        this.view = paddleShape;
        updateView();
    }
    public PowerUp getCurrentPowerUp(){
        return currentPowerUp;
    }

    public void setCurrentPowerUp(PowerUp currentPowerup){
        this.currentPowerUp = currentPowerup;
    }
    public void applyPowerUp(PowerUp p, GameManager gm) {
        // luu powerup moi
        this.currentPowerUp = p;
        this.currentPowerUp.applyEffect(gm);
    }
    public int getInitialWidth(){
        return initialWidth;
    }
    public void setInitialWidth(int initialWidth){
        this.initialWidth = initialWidth;
    }
    public void setWidth(int width){
        super.setWidth(width); // Cập nhật thuộc tính width của GameObject
        if (view instanceof Rectangle) {
            ((Rectangle) view).setWidth(width); // Cập nhật chiều rộng của hình chữ nhật
        }
    }

    public void moveLeft(){
        setDx(-PADDLE_SPEED);
    }
    public void moveRight(){
        setDx(PADDLE_SPEED);
    }

    public void stopMove(){
        setDx(0);
    }
    //đưa paddle về trạng thái ban đầu
    public void update(double screenWidth) {
        super.update();

        // Giới hạn paddle không đi ra khỏi màn hình
        if (getX() < 0) {
            setX(0);
        }
        if (getX() + getWidth() > screenWidth) {
            setX(screenWidth - getWidth());
        }
        updateView();
    }
    public void reset(GameManager gm){
        setX(240.0);
        setY(540.0);

        // Đặt lại kích thước và dừng di chuyển
        setWidth(this.initialWidth);
        stopMove();

        // gỡ powerup
        if (currentPowerUp != null) {
            currentPowerUp.removeEffect(gm); //bao gio co gamemanager thi dung lenh nay
            currentPowerUp = null;
        }
        updateView();
    }
}
