package JSON;

import model.Shape;
import org.json.JSONArray;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileIO {
    //reads and writes to jsonfile
    //jsonifier and shape parser are basically helper functions
    public static final File jsonShapesData = new File("./resources/shapes.json");
    public static final File jsonImageData = new File("./resources/imageRender.json");


    // EFFECTS: attempts to readShapes jsonShapesData and parse it
    //           returns a list of shapes from the content of jsonShapesData
    public static List<Shape> readShapes() throws IOException {
        String readIt = "";
        readIt = new String(Files.readAllBytes(Paths.get(jsonShapesData.getPath())));
        ShapeParser parser = new ShapeParser();
        return parser.parse(readIt);
    }

    // EFFECTS: attempts to readShapes jsonShapesData and parse it
    //           returns a list of shapes from the content of jsonShapesData
    public static List<Point> readImage() throws IOException {
        String readIt = "";
        readIt = new String(Files.readAllBytes(Paths.get(jsonImageData.getPath())));
        ImageParser parser = new ImageParser();
        return parser.parse(readIt);
    }

    // EFFECTS: saves the obstacle to jsonShapesData
    public static void writeToShapes(List<Shape> shapes) throws IOException {
        JSONArray array = ShapeJsonifier.shapeListtoJson(shapes);
        String addToFile = array.toString();
        FileWriter writer = new FileWriter(jsonShapesData);
        writer.write(addToFile);
        writer.close();
    }

    // EFFECTS: saves the points to jsonImageData
    public static void writeToImageData(List<Point> points) throws IOException {
        JSONArray array = ImageJsonifier.pointListtoJson(points);
        String addToFile = array.toString();
        FileWriter writer = new FileWriter(jsonImageData);
        writer.write(addToFile);
        writer.close();
    }

    // EFFECTS: adds the obstacle to jsonShapesData
    public static void add(Shape shape) throws IOException {
        List<Shape> shapes = new ArrayList<>();
        if (readShapes() != null) {
            List<Shape> oldShapes = readShapes();
            shapes.addAll(oldShapes);
        }
        shapes.add(shape);
        writeToShapes(shapes);
    }

    // EFFECTS: removes the obstacle to jsonShapesData
    public static void remove(Shape shape) throws IOException {
        List<Shape> shapes = new ArrayList<>();
        if (readShapes() != null) {
            List<Shape> oldShapes = readShapes();
            shapes.addAll(oldShapes);
        }
        if (shapes.contains(shape)) {
            shapes.remove(shape);
        }
        writeToShapes(shapes);
    }
}
