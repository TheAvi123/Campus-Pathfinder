package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Drawing extends JPanel {

    private ArrayList<Shape> shapes;
    private ArrayList<Shape> gridBlocks;
    private ArrayList<Shape> paths;

    public Drawing() {
        super();
        shapes = new ArrayList<Shape>();
        gridBlocks =  new ArrayList<Shape>();
        paths =  new ArrayList<Shape>();
        setBackground(Color.white);
    }

    public ArrayList<Shape> getShapes() { return this.shapes; }

    public ArrayList<Shape> getGridBlocks() { return this.gridBlocks; }

    public ArrayList<Shape> getPaths() { return this.paths; }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
        for (Shape block : gridBlocks) {
            block.draw(g);
        }
        for (Shape path : paths) {
            path.draw(g);
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds the given shape to the drawing
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void addBlock(Shape shape) {
        gridBlocks.add(shape);
    }

    public void addPath(Shape shape) {
        paths.add(shape);
    }


    // MODIFIES: this
    // EFFECTS:  removes shape from the drawing
    public void removeShape(Shape shape) {
        shapes.remove(shape);
        repaint();
    }

    public void clearGrid() {
        gridBlocks = new ArrayList<Shape>();
        //repaint();
    }

    public void clearPaths() {
        paths = new ArrayList<Shape>();
        repaint();
    }

    public Obstacle getShapeAtPoint(Point p) {
        for (Shape s : shapes) {
            if (s.contains(p)) {
                return (Obstacle) s;
            }
        }
        return null;
    }

    public boolean collide(Path p) {
        for (Shape s : shapes) {
            if (Obstacle.class == s.getClass()) {
                if (p.intersects( (Obstacle) s)) {
                    return true;
                }
            }
        }
        return false;
    }
}
