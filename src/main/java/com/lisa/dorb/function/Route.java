package com.lisa.dorb.function;


/*input: adres
return:
    -Km **
    -Land **
    -Route
    -Adres **
input: rit
return:
    -lijst met verst
 */
//Kijk naar km tussen 2 in 1 rit en tussen rit 2 en thuisbase.

import com.vaadin.spring.annotation.SpringComponent;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;
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
        JSONObject inRows = obj.getJSONArray("rows").getJSONObject(0);
        JSONObject inElements = inRows.getJSONArray("elements").getJSONObject(0);
        JSONObject inDistance = inElements.getJSONObject("distance");
        String km = inDistance.getString("text");
        return km;
    }

    public String getLand(String str_from, String str_to) throws Exception {
        // laat mensen landen in verschillend vakje invullen en voeg dat dan bij de rest van het adres
        return "";
    }

    public String getAdres(String str_from, String str_to) throws Exception {
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
        String adres = obj.getJSONArray("destination_addresses").getString(0);
        return adres;
    }

    public void ritLijst(String str_from, List<String> adres){ //doe in model
        //get all from list
        //vergelijk
            //krijg km tussen thuis en adres
            //laad in lijst
        //Order de lijst op groote van de km
        //vergelijk: 2 (kan nieuwe function)
            //krijg km tussen adres 1 en adres 2
            //vergelijk met adres naar thuis
                //als grooter maak nieuwe rit aan


        for(String ad : adres){


        }

    }

}
