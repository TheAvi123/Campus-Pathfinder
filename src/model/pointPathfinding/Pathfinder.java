package model.pointPathfinding;

import exceptions.NoPathException;
import model.ImageRecognition;
import model.Obstacle;
import model.Path;
import model.Shape;
import ui.DrawingEditor;

import java.awt.*;
import java.util.*;

public class Pathfinder {

    Point startPoint, endPoint;
    KeyPoint startKP, endKP;
    DrawingEditor editor;

    Set<KeyPoint> keyPoints;
    ArrayList<KeyPoint> openSet;
    Set<Point> closedSet;

    KeyPoint currentPoint;

    public Pathfinder(Point startPoint, Point endPoint, DrawingEditor editor) {

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.editor = editor;
        keyPoints = new HashSet<>();

        startKP = new KeyPoint(startPoint, startPoint, endPoint);
        endKP = new KeyPoint(endPoint, startPoint, endPoint);
        keyPoints.add(startKP);
        keyPoints.add(endKP);

        openSet = new ArrayList<>();
        closedSet = new HashSet<>();
    }

    public void findPath() throws NoPathException {

        findKeyPoints();

        openSet.add(startKP);
        while (openSet.size() > 0) {
            currentPoint = openSet.get(0);
            for (int i = 1; i < openSet.size(); i++) {
                if (openSet.get(i).getfCost() < currentPoint.getfCost()
                        || (openSet.get(i).getfCost() == currentPoint.getfCost() && openSet.get(i).gethCost() < currentPoint.gethCost())) {
                    currentPoint = openSet.get(i);
                }
            }
            openSet.remove(currentPoint);
            closedSet.add(currentPoint);

            if (currentPoint == endKP) {
                return;
            }

            Set<KeyPoint> accessible = findAccessiblePoints();
            for (KeyPoint kp : accessible) {

                if (closedSet.contains(kp)) {
                    continue;
                }

                double newgCostForPoint = currentPoint.getgCost() +
                        Math.sqrt(Math.pow((currentPoint.x - kp.x), 2) + Math.pow(currentPoint.y - kp.y, 2));
                if (newgCostForPoint < kp.getgCost() || !openSet.contains(kp)) {
                    kp.setgCost(newgCostForPoint);
                    kp.sethCost(Math.sqrt(Math.pow((endPoint.x - kp.x), 2) + Math.pow(endPoint.y - kp.x, 2)));
                    kp.parent = currentPoint;

                    if (!openSet.contains(kp)) {
                        openSet.add(kp);
                    }
                }
            }
        }
        throw new NoPathException("No Path Found");
    }

    public void findKeyPoints() {

        ArrayList<model.Shape> shapes = editor.returnShapesInDrawing();
        Set<KeyPoint> tempSet = new HashSet<>();
        for (Shape s : shapes) {
            tempSet.add(new KeyPoint(new Point(s.getX() - 1, s.getY() - 1), startPoint, endPoint));
            tempSet.add(new KeyPoint(new Point(s.getX() + s.getX2() + 1, s.getY() - 1), startPoint, endPoint));
            tempSet.add(new KeyPoint(new Point(s.getX() - 1, s.getY() + s.getY2() + 1), startPoint, endPoint));
            tempSet.add(new KeyPoint(new Point(s.getX() + s.getX2() + 1, s.getY() + s.getY2() + 1), startPoint, endPoint));
        }
        for (KeyPoint p : tempSet) {
            if (editor.getObstacleInDrawing(p.getPoint()) != null) { // only want to add it when there is no obstacle at that position
                continue;
            }
            keyPoints.add(p);
        }
    }

    public Set<KeyPoint> findAccessiblePoints() {

        Set<KeyPoint> temp = new HashSet<>();
        for (KeyPoint p : keyPoints) {
            Path path = new Path(p.x, p.y, currentPoint.x, currentPoint.y);
            if (!pathBlockChecker(path)) {
                temp.add(p);
            }
        }
        return temp;
    }

    public boolean pathBlockChecker(Path path) {
        for (Shape s : editor.returnShapesInDrawing()) {
            if (path.intersects((Obstacle) s)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Point> retracePath() {
        ArrayList<KeyPoint> finalPath = new ArrayList<>();
        KeyPoint currentPoint = endKP;

        while (currentPoint != startKP) {
            finalPath.add(currentPoint);
            currentPoint = currentPoint.parent;
        }
        finalPath.add(startKP);
        Collections.reverse(finalPath);
        return convertKPstoPoints(finalPath);
    }

    private ArrayList<Point> convertKPstoPoints(ArrayList<KeyPoint> keyPoints) {
        ArrayList<Point> points = new ArrayList<>();
        for (KeyPoint kp : keyPoints) {
            points.add(kp.getPoint());
        }
        return points;
    }
}
