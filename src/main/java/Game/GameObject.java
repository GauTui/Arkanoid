package Game;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public abstract class GameObject {
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected boolean isVisiable;
    protected Node view;

    public GameObject(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisiable = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisiable() {
        return isVisiable;
    }

    public Node getView() {
        return view;
    }

    public Bounds getBounds() {
        return view.getBoundsInParent();
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setVisiable(boolean visiable) {
        isVisiable = visiable;
        view.setVisible(visiable);
    }

    public void updateView() {
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

}











