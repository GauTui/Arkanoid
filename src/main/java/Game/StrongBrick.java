package Game;

import javafx.scene.paint.Color;

public class StrongBrick extends Brick {
    public StrongBrick(double x, double y, int width, int height, int hitPoints) {
        super(x,y,width,height,hitPoints);
        //sửa màu
        this.view.setFill(Color.GRAY);
    }

    @Override
    public void takeHit() {
        hitPoints--;
        // Đặt lại màu gạch cho phù hợp với số máu của nó
        if(hitPoints == 2) {
            this.view.setFill(Color.YELLOW);
        } else if(hitPoints == 1) {
            this.view.setFill(Color.GREEN);
        }
        else if(hitPoints <= 0 ) {
            isDestroyed = true;
        }
    }


}
