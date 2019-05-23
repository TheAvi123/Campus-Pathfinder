package JSON;

import model.Obstacle;
import model.Shape;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShapeParser {
    //turn string into shape array

    List<Shape> shapeList = new ArrayList<>();


    public List<Shape> parse(String input) {
        JSONArray jsonObstacles = new JSONArray(input);
        for (int i = 0; i < jsonObstacles.length(); i++) {
            JSONObject jsonObstacle = jsonObstacles.getJSONObject(i);
            shapeList.add(parseObj(jsonObstacle));
        }
        return shapeList;
    }

    public Shape parseObj(JSONObject obstacle) {
        return new Obstacle((int) obstacle.get("x"), (int) obstacle.get("y"), (int) obstacle.get("x2"), (int) obstacle.get("y2"));
    }
}
