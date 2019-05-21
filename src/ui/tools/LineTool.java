package ui.tools;

import exceptions.PointInObstacleException;
import exceptions.PointOutOfBoundsException;
import model.EndPoint;
import model.Path;
import model.gridPathfinding.Block;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class LineTool extends Tool {

    private DrawingEditor editor;
    private Path path;
    private Point startPoint;
    private Point endPoint;
    private EndPoint start;
    private EndPoint end;
    private int clickOne;
    private int x;
    private int y;

    private Block startBlock = null;
    private Block targetBlock;


    public LineTool(DrawingEditor editor, JComponent parent) {
        super(editor, parent);
        clickOne = 1;
        this.editor = editor;
        path = null;
        start = null;
        end = null;
    }

    // MODIFIES: this
    // EFFECTS:  creates new button and adds to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("FindPath");
        button = customizeButton(button);
    }

    // MODIFIES: this
    // EFFECTS:  associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new LineToolClickHandler());
    }

    @Override
    public void mousePressedInDrawingArea(MouseEvent e) throws PointOutOfBoundsException, PointInObstacleException {

        if (clickOne == 1) {
            if (e.getX() > editor.WIDTH || e.getY() > editor.HEIGHT) {
                throw new PointOutOfBoundsException("Point out of bounds.");
            } else if (editor.getObstacleInDrawing(e.getPoint()) != null) {
                throw new PointInObstacleException("Point inside an obstacle");
            }
            x = e.getX();
            y = e.getY();
            start = new EndPoint(e.getPoint(), editor);
            startPoint = e.getPoint();
            startBlock = editor.getBlockAtPoint(e.getPoint());
            editor.addToPaths(start);
            clickOne = 2;

        } else if (clickOne == 2) {
            if (e.getX() > editor.WIDTH || e.getY() > editor.HEIGHT) {
                throw new PointOutOfBoundsException("Point out of bounds.");
            } else if (editor.getObstacleInDrawing(e.getPoint()) != null) {
                throw new PointInObstacleException("Point inside an obstacle");
            }
            path = new Path(x, y, e.getX(), e.getY());
            end = new EndPoint(e.getPoint(), editor);
            editor.addToPaths(end);
            //Point Pathfinder
            endPoint = e.getPoint();
            editor.drawShortestPath(startPoint, endPoint);
            //Grid Pathfinder
//            targetBlock = editor.getBlockAtPoint(e.getPoint());
//            if(!editor.checkPathCollision(path)){
//                editor.addToPaths(path);
//            } else {
//                editor.drawFinalPath(startBlock, targetBlock);
//            }

            clickOne = 3;

        } else if (clickOne == 3) {  // can just be else
            editor.updateGrid();
            editor.clearPaths();

            clickOne = 1;
        }
    }

    private class LineToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the shape tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(LineTool.this);
        }
    }
}

