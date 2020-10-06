package com.example.task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CallApi {
    public static JSONObject resultjsonobject;
    public static String error = "";
    public static List<DataAdapter> DataList;
    public static double TotalCap = 0.0;

    public static void CallWebMethod() throws JSONException {

        try {
            URL mUrl = new URL("https://api.coincap.io/v2/assets");
            HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-length", "0");
            httpConnection.setUseCaches(false);
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setConnectTimeout(100000);
            httpConnection.setReadTimeout(100000);
            httpConnection.connect();
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                if (sb.length() > 0) {
                    resultjsonobject = new JSONObject(sb.toString());
                    JSONArray jsonArray = resultjsonobject.getJSONArray("data");
                    DataList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        DataList.add(new DataAdapter(
                                jsonArray.getJSONObject(i).getString("name"),
                                jsonArray.getJSONObject(i).getDouble("priceUsd"),
                                jsonArray.getJSONObject(i).getDouble("changePercent24Hr"),
                                jsonArray.getJSONObject(i).getString("symbol")));
                        TotalCap += jsonArray.getJSONObject(i).getDouble("priceUsd");
                    }
                } else
                    error = "Oops Something went wrong try again later!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            error=e.toString();
        }
    }
}
