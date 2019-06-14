package JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImageParser {
    //turns string into list of points

    List<Point> pointList = new ArrayList<>();


    public List<Point> parse(String input) {
        if (!input.isEmpty()) {
            JSONArray jsonPoints = new JSONArray(input);
            for (int i = 0; i < jsonPoints.length(); i++) {
                JSONObject jsonPoint = jsonPoints.getJSONObject(i);
                pointList.add(parseObj(jsonPoint));
            }
            return pointList;
        }
        return new ArrayList<Point>();
    }

    public Point parseObj(JSONObject point) {
        return new Point((int) point.get("x"), (int) point.get("y"));
    }

}
