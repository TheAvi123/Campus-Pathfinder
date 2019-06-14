package JSON;

import model.Shape;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.List;

public class ImageJsonifier {

    //turns shapes into json array
    public static JSONArray pointListtoJson(List<Point> points) {
        JSONArray pntList = new JSONArray();
        for (Point pnt : points) {
            pntList.put(pointToJson(pnt));
        }
        return pntList;
    }

    public static JSONObject pointToJson(Point point) {
        JSONObject jsonPoint = new JSONObject();
        jsonPoint.put("x",point.getX());
        jsonPoint.put("y",point.getY());
        return jsonPoint;
    }

    // can add more information in future about color,ect
}
