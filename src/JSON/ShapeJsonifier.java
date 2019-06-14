package JSON;

import model.Obstacle;
import model.Shape;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ShapeJsonifier {
    //turns shapes into json array
    public static JSONArray shapeListtoJson(List<Shape> shapes) {
        JSONArray obsList = new JSONArray();
        for (Shape obs : shapes) {
            obsList.put(shapeToJson(obs));
        }
        return obsList;
    }

    public static JSONObject shapeToJson(Shape shape) {
        JSONObject jsonShape = new JSONObject();
        jsonShape.put("x",shape.getX());
        jsonShape.put("y",shape.getY());
        jsonShape.put("x2",shape.getX2());
        jsonShape.put("y2",shape.getY2());
        return jsonShape;
    }

}
