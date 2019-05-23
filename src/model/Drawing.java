package model;

import JSON.JsonFileIO;
import JSON.Jsonifier;
import org.json.JSONArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Drawing extends JPanel {

    private ArrayList<Shape> shapes;
    private ArrayList<Shape> gridBlocks;
    private ArrayList<Shape> paths;

    public Drawing() {
        super();
        try {
            shapes = (ArrayList) JsonFileIO.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        //till here
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./resources/UBCMap.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem with image reading");
        }
        g.drawImage(img,0,0,this);
        // comment out above code

        for (Shape shape : shapes) {
            shape.draw(g);        }
        for (Shape block : gridBlocks) {
            block.draw(g);
        }
        for (Shape path : paths) {
            path.draw(g);
        }
//        JSONArray arry = Jsonifier.shapeListtoJson(shapes);
//        System.out.println(arry.toString());
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
