package JSON;

import model.Obstacle;
import model.Shape;
import org.json.JSONArray;
import org.json.JSONObject;

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
    public static final File jsonDataFile = new File("./resources/shapes.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of shapes from the content of jsonDataFile
    public static List<Shape> read() throws IOException {
        String readIt = "";
        readIt = new String(Files.readAllBytes(Paths.get(jsonDataFile.getPath())));
        ShapeParser parser = new ShapeParser();
        return parser.parse(readIt);
    }

    // EFFECTS: saves the obstacle to jsonDataFile
    public static void write(List<Shape> shapes) throws IOException {
        JSONArray array = Jsonifier.shapeListtoJson(shapes);
        String addToFile = array.toString();
        FileWriter writer = new FileWriter(jsonDataFile);
        writer.write(addToFile);
        writer.close();
    }

    // EFFECTS: adds the obstacle to jsonDataFile
    public static void add(Shape shape) throws IOException {
        List<Shape> shapes = new ArrayList<>();
        if (read() != null) {
            List<Shape> oldShapes = read();
            shapes.addAll(oldShapes);
        }
        shapes.add(shape);
        write(shapes);
    }

    // EFFECTS: removes the obstacle to jsonDataFile
    public static void remove(Shape shape) throws IOException {
        List<Shape> shapes = new ArrayList<>();
        if (read() != null) {
            List<Shape> oldShapes = read();
            shapes.addAll(oldShapes);
        }
        if (shapes.contains(shape)) {
            shapes.remove(shape);
        }
        write(shapes);
    }
}
