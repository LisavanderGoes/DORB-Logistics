package com.lisa.dorb.function;


/*input: adres
return:
    -Km **
    -Natio **
    -Route **
    -Adres **
input: rit
return:
    -lijst met verst ** (stem af met maps)
 */
//Kijk naar km tussen 2 in 1 rit en tussen rit 2 en thuisbase.

import com.lisa.dorb.layout.OrderUI;
import com.lisa.dorb.model.Km;
import com.vaadin.spring.annotation.SpringComponent;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.*;

@SpringComponent
public class Route {

    @Autowired
    OrderUI orderUI;

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
        JSONObject inDistance = inElements.optJSONObject("distance");
        String km = inDistance.optString("text");
        km = km.replaceAll(",",".");
        km = km.replaceAll("\\s+","");
        km = km.replaceAll("km","");
        return km;
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

    public ArrayList<String> ritLijst(String str_from, ArrayList<String> adres){ //doe in model
        //get all from list
        //vergelijk
            //krijg km tussen thuis en adres
            //laad in lijst
        //Order de lijst op groote van de km
        //vergelijk: 2 (kan nieuwe function)
            //krijg km tussen adres 1 en adres 2
            //vergelijk met adres naar thuis
                //als grooter maak nieuwe rit aan

        ArrayList<Km> HomeToKm = new ArrayList<>();
        ArrayList<Float> KmtoHome = new ArrayList<>();
        ArrayList<String> adr = new ArrayList<>(); //goede volgende adres
        ArrayList<Float> dis = new ArrayList<>(); //goede volgende distance

        for(String ad : adres){ //naar km en add to dis
            float km;
            try {
                km = Float.parseFloat(getDistance(str_from, ad));
                HomeToKm.add(new Km(ad, km));
                dis.add(km);
                //orderUI.send.setValue(orderUI.send.getValue()+km+ "-org-");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(dis);

        for(Float km : dis){ //laat nieuwe dis zien
            try {
                //orderUI.send.setValue(orderUI.send.getValue()+km+ "---");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for(Float dst: dis){ //adres op dis volgende
            for (Km km: HomeToKm){
                if(dst.equals(km.getKm())){
                    adr.add(km.getAdres());
                    //orderUI.send.setValue(orderUI.send.getValue()+km.getOrder()+ "-+-");
                }
            }
        }

        return adr;

//        String frm = str_from;

//        for(String ad : adr){ //berekenen de afstanden tussen 1 en 2 (klopt)
//            float km;
//            try {
//                km = Float.parseFloat(getDistance(frm, ad));
//                frm = ad;
//                KmtoHome.add(km);
//                orderUI.send.setValue(orderUI.send.getValue()+km+ "-o-");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public String setMaps(String str_from, ArrayList<String> waypoint){
        //laat eerst 1 rout zien als bij bestemming klik button voor volgende bestemming
        StringBuilder waypoints = new StringBuilder();
        for(String ad: waypoint){
            waypoints.append(ad).append("|");
        }
        String s = "https://maps.googleapis.com/maps/api/directions/json?origin=" + str_from + "&destination=" + str_from + "&mode=driving&language=nl-NL&avoid=tolls&waypoints=optimize:true|"+ waypoints +"&key=" + API_KEY;
        orderUI.send.setValue(s);
        return(s);

    }

}
