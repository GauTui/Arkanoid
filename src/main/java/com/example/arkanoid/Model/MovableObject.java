package com.example.arkanoid.Model;

public abstract class MovableObject extends GameObject {
    protected double dx;
    protected double dy;
    public MovableObject(double x, double y, int width, int height, double dx, double dy){
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }
    public double getDx(){
        return dx;
    }
    public double getDy(){
        return dy;
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
