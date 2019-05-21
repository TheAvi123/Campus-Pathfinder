package model;

import java.awt.*;
import java.awt.geom.Line2D;

public class Path extends Shape{

    private static final Color color = Color.RED;

    public Path(int x1, int y1, int x2, int y2) {
        super(color,x1,y1,x2,y2);
        this.x = x1;
        this.y = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics g) {
        Color save = g.getColor();
        g.setColor(color);
        g.drawLine(x,y,x2,y2);
        g.setColor(save);
    }

    @Override
    public boolean contains(Point p) {return false;}

    public boolean intersects(Obstacle e) {
        Line2D line = new Line2D.Float(x,y,x2,y2);
        return line.intersects(e.x,e.y,e.x2,e.y2);
    }
}
