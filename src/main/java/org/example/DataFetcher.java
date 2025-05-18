package org.example;

import org.example.PointData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataFetcher {
    public static List<PointData> fetchData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            content.append(line);
        }
        in.close();
        con.disconnect();

        JSONArray array = new JSONArray(content.toString());
        List<PointData> points = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            int x = (int) Math.round(obj.getDouble("x"));
            int y = (int) Math.round(obj.getDouble("y"));
            boolean white = obj.getBoolean("white");
            points.add(new PointData(x, y, white));
        }

        return points;
    }
}