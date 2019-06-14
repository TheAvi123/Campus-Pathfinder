package ui.tools;

import JSON.JsonFileIO;
import JSON.ShapeJsonifier;
import model.Obstacle;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ObstacleTool extends Tool {

    private Obstacle obstacle;

    public ObstacleTool(DrawingEditor editor, JComponent parent) {
        super(editor,parent);
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Create Obstacle");
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ObstacleToolClickHandler());
    }

    // EFFECTS: default behaviour does nothing
    public void mousePressedInDrawingArea(MouseEvent e) {
        obstacle = new Obstacle();
        obstacle.setStartBound(e.getPoint());
        editor.addToDrawing(obstacle);
    }

    // EFFECTS: default behaviour does nothing
    public void mouseReleasedInDrawingArea(MouseEvent e) {
        editor.updateGrid();
        System.out.println(ShapeJsonifier.shapeToJson(obstacle));
        try {
            JsonFileIO.add(obstacle);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    // EFFECTS: default behaviour does nothing
    public void mouseClickedInDrawingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseDraggedInDrawingArea(MouseEvent e) {
        obstacle.setEndBound(e.getPoint());
    }

    private class ObstacleToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the shape tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(ObstacleTool.this);
        }
    }
}
