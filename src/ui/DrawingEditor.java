package ui;

import JSON.JsonFileIO;
import exceptions.NoPathException;
import exceptions.PointInObstacleException;
import exceptions.PointOutOfBoundsException;
import model.*;
import model.Shape;
import model.gridPathfinding.Block;
import model.gridPathfinding.Grid;
import model.pointPathfinding.Pathfinder;
import ui.tools.DeleteObstacleTool;
import ui.tools.LineTool;
import ui.tools.ObstacleTool;
import ui.tools.Tool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// The ShortestPath GUI Application
public class DrawingEditor extends JFrame {
    public int WIDTH = 1000;
    public int HEIGHT = 1000;
    private List<Tool> tools;
    private Tool activeTool;
    private Drawing currentDrawing;
    private Grid grid;
    public static File mapFile = new File("./resources/maze.png");
//    public static File mapFile = new File("./resources/UBCMap.png");



    public static void main(String[] args) {
        DrawingEditor l = new DrawingEditor();
    }

    public DrawingEditor() {
        initializeFields();
        initializeGraphics();
        initializeInteraction();
    }

    private void initializeFields() {
        activeTool = null;
        currentDrawing = null;
        tools = new ArrayList<>();
        grid = new Grid(this, WIDTH, HEIGHT);
    }

    private void initializeGraphics() {
        setLayout(new BorderLayout());
        BufferedImage img = null;
        try {
            img = ImageIO.read(mapFile);
        } catch (IOException e) {
            System.out.println("Could not readShapes image file");
        }
        HEIGHT = img.getHeight() + 500;
        WIDTH = img.getWidth() + 500;
        ImageRecognition ir = new ImageRecognition(true);
        setMinimumSize(new Dimension(WIDTH + 19, HEIGHT + 10));
        setResizable(false);
        createTools();
        addNewDrawing();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        formCanvasBorders();
        grid.createEmptyGrid();
        grid.drawGrid();
    }

    // MODIFIES: this
    // EFFECTS:  initializes a DrawingMouseListener to be used in the JFrame
    private void initializeInteraction() {
        DrawingMouseListener dml = new DrawingMouseListener();
        addMouseListener(dml);
        addMouseMotionListener(dml);
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all tools
    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);

        LineTool lineTool = new LineTool(this, toolArea);
        tools.add(lineTool);
        ObstacleTool obstacleTool = new ObstacleTool(this, toolArea);
        tools.add(obstacleTool);
        DeleteObstacleTool deleteObstacleTool = new DeleteObstacleTool(this, toolArea);
        tools.add(deleteObstacleTool);
        setActiveTool(lineTool);
    }

    // MODIFIES: this
    // EFFECTS:  sets the given tool as the activeTool
    public void setActiveTool(Tool aTool) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        aTool.activate();
        activeTool = aTool;
    }

    public void addNewDrawing() {
        Drawing newDrawing = new Drawing();
        currentDrawing = newDrawing;
        add(newDrawing, BorderLayout.CENTER);
        validate();
    }

    private void formCanvasBorders(){
        currentDrawing.addShape(new Obstacle(0, 0, 1, HEIGHT + 1));
        currentDrawing.addShape(new Obstacle(0, 0, WIDTH + 1, 1));
        currentDrawing.addShape(new Obstacle(0, HEIGHT - 1, WIDTH, 1));
        currentDrawing.addShape(new Obstacle(WIDTH - 1, 0, 1, HEIGHT));
    }

    // MODIFIES: this
    // EFFECTS:  adds given Shape to currentDrawing
    public void addToDrawing(Shape s) {
        currentDrawing.addShape(s);
    }

    //MODIFIES: this
    // EFFECTS: removes given shape from currentDrawing
    public void removeFromDrawing(Shape s) {
        currentDrawing.removeShape(s);
    }

    public Obstacle getObstacleInDrawing(Point p) {
        return currentDrawing.getShapeAtPoint(p);
    }

    //GRID PATHFINDER METHODS

    public Grid returnEmptyGrid(){
        Grid g = new Grid(this, WIDTH, HEIGHT);
        g.createEmptyGrid();
        return g;
    }

    public void addToGrid(Shape s) {
        currentDrawing.addBlock(s);
    }

    public void clearGrid() {
        currentDrawing.clearGrid();
    }

    public void updateGrid(){
        grid.updateGrid();
    }

    public Block getBlockAtPoint(Point p) {
        return grid.blockAtPosition(p);
    }

    public void drawFinalPath(Block startBlock, Block targetBlock){
        grid.drawFinalPath(startBlock, targetBlock);
    }

    //METHODS FOR POINT PATHFINDER

    public boolean checkPathCollision(Path p){
        return currentDrawing.collide(p);
    }

    public ArrayList<Shape> returnShapesInDrawing(){
        return currentDrawing.getShapes();
    }

    public void addToPaths(Shape p) {
        currentDrawing.addPath(p);
    }

    public void clearPaths() {
        currentDrawing.clearPaths();
    }

    public void drawShortestPath(Point start, Point end){
        Pathfinder pathfinder = new Pathfinder(start, end, this);
        try {
            pathfinder.findPath();
            ArrayList<Point> points = pathfinder.retracePath();
            for(int i = 1; i < points.size(); i++) {
                addToPaths(new Path(points.get(i-1).x, points.get(i-1).y, points.get(i).x, points.get(i).y));
            }
        } catch (NoPathException e) {
            System.out.println("No Path Found for Given Points");
        }

    }

    //CLICK DETECTION METHODS

    // EFFECTS: if activeTool != null, then mousePressedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMousePressed(MouseEvent e) {
        try {
            if (activeTool != null) {
                activeTool.mousePressedInDrawingArea(e);
            }
            repaint();
        } catch (PointInObstacleException exception) {
            System.out.println("Given Point is Inside an Obstacle");
        } catch (PointOutOfBoundsException exception) {
            System.out.println("Given Point is Out of Bounds");
        }
        System.out.println("x: " + e.getX() + "y: " + e.getY());

    }

    // EFFECTS: if activeTool != null, then mouseReleasedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseReleased(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseReleasedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, then mouseClickedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseClicked(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseClickedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, then mouseDraggedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseDragged(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseDraggedInDrawingArea(e);
        }
        repaint();
    }

    private class DrawingMouseListener extends MouseAdapter {

        // EFFECTS: Forward mouse pressed event to the active tool
        public void mousePressed(MouseEvent e) {
            handleMousePressed(translateEvent(e));
        }

        // EFFECTS: Forward mouse released event to the active tool
        public void mouseReleased(MouseEvent e) {
            handleMouseReleased(translateEvent(e));
        }

        // EFFECTS: Forward mouse clicked event to the active tool
        public void mouseClicked(MouseEvent e) {
            handleMouseClicked(translateEvent(e));
        }

        // EFFECTS: Forward mouse dragged event to the active tool
        public void mouseDragged(MouseEvent e) {
            handleMouseDragged(translateEvent(e));
        }

        // EFFECTS: translates the mouse event to current drawing's coordinate system
        private MouseEvent translateEvent(MouseEvent e) {
            return SwingUtilities.convertMouseEvent(e.getComponent(), e, currentDrawing);
        }
    }

}
