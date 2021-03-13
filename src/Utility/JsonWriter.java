package Utility;

import Project_v2.Net;
import Project_v2.Pair;
import Project_v2.Place;
import Project_v2.Transition;
//import org.json.JSONArray;
//import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonWriter {
    public static final String WRITING_FILE_ERROR = "writing file error.";
    //pathname string of the file
    private static final String path = new File("src/main/java/Json/").getAbsolutePath();


    //TODO: HO COMMENTATO TUTTI I METODI PERCHè NON è STATO IMPORTATOL IL JSON (jack importa il json pls)
        /**
         * method to write json
         * @param net
         */
        /*
    public static void writeJsonFile(Net net) {
        String stringJson = stringNet(net);
        String fileToWrite = makeFile();

        //write file
        try {
            FileWriter writer = new FileWriter(fileToWrite);
            writer.write(stringJson);
            writer.close();
        } catch (IOException e) {
            System.out.println(WRITING_FILE_ERROR);
            e.printStackTrace();
        }
    }

    private static String stringNet(Net net) {
        ArrayList<Pair> pairsList = net.getNet();

        String idNet = net.getID();
        String nameNet = net.getName();

        //create json object to add to file
        JSONObject netJson = new JSONObject();
        //add propriety to json
        netJson.put("@net", idNet);
        netJson.put("@name", nameNet);

        JSONArray pairs = new JSONArray();

        for (Pair n: pairsList) {
            Place place = n.getPlace();
            String p = place.getId();

            Transition trans = n.getTrans();
            String t = trans.getId();

            JSONObject jsonPair = new JSONObject();
            jsonPair.put("@place", p);
            jsonPair.put("@transition", t);

            pairs.put(jsonPair);
        }

        netJson.put("@pairs", pairs);

        //extract json object the string to print in the file (the parameter is the indentation factor)
        String stringJson = netJson.toString(2);
        return stringJson;
    }

    private static String makeFile() {
        File directory = new File(path);
        String[] pathname = directory.list();
        boolean ctrl;
        String name;
        do {
            name = Reader.ReadString("Insert the name of the file:\n");
            name=name+".json";
            ctrl = false;
            for (String s: pathname) {
                if (name.equals(s)) {
                    System.out.println("File already exist, please try again.");
                    ctrl = true;
                }
            }
        } while (ctrl);
        return path+name;
    }*/

}
