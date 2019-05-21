package ui.tools;

import model.Obstacle;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class DeleteObstacleTool extends Tool {

    private Obstacle obstacletoDelete;

    public DeleteObstacleTool(DrawingEditor editor, JComponent parent) {
        super(editor, parent);
        obstacletoDelete = null;
    }

    // MODIFIES: this
    // EFFECTS:  constructs a delete button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("DeleteObstacle");
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new DeleteToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS:  Sets the shape at the current mouse position as the shape to delete,
    //           selects the shape and plays it
    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
        obstacletoDelete = editor.getObstacleInDrawing(e.getPoint());
    }

    // MODIFIES: this
    // EFFECTS:  unselects the shape being deleted, and removes it from the drawing
    @Override
    public void mouseReleasedInDrawingArea(MouseEvent e) {
        if (obstacletoDelete != null) {
            editor.removeFromDrawing(obstacletoDelete);
            obstacletoDelete = null;
            editor.updateGrid();
        }
    }

    private class DeleteToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the delete tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(DeleteObstacleTool.this);
        }
    }
}
