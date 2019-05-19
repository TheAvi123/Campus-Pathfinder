package ui.tools;

import model.EndPoint;
import model.Path;
import model.pathfinding.Block;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class LineTool extends Tool {

    private Path path;
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
    public void mousePressedInDrawingArea(MouseEvent e) {

        if (clickOne == 1) {
            x = e.getX();
            y = e.getY();
            start = new EndPoint(e.getPoint(), editor);
            startBlock = editor.getBlockAtPoint(e.getPoint());
            editor.addToDrawing(start);
            clickOne = 2;

        } else if (clickOne == 2) {
            path = new Path(x,y,e.getX(),e.getY());
            end = new EndPoint(e.getPoint(), editor);
            targetBlock = editor.getBlockAtPoint(e.getPoint());
            if(!editor.checkPathCollision(path)){
                editor.addToDrawing(path);

            } else {
                editor.drawFinalPath(startBlock, targetBlock);
            }
            editor.addToDrawing(end);
            clickOne = 3;

        } else if (clickOne == 3) {  // can just be else
            editor.removeFromDrawing(path);
            editor.removeFromDrawing(start);
            editor.removeFromDrawing(end);
            clickOne = 1;
            editor.updateGrid();
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

