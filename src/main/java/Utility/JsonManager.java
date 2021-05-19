package main.java.Utility;




import main.java.Project_v2.Net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonManager {

    public static final String INSERT_THE_ID_OF_THE_FILE_YOU_WANT_TO_LOAD = "Insert the id of the file you want to load ";
    public static final String FILE_IS_LOADED = "File is loaded";
    public static final String SHOW_THE_NET = "Show the net";
    public static final String THERE_AREN_T_ANY_FILES_TO_LOAD = "There aren't any files to load";

    /**
     * method to load a net by json file
     * @throws FileNotFoundException
     */
    public static Net loadNet(String path) throws FileNotFoundException {
        //initialize the File object directory
        File directory = new File(path);
        //initialize the string that contains the list of name file
        String[] pathname = directory.list();
        int i = 0;
        Net newNet;
        //for every name file in the directory print the name
        if (pathname!=null) {
            for (String s: pathname) {
                i++;
                System.out.println(i+") "+s);
            }
        }
        //if there are not files in the directory print this
        if (i==0) {
            System.out.println(THERE_AREN_T_ANY_FILES_TO_LOAD);
        }
        else {
            //else ask to user to chose which file load

            int number = IO.readInteger(INSERT_THE_ID_OF_THE_FILE_YOU_WANT_TO_LOAD, 1, i);
            //get the name of file by the pathname string array and decrement 1 because the print file start with 1
            String pathFile = path+"/"+pathname[number-1];
            //initialize new net and read json file
            newNet = JsonReader.readJson(pathFile);
            System.out.println(FILE_IS_LOADED);
            System.out.println(SHOW_THE_NET);
            return newNet;
        }
        return null;
    }


    /**
     * method to check if the index to check has already checked or not
     * @param index
     * @param i
     * @param j
     * @return
     */
    public static boolean existAlready(HashMap<Integer, Integer> index, int i, int j) {
        boolean ctrl = false;
        for (Map.Entry<Integer, Integer> entry: index.entrySet()) {
            if (entry.getKey() == i) {
                if (entry.getValue() == j) {
                    ctrl = true;
                }
            }
        }
        return ctrl;
    }
}
