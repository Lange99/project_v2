package Utility;

import Project_v2.Net;
import Project_v2.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JsonReader {
    //private static String path = new File("src/main/java/Json/Net.json").getAbsolutePath();

    public static Net readJson(String pathname) throws FileNotFoundException {
        String path = new File(pathname).getAbsolutePath();

        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(new File(path));

        while (sc.hasNextLine()) {
            sb.append(sc.nextLine()).append("\n");

        }
        System.out.println(sb.toString());
        JSONObject objectJson = new JSONObject(sb.toString());
        String netName = objectJson.getString("@name");
        String idNet = objectJson.getString("@net");
        JSONArray placesNet = objectJson.getJSONArray("@pairs");
        Net net = new Net(netName, idNet);

        for (int i = 0; i < placesNet.length(); i++) {
            JSONObject obj = (JSONObject) placesNet.get(i);
            String place = obj.getString("@place");
            String trans = obj.getString("@transition");
            Pair pair = new Pair(place, trans);
            net.addPairFromJson(pair);
        }
        return net;
    }
}
