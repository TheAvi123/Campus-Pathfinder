package model;

import java.awt.*;
import java.util.Objects;

public abstract class Shape {
    protected Color color;
    private boolean selected;
    protected int x;
    protected int y;
    protected int x2; // width for obstacle
    protected int y2; // height for obstacle

    public Shape(Color color, int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    public abstract void draw(Graphics g);

    public abstract boolean contains(Point p);

    public Color getColor() {
        return color;
    }
    public boolean isSelected() {
        return selected;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getX2() {
        return x2;
    }
    public int getY2() {
        return y2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return x == shape.x &&
                y == shape.y &&
                x2 == shape.x2 &&
                y2 == shape.y2 &&
                Objects.equals(color, shape.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, x, y, x2, y2);
    }
}
