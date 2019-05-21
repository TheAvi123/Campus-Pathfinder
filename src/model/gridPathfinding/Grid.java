package model.gridPathfinding;

import model.Path;
import ui.DrawingEditor;

import java.awt.*;
import java.util.ArrayList;

public class Grid {

    DrawingEditor editor;
    Block[][] grid;

    static final int blockRadius = 2;
    int blockDiameter = blockRadius * 2;
    int worldWidth, worldHeight;
    int gridSizeX, gridSizeY;
    GridPathfinder pathfinder;

    public Grid(DrawingEditor editor, int width, int height){
        this.editor = editor;
        worldWidth = width;
        worldHeight = height;

        gridSizeX = worldWidth / blockDiameter;
        gridSizeY = worldHeight / blockDiameter;
    }

    public void createEmptyGrid(){
        grid = new Block[gridSizeX][gridSizeY];

        for(int x = 0; x < gridSizeX; x++){
            for(int y = 0; y < gridSizeY; y++){
                Point p = new Point((x * blockDiameter),(y * blockDiameter));
                grid[x][y] = new Block(false, p, blockRadius);
            }
        }
    }

    public void drawGrid(){
        editor.clearGrid();
        //Uncomment Following Code to Render Grid
//        for(int x = 0; x < gridSizeX; x++){
//            for(int y = 0; y < gridSizeY; y++){
//                editor.addToGrid(grid[x][y]);
//            }
//        }
    }

    public void updateGrid(){
        for(int x = 0; x < gridSizeX; x++){
            for(int y = 0; y < gridSizeY; y++){
                Point p = new Point((x * blockDiameter),(y * blockDiameter));
                boolean b;
                b = checkObstacleInBlock(p);
                grid[x][y] = new Block(b, p, blockRadius);
            }
        }
        drawGrid();
    }

    //CHANGE PARAMETERS TO ADJUST PRECISION OF OBSTACLE DETECTION
    public boolean checkObstacleInBlock(Point p){
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                if(editor.getObstacleInDrawing(new Point((p.x + x*blockRadius),(p.y + y*blockRadius))) != null){
                    return true;
                }
            }
        }
        return false;
    }

    public int blockGridPosX (Block b) {
        int x = b.point.x / blockDiameter;
        return x;
    }

    public int blockGridPosY (Block b) {
        int y = b.point.y / blockDiameter;
        return y;
    }

    public Block blockAtPosition(Point p) {

        int x = p.x / blockDiameter;
        int y = p.y / blockDiameter;

        return grid[x][y];
    }

    public ArrayList<Block> findAdjacentBlocks(Block b){
        ArrayList<Block> adjacentBlocks = new ArrayList<>();

        for(int x = -1; x <= 1; x++){
            for(int y = -1; y <= 1; y++){
                if (x == 0 && y == 0){
                    continue;
                }

                int checkX = b.getX() + x * blockDiameter;
                int checkY = b.getY() + y * blockDiameter;

                if (checkX >= 0 && checkX < worldWidth && checkY >= 0 && checkY < worldHeight){
                    adjacentBlocks.add(blockAtPosition(new Point(checkX, checkY)));
                }
            }
        }
        return adjacentBlocks;
    }

    public void drawFinalPath(Block startBlock, Block targetBlock){
        pathfinder = new GridPathfinder(this, startBlock, targetBlock);
        pathfinder.findPath();
        ArrayList<Block> blocks = pathfinder.retracePath();

        Block currentBlock = startBlock;
        for(int i = 0; i < blocks.size(); i++) {
            Path p = new Path(currentBlock.getX() + blockRadius, currentBlock.getY() + blockRadius,
                    blocks.get(i).getX() + blockRadius, blocks.get(i).getY() + blockRadius);
            editor.addToGrid(p);
            currentBlock = blocks.get(i);
        }
    }
}
