package model.pointPathfinding;

import java.awt.*;
import java.util.Objects;

public class KeyPoint extends Point {

    private Point point;
    private double gCost, hCost;
    public KeyPoint parent;

    public KeyPoint(Point p, Point startPoint, Point endPoint){
        super(p.x, p.y);
        point = p;
        gCost = Math.sqrt(Math.pow((p.x - startPoint.x), 2) + Math.pow((p.y - startPoint.y), 2));
        hCost = Math.sqrt(Math.pow((p.x - endPoint.x), 2) + Math.pow((p.y - endPoint.y), 2));
    }

    public Point getPoint(){
        return point;
    }

    public void setgCost(double gCost) {
        this.gCost = gCost;
    }

    public double getgCost() {
        return gCost;
    }

    public void sethCost(double hCost) {
        this.hCost = hCost;
    }

    public double gethCost() {
        return hCost;
    }

    public double getfCost(){
        return gCost + hCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        KeyPoint keyPoint = (KeyPoint) o;
        return Objects.equals(point, keyPoint.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), point);
    }
}
