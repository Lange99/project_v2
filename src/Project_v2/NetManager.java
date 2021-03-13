package Project_v2;
import Utility.JsonReader;
import Utility.JsonWriter;
import Utility.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetManager {
    public static final String ANOTHER_NET = "You want add another Net?";
    public static final String NAME_OF_NET = "Add the name of Net:";
    public static final String MENU = "What do you want do?\n0)EXIT\n1)Add new Net\n"+/*"2)Check the net\n3)Save Net\n"+*/"2)Load net";
    public static final String WANT_TO_DO_ANOTHER_OPERATION = "you want to do another operation ";
    public static final String DIGIT_YOUR_CHOISE = "Digit your choise ";
    public static final String DIGIT_VALID_CHOISE = "Digit valid choise!";
    public static final String THE_NET_IS_INCORRECT_IT_CAN_T_BE_SAVED = "The net is incorrect, it can't be saved";
    public static final String THE_NET_IS_CORRECT_WE_ARE_GOING_TO_SAVE_IT = "The net is correct, we are going to save it";

    private ArrayList<Net> netList = new ArrayList<Net>();

    public void menageOption() throws FileNotFoundException {
        boolean check=true;
        int choise=0;
        do {
            System.out.println(MENU);
            choise = Reader.readNumber(DIGIT_YOUR_CHOISE);
            while(choise<0 || choise>4){
                System.out.println(DIGIT_VALID_CHOISE);
                choise=Reader.readNumber(DIGIT_YOUR_CHOISE);
            }

            switch (choise){
                case 0:
                    check=false;
                    break;
                case 1:

                    addNet();
                    check=Reader.yesOrNo(WANT_TO_DO_ANOTHER_OPERATION);
                    break;


                case 2:
                    loadNet();
                    check=Reader.yesOrNo(WANT_TO_DO_ANOTHER_OPERATION);
                    break;
            }
        }while(check==true);

    }

    public void addNet() {
        do {
            Net n= new Net(Reader.ReadString(NAME_OF_NET));
            if(checkNet(n) && n.checkTrans()) {
                showNet(n);
                System.out.println(THE_NET_IS_CORRECT_WE_ARE_GOING_TO_SAVE_IT);
                netList.add(n);
                boolean c=n.checkConnect();
                System.out.println(c);
                //  if(Reader.yesOrNo("Do you want to save the net that you have already made? ")){
                //TODO: JsonWriter.writeJsonFile(n);
                // }
            }else{
                System.out.println(THE_NET_IS_INCORRECT_IT_CAN_T_BE_SAVED);
            }
        } while (Reader.yesOrNo(ANOTHER_NET));
    }

    //DA DEBUGGARE
    public boolean checkNet(Net n){

        if(n.checkPendantNode()==false){

            return false;
        }else{
            return true;
        }
    }

    /**
     * method to load a net by json file
     * @throws FileNotFoundException
     */
    public void loadNet() throws FileNotFoundException {
        //initialize the File object directory
        File directory = new File("src/main/java/Json");
        //initialize the string that contains the list of name file
        String[] pathname = directory.list();
        int i = 0;
        //for every name file in the directory print the name
        for (String s: pathname) {
            i++;
            System.out.println(i+") "+s);
        }
        //if there are not files in the directory print this
        if (i==0) {
            System.out.println("There aren't any files to load");
        }
        else {
            //else ask to user to chose which file load
            int number = Reader.leggiIntero("Insert the id of the file you want to load ", 1, i);
            //get the name of file by the pathname string array and decrement 1 because the print file start with 1
            String path = "src/main/java/Json/"+pathname[number-1];
            //initialize new net and read json file
            /* TODO: JSON
            Net newNet = JsonReader.readJson(path);
            //add new net to the list of the net
            netList.add(newNet);
            System.out.println("File is loaded");
            System.out.println("Visualizzazione della lista");
            //view the new net
            showNet(newNet);*/
        }
    }

    /**
     * method to view the net
     * @param net
     */
    public void showNet(Net net) {
        //get name and if of the net
        String nameNet = net.getName();
        String idNet = net.getID();
        //initialize the places and transitions arraylist
        ArrayList<String> places = new ArrayList<String>();
        ArrayList<String> transitions = new ArrayList<String>();
        ArrayList<Integer> directions = new ArrayList<>();

        //for every pair in the net get the name of place and name of transition
        for (Pair p: net.getNet()) {
            String place = p.getPlace().getName();
            String trans = p.getTrans().getName();
            int direction = p.getTrans().getInputOutput(p.getTrans().getName());
            //add place to arraylist of places
            places.add(place);
            //add transition to arraylist of transitions
            transitions.add(trans);
            directions.add(direction);
        }
        ArrayList<Integer> order = new ArrayList<>();
        //initialize hashmap that contains the index of place that have the same transition in common
        HashMap<Integer, Integer> index = new HashMap<Integer, Integer>();
        //for every transition in the arraylist check if there are other transition equal
        for (int i = 0; i < transitions.size(); i++) {
            for (int j = 0; j < transitions.size(); j++) {
                //if index i and j are different, check
                if (i != j) {
                    //if the transition in i position is equal to the transition in j position, put the index i and j put the index i and j in the hashmap of index
                    if (transitions.get(i).equals(transitions.get(j))) {
                        int dir = directions.get(i);
                        if (dir==1) {
                            if (!existAlready(index, i, j)) {
                                index.put(i, j);
                                order.add(0);
                            }
                        }
                        else {
                            if (!existAlready(index, i, j)) {
                                index.put(i, j);
                                order.add(1);
                            }
                        }
                    }
                }
            }
        }
        //initialize new hashmap of index without the copies of the same reference
        //HashMap<Integer, Integer> indexUpdate = checkDuplicate(index);
        //initialize new Arraylist of couples
        ArrayList<String> couples = new ArrayList<String>();
        //for every element in indexUpdate initialize a String that contains the two place and the transition in common
        int i = 0;
        String couple = "";
        for (Map.Entry<Integer, Integer> entry: index.entrySet()) {
            if (order.get(i) == 0) {
                couple = places.get(entry.getKey())+"----->"+transitions.get(entry.getValue());
            }
            else {
                couple = places.get(entry.getKey())+"<-----"+transitions.get(entry.getValue());
            }
            //add the string to the arraylist
            couples.add(couple);
            i++;
        }

        //print the name and id and print all the pairs with their transition
        System.out.println("\nName net: "+nameNet+"\t\tID net: "+idNet);
        System.out.println("List pairs:");
        for (String s: couples) {
            System.out.println("\t"+s);
        }
        System.out.println();
    }

    private static boolean existAlready(HashMap<Integer, Integer> index, int i, int j) {
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

//    method that delete the double reference to the same pair
//    private HashMap<Integer, Integer> checkDuplicate(HashMap<Integer, Integer> map) {
//        //initialize new map that is equal to the map to check
//        HashMap<Integer, Integer> newMap = new HashMap<Integer, Integer>();
//        //for every elements of map add it to newmap
//        for (Map.Entry<Integer, Integer> copy: map.entrySet()) {
//            newMap.put(copy.getKey(),copy.getValue());
//        }
//        //initialize new Arraylist done that contains the index already check
//        ArrayList<Integer> done = new ArrayList<Integer>();
//        //for every element in map
//        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
//            //initialize boolean variable ctrl that allow to delete element in map
//            boolean ctrl = true;
//            for (Integer d: done) {
//                //if the one of the copies is already delete, it won't do it again
//                if (entry.getKey().equals(d)) {
//                    ctrl = false;
//                    break;
//                }
//            }
//            if (ctrl) {
//                //for every element in map check if its key is equal to a value of another element and vice versa
//                for (Map.Entry<Integer, Integer> entry2: map.entrySet()) {
//                    if (entry.getKey().equals(entry2.getValue())) {
//                        if (entry.getValue().equals(entry2.getKey())) {
//                            //if both check are true one element is removed by newMap
//                            newMap.remove(entry2.getKey());
//                            done.add(entry2.getKey());
//                        }
//                    }
//                }
//            }
//        }
//        //the newMap is completed
//        return newMap;
//    }

}