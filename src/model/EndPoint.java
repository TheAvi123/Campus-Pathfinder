package model;

import ui.DrawingEditor;

import java.awt.*;

public class EndPoint extends Shape {

    private static final Color color = Color.BLACK;

    private static final int sideLength = 10;    //MIGHT WANT TO CHANGE WITH CHANGE IN GRID PARAMETERS

    private DrawingEditor editor;

    public EndPoint(Point p, DrawingEditor editor) {
        super(color, p.x, p.y, sideLength,sideLength);
        this.editor = editor;
        this.x = p.x;
        this.y = p.y;

        //Uncomment to Make EndPoints Snap to Grid
//        Grid g = editor.returnEmptyGrid();
//        Block b = g.blockAtPosition(new Point(x, y));
//        this.x = b.getX();
//        this.y = b.getY();
    }

    @Override
    public boolean contains(Point p) {return false;}

    public void draw(Graphics g) {
        Color save = g.getColor();
        g.setColor(color);
        g.fillRect(x - sideLength / 2, y - sideLength / 2, x2, y2);
        //g.fillRect(x,y,x2,y2);
        g.setColor(save);
    }
}