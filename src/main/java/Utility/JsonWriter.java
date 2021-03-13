package Utility;

import Project_v2.Net;
import Project_v2.Pair;
import Project_v2.Place;
import Project_v2.Transition;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class to save the net in json file
 */
public class JsonWriter {
    public static final String WRITING_FILE_ERROR = "writing file error.";
    //absolute pathname string of the json directory
    private static final String path = new File("src/main/java/JsonFile").getAbsolutePath();

    /**
     * method to write json
     * @param net
     */
    public static void writeJsonFile(Net net) {
        //get the json string of the net to print in the file
        String stringJson = stringNet(net);
        //get the pathname of the file to create
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

    /**
     * method to get the Json string of the net
     * @param net
     * @return stringJson
     */
    private static String stringNet(Net net) {
        //initalize the Arraylist of pairs of the net
        ArrayList<Pair> pairsList = net.getNet();

        //initialize the string  id and name of the net
        String idNet = net.getID();
        String nameNet = net.getName();

        //create json object to add to file
        JSONObject netJson = new JSONObject();
        //add propriety to json
        netJson.put("@net", idNet);
        netJson.put("@name", nameNet);

        //initialize the JsonArray of the pairs of the net
        JSONArray pairs = new JSONArray();

        //for every pair in the net get place and transition
        for (Pair pair: pairsList) {
            Place place = pair.getPlace();
            //get the string name of the place
            String p = place.getName();

            Transition trans = pair.getTrans();
            //get the string name of the transition
            String t = trans.getName();
            int d = trans.getInputOutput(p);

            //initialize the jsonPair to build
            JSONObject jsonPair = new JSONObject();
            //add to json pair the tag place and the attribute
            jsonPair.put("@place", p);
            //add to json pair the tag transition and the attribute
            jsonPair.put("@transition", t);
            //add to json pair the tag direction and the attribute
            jsonPair.put("@direction", Integer.toString(d));

            //add the json pair to the json array of the pairs of the net
            pairs.put(jsonPair);
        }

        //add to the json net the json array of the pairs of the net
        netJson.put("@pairs", pairs);

        //extract json object the string to print in the file (the parameter is the indentation factor)
        String stringJson = netJson.toString(2);
        return stringJson;
    }

    /**
     * method to create a new json file where save the net and get its pathname
     * @return pathname
     */
    private static String makeFile() {
        //initialize file object of directory
        File directory = new File(path);
        //initialize the String array of the list of file in directory
        String[] pathname = directory.list();
        boolean ctrl;
        String name;
        do {
            //get the name of file insert by user
            name = Reader.ReadString("Insert the name of the file:\n");
            //add the extension .json to the name file
            name=name+".json";
            //set ctrl false to exit by the loop
            ctrl = false;
            //for every file in the directory check if the string name exist already or not
            if (pathname!=null) {
                for (String s: pathname) {
                    //check if the name input is equal to the string name of file
                    if (name.equals(s)) {
                        System.out.println("File already exist, please try again.");
                        //if the name of file is already in the directory set ctrl true and stay in loop
                        ctrl = true;
                    }
                }
            }
        } while (ctrl); //until the ctrl boolean variable is true
        //return the path of directory and name of file
        return path+"/"+name;
    }

}