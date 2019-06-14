package model;

import JSON.JsonFileIO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static JSON.JsonFileIO.jsonImageData;
import static ui.DrawingEditor.mapFile;


public class ImageRecognition {

    BufferedImage img;

    public List<Point> buildings = new ArrayList<>();
    public List<Shape> shapes = new ArrayList<>();


    public ImageRecognition(boolean shouldRender) {
        JsonFileIO reader = new JsonFileIO();
        buildings = new ArrayList<>();
//        try {
////            if (!reader.readShapes().isEmpty()) {
////                shapes = reader.readShapes();
//            }// probably not the best List to use
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            img = ImageIO.read(mapFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (shouldRender) {
            renderImage();
        }

    }

    public List<Point> getBuildingPos() {
        return buildings;
    }

    public boolean isABuildingThere(Point p) {
        return buildings.contains(p);
    }


    private void renderImage() {
        try {
            int height = img.getHeight() ;
            int width = img.getWidth() ;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (shapes.size() == 10) {
                        System.out.println("test");
                    }

                        if (isBlack(x, y)) {
                            if (notInShape(x, y)) {
                                int shapeWidth = 0;
                                int shapeHeight = 1;
                                buildings.add(new Point(x,y));
                                while (rightIsBlack(x + shapeWidth, y)) {
                                    shapeWidth++;
                                }
                                while (downIsBlack(x, y + shapeHeight, shapeWidth)) {
                                    shapeHeight++;
                                }
                                if (shapeHeight > 0) {
                                    shapes.add(new Obstacle(x, y, shapeWidth, shapeHeight));
                                }
                            }

//                            if (checkNeighbors(x, y)) {
//                                buildings.add(new Point(x, y));
//                            }
                        }
                    }
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        System.out.println("out of bounds... chaka");
//                    }
                }
            System.out.println("number of obstacles: " + shapes.size());

            JsonFileIO writer = new JsonFileIO();
            writer.writeToShapes(shapes);
            writer.writeToImageData(buildings);
        } catch (IOException e) {
            System.out.println("ImageRecognition could not readShapes file");
        }
    }

    private boolean notInShape(int x, int y) {
        for (Shape s : shapes) {
            if (s.contains(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }

    private boolean rightIsBlack(int x, int y) {
        return isBlack(x++, y);
    }

    private boolean downIsBlack(int x, int y, int width) {
        if (y >= img.getHeight()) {
            return false;
        }
        for (int i = 0; i <= width; i++) {
            if (!isBlack(x + i, y)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkNeighbors(int x, int y) {
        int count = 0;
        if (x > 1 && y > 1 && x < img.getWidth() - 1 && y < img.getHeight() - 1) {
            for (int i = -1; i <= 1; i++) {
                for (int h = -1; h <= 1; h++) {
                    if (isBlack(x + i, y + i)) {
                        count++;
                    }
                }
            }
        }

        return count == 6;
    }

    public boolean isBlack(int x, int y) {
        int rgb = img.getRGB(x, y);
//        return rgb == Color.BLACK.getRGB();
        float hsb[] = new float[3];
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb) & 0xFF;
        Color.RGBtoHSB(r, g, b, hsb);
        return (hsb[2] < .5);
        //                    else {
//                        float deg = hsb[0] * 360;
//                        if ((deg >= 0 && deg < 30) || (deg >= 330 && deg <= 360)){   buildings.add(new Point(w, h));}

//                        else if (deg >= 30 && deg < 90) yellow();
//                        else if (deg >= 90 && deg < 150) green();
//                        else if (deg >= 150 && deg < 210) cyan();
//                        else if (deg >= 210 && deg < 270) blue();
//                        else if (deg >= 270 && deg < 330) magenta();
//                        else red();
//                    }
    }


}
