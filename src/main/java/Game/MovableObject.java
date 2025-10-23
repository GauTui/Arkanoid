package Game;

public abstract class MovableObject extends GameObject {
    private double dx;
    private double dy;
    public MovableObject(double x; double y; int width; int height; double dx; double dy){
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }
    public double getDx(){
        return x;
    }
    public double getDy(){
        return y;
    }
    public void setDx(double dx){
        this.dx = dx;
    }
    public void setDy(double dy){
        this.dy = dy;
    }
    public void update(){
        x += dx;
        y += dy;
        updateView();
    }
}
