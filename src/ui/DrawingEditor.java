package ui;

import model.Drawing;
import model.Obstacle;
import model.Path;
import model.Shape;
import model.pathfinding.Block;
import model.pathfinding.Grid;
import ui.tools.DeleteObstacleTool;
import ui.tools.LineTool;
import ui.tools.ObstacleTool;
import ui.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// The ShortestPath GUI Application
public class DrawingEditor extends JFrame {
    private static final String TITLE = "ShortestPath";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private List<Tool> tools;
    private Tool activeTool;
    private Drawing currentDrawing;
    private Drawing gridDrawing;
    private Grid grid;

    public static void main(String[] args) {
        DrawingEditor l = new DrawingEditor();
    }

    public DrawingEditor() {
        initializeFields();
        initializeGraphics();
        initializeInteraction();
    }

    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH + 18, HEIGHT + 125));
        createTools();
        addNewDrawing();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        grid.createEmptyGrid();
        grid.drawGrid();
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

    private void initializeFields() {
        activeTool = null;
        currentDrawing = null;
        gridDrawing = null;
        tools = new ArrayList<>();
        grid = new Grid(this, WIDTH, HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS:  initializes a DrawingMouseListener to be used in the JFrame
    private void initializeInteraction() {
        DrawingMouseListener dml = new DrawingMouseListener();
        addMouseListener(dml);
        addMouseMotionListener(dml);
    }

    public void addNewDrawing() {
        Drawing newDrawing = new Drawing();
        currentDrawing = newDrawing;
        add(newDrawing, BorderLayout.CENTER);
        validate();
    }


    // MODIFIES: this
    // EFFECTS:  adds given Shape to currentDrawing
    public void addToDrawing(Shape s) {
        currentDrawing.addShape(s);
    }

    public void addToGrid(Shape s) {
        currentDrawing.addBlock(s);
    }

    //MODIFIES: this
    // EFFECTS: removes given shape from currentDrawing
    public void removeFromDrawing(Shape s) {
        currentDrawing.removeShape(s);
    }

    public void clearGrid() {
        currentDrawing.clearGrid();
    }

    public Obstacle getObstacleInDrawing(Point p) {
        return currentDrawing.getShapeAtPoint(p);
    }

    public void updateGrid(){
        grid.updateGrid();
    }

    public Block getBlockAtPoint(Point p) {
        return grid.blockAtPosition(p);
    }

    public boolean checkPathCollision(Path p){
        return currentDrawing.collide(p);
    }

    public void drawFinalPath(Block startBlock, Block targetBlock){
        grid.drawFinalPath(startBlock, targetBlock);
    }

    public Grid returnEmptyGrid(){
        Grid g = new Grid(this, WIDTH, HEIGHT);
        g.createEmptyGrid();
        return g;
    }

    // EFFECTS: if activeTool != null, then mousePressedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMousePressed(MouseEvent e)  {
        if (activeTool != null) {
            activeTool.mousePressedInDrawingArea(e);
        }
        repaint();
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
