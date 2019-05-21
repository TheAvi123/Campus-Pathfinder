package model.pathfinding;

import model.Shape;

import java.awt.*;
import java.util.Objects;

public class Block extends Shape {

    private static final Color color1 = new Color(0, 255, 0, 50);
    private static final Color color2 = new Color(255, 0, 0, 50);


    boolean isBlocked;
    Point point;
    int blockRadius;

    public int gCost, hCost;
    public Block parentBlock;

    public Block (boolean isBlocked, Point p , int blockRadius){
        super(color1, p.x, p.y, blockRadius*2, blockRadius*2);
        this.isBlocked = isBlocked;
        point = p;
    }

    @Override
    public void draw(Graphics g) {
        Color save = g.getColor();
        if(!isBlocked){
            g.setColor(color1);
        } else {
            g.setColor(color2);
        }
        g.fillRect(x - blockRadius,y - blockRadius, x2, y2);
        g.setColor(Color.BLACK);
        g.drawRect(x - blockRadius,y - blockRadius, x2, y2);
        g.setColor(save);
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getfCost(){
        return gCost + hCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Block block = (Block) o;
        return Objects.equals(point, block.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), point);
    }
}
