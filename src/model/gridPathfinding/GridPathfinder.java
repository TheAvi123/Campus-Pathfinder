package model.gridPathfinding;

import exceptions.NoPathException;
import model.ImageRecognition;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GridPathfinder {

    Grid grid;

    Block startBlock;
    Block targetBlock;

    ArrayList<Block> openBlocks;
    Set<Block> closedBlocks;

    public GridPathfinder(Grid grid, Block startBlock, Block targetBlock){
        this.grid = grid;
        this.startBlock = startBlock;
        this.targetBlock = targetBlock;

        openBlocks = new ArrayList<>();
        closedBlocks = new HashSet<>();

    }


        public void findPath() throws NoPathException {

        openBlocks.add(startBlock);

        while(openBlocks.size() > 0) {
            Block currentBlock = openBlocks.get(0);
            for(int i = 1; i < openBlocks.size(); i++){
                if( (openBlocks.get(i).getfCost() < currentBlock.getfCost())
                        || (openBlocks.get(i).getfCost() == currentBlock.getfCost()) && (openBlocks.get(i).hCost < currentBlock.hCost) ){
                    currentBlock = openBlocks.get(i);
                }
            } // by the end of this loop current block has the smallest possible cost
            openBlocks.remove(currentBlock);
            closedBlocks.add(currentBlock);

            if (currentBlock == targetBlock) {
                retracePath(); // whats the point of just calling this, the array that this method returns doesn't go anywhere
                return; // if you've reached ur goal then return
            }

            ArrayList<Block> neighbors = grid.findAdjacentBlocks(currentBlock);
            for (Block neighbor : neighbors) {
//                ImageRecognition ir = new ImageRecognition(false);
                if(neighbor.isBlocked || closedBlocks.contains(neighbor)) {
                    continue;
                }

                int newMoveCostToNeighbor = currentBlock.gCost + getDistanceBetween(currentBlock, neighbor);
                if (newMoveCostToNeighbor < neighbor.gCost || !openBlocks.contains(neighbor)){ // when would the first part of this or statement ever be true?
                    neighbor.gCost = newMoveCostToNeighbor;
                    neighbor.hCost = getDistanceBetween(neighbor, targetBlock);
                    neighbor.parentBlock = currentBlock;

                    if (!openBlocks.contains(neighbor)){
                        openBlocks.add(neighbor);
                    }
                }
            }
        }
        throw new NoPathException("No Path Found");
    }

    public ArrayList<Block> retracePath(){
        ArrayList<Block> finalPath = new ArrayList<>();
        Block currentBlock = targetBlock;

        while(currentBlock != startBlock){
            finalPath.add(currentBlock);
            currentBlock = currentBlock.parentBlock;
        }
        Collections.reverse(finalPath);
        return finalPath;
    }

    int getDistanceBetween (Block blockA, Block blockB) {
        int distX = Math.abs(grid.blockGridPosX(blockA) - grid.blockGridPosX(blockB));
        int distY = Math.abs(grid.blockGridPosY(blockA) - grid.blockGridPosY(blockB));

        if (distX == distY) {
            return 14*distX;
        } else if (distX > distY) {
            return 14*distY + 10*(distX-distY);
        } else {
            return 14*distX + 10*(distY-distX);
        }
    }
}
