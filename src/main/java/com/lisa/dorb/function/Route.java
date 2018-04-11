package com.lisa.dorb.function;


/*input: adres
return:
    -Km
    -Land
    -Route
    -Adres
input: rit (miss kijken naar windrichtingen)
return:
    -lijst met verst
 */ //miss is region wel iets

import com.vaadin.spring.annotation.SpringComponent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

@SpringComponent
public class Route {

    private static final String API_KEY = "AIzaSyAU7XaF6rJn7F6oITjKukT-Z5hi0bs95PI";

    public String getDistance(String str_from, String str_to) throws Exception {
        // build a URL
        String s = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=nl-NL&avoid=tolls&key=" + API_KEY;
        URL url = new URL(s);

        // read from the URL
        Scanner scan = new Scanner(url.openStream());
        String str = new String();
        while (scan.hasNext()) {
            str += scan.nextLine();
        }
        scan.close();

        // build a JSON object
        JSONObject obj = new JSONObject(str);
        if (!obj.getString("status").equals("OK")) {
            return obj.getString("status");
        }

        // get the first result
        JSONObject res = obj.getJSONArray("rows").getJSONObject(0);
        JSONObject des = res.getJSONArray("elements").getJSONObject(0);
        JSONObject loc = des.getJSONObject("distance");
        String rtrn = loc.getString("text");
        return rtrn;
    }

}
