package Utility;

import Project_v2.Net;
import Project_v2.Pair;
import Project_v2.Place;
import Project_v2.Transition;

import java.io.File;
import java.util.*;

public class IO {
    public static final String SET_NEW_NAME = "A network with this name already exists, please enter a different name";
    public static final String DO_YOU_WANT_TO_SAVE_THAT_PETRI_S_NET = "Do you want to save that Petri's Net?";
    public static final String DO_YOU_WANT_TO_ADD_TOKEN_TO_PLACE = "Do you want to add token to place ? ";
    public static final String THERE_AREN_T_ANY_FILES_TO_LOAD = "There aren't any files to load";
    public static final String WHICH_TYPE_OF_CONNECTION_THERE_IS_BETWEEN_THE_PLACE = "Which type of connection there is between the place";
    public static final String INSERT_PLACE_S_ID = "Insert place's Name ";
    public static final String INSERT_TRANSITION_S_ID = "Insert transition's Name ";
    public static final String YOU_CAN_T_ADD_THIS_PAIR_BECAUSE_ALREADY_EXISTS = "You can't Add this pair because it already exists";
    public static final String YOU_WANT_ADD_ANOTHER_PAIR = "You want add another Pair?";
    public static final String WRITING_FILE_ERROR = "writing file error.";
    public static final String TYPE_OF_NET = "Do you want load:\n1) simple net\n2) Petri Net\n" ;
    public static final String FILE_IS_LOADED = "File is loaded";
    public static final String VISUALIZE_THE_LIST = "Visualize the list";
    public static final String ADD_WEIGHT = "Do you want to add weight to the transition? If you say no we insert the default value";
    public static final String TRANSITION_CHOOSE = "These are the transition in the Net, do you have to choose which one modify: (insert the number)" ;
    private static final String path = new File("src/main/java/JsonFile").getAbsolutePath();
    private static final String petriPath = new File("src/main/java/JsonPetri").getAbsolutePath();
    public static final String ANOTHER_NET = "You want add another Net?";
    public static final String NAME_OF_NET = "Add the name of Net:";
    public static final String MENU = "What do you want do?\n0)EXIT\n1)Add new Net\n2)Load net\n3)Create a new Petri's Net";
    public static final String WANT_TO_DO_ANOTHER_OPERATION = "you want to do another operation ";
    public static final String SAVE_NET = "Do you want to save the net that you have already made? ";
    public static final String DIGIT_YOUR_CHOISE = "Digit your choise ";
    public static final String DIGIT_VALID_CHOISE = "Digit valid choise!";
    public static final String THE_NET_IS_INCORRECT_IT_CAN_T_BE_SAVED = "The net is incorrect, it can't be saved";
    public static final String THE_NET_IS_CORRECT_WE_ARE_GOING_TO_SAVE_IT = "The net is correct, we are going to save it";
    public static final String NO_NORMAL_NET = "There aren't any nets! You have to insert or load a net before adding a Petri Net";
    public static final String JSON_FILE = "src/main/java/JsonFile";
    public static final String JSON_PETRI_FILE = "src/main/java/JsonPetri";
    public static final String HOW_MANY_TOKEN = "How many tokens do you want this place to have?\n(if you don't want tokens enter 0)";
    private final static String ERROR_FORMAT = "Warning: the entered data are in the wrong format.";
    private final static String MINIMUM_ERROR = "Warning: the value must to be grater or equal to ";
    private final static String STRING_EMPTY_ERROR = "Warning: the string entered is empty";
    private final static String MAXIMUM_ERROR = "Warning: the value must to be lower or equal to ";
    private final static String MESSAGES_ALLOWED = "Warning, the value allowed are: ";
    private final static char YES_ANSWER = 'S';
    private final static char NO_ANSWER = 'N';

    private static Scanner reader = scannerBuild();

    public static void print(String s){
        System.out.println(s);
    }
    public static void printError(String s){
        System.err.println(s);
    }

    public static String readNotEmptyString(String message) {
        boolean finish = false;
        String read = null;
        do {
            read = ReadString(message);
            read = read.trim();
            if (read.length() > 0)
                finish = true;
            else
                print(STRING_EMPTY_ERROR);
        } while (!finish);

        return read;
    }

    public static boolean yesOrNo(String message) {
        String myMessage = message + "(" + YES_ANSWER + "/" + NO_ANSWER + ")";
        char readValue = readUpperChar(myMessage, String.valueOf(YES_ANSWER) + String.valueOf(NO_ANSWER));

        if (readValue == YES_ANSWER)
            return true;
        else
            return false;
    }

    public static double readDouble(String message) {
        boolean finish = false;
        double readValue = 0;
        do {
            print(message);
            try {
                readValue = reader.nextDouble();
                finish = true;
            } catch (InputMismatchException e) {
                print(ERROR_FORMAT);
                String toDelete = reader.next();
            }
        } while (!finish);
        return readValue;
    }

    public static int readInteger(String message, int min, int max) {
        boolean finish = false;
        int readValue = 0;
        do {
            readValue = readNumber(message);
            if (readValue >= min && readValue <= max)
                finish = true;
            else if (readValue < min)
                print(MINIMUM_ERROR + min);
            else
                print(MAXIMUM_ERROR + max);
        } while (!finish);

        return readValue;
    }

    public static int readIntegerWithMin(String message, int min) {
        boolean finish = false;
        int readValue = 0;
        do {
            readValue = readNumber(message);
            if (readValue >= min)
                finish = true;
            else
                print(MINIMUM_ERROR+min);
        } while (!finish);

        return readValue;
    }

    public static int readNumber(String message) {
        boolean finish = false;
        int readValue = 0;
        do {
            print(message);
            try {
                readValue = reader.nextInt();
                finish = true;
            } catch (InputMismatchException e) {
                print(ERROR_FORMAT);
                String toDelete = reader.next();
            }
        } while (!finish);
        return readValue;
    }

    public static char readUpperChar(String message, String allowed) {
        boolean finish = false;
        char readValue = '\0';
        do {
            readValue = leggiChar(message);
            readValue = Character.toUpperCase(readValue);
            if (allowed.indexOf(readValue) != -1)
                finish = true;
            else
                print(MESSAGES_ALLOWED+allowed);
        } while (!finish);
        return readValue;
    }

    public static char leggiChar(String message) {
        boolean finish = false;
        char readValue = '\0';
        do {
            print(message);
            String read = reader.next();
            if (read.length() > 0) {
                readValue = read.charAt(0);
                finish = true;
            } else {
                print(STRING_EMPTY_ERROR);
            }
        } while (!finish);
        return readValue;
    }

    private static Scanner scannerBuild() {
        Scanner created = new Scanner(System.in);
        //creato.useDelimiter(System.getProperty("line.separator"));
        created.useDelimiter(System.lineSeparator() + "|\n");
        return created;
    }

    public static String ReadString(String message) {
        print(message);
        return reader.next();
    }

    public static void printPlace(Iterable<Place> list){
        int i=1;

        for(Place p:list){
            IO.print(i+") "+ p.getName());
            i++;
        }

    }
    public static void printTransition(Iterable<Transition> list){
        int i=1;

        for(Transition t:list){
            IO.print(i+") "+ t.getName());
            i++;
        }
    }


    public static void printString(List<String> list) {
        for(int i=0; i<list.size();i++){
            IO.print((i+1)+") "+list.get(i));
        }
    }

    /**
     * method to view the net
     *
     * @param net
     */
    public static void showPetriNet(Net net) {
        //get name and if of the net
        String nameNet = net.getName();
        //initialize the places and transitions arraylist
        ArrayList<String> places = new ArrayList<String>();
        ArrayList<String> transitions = new ArrayList<String>();
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> weights = new ArrayList<>();
        ArrayList<Integer> directions = new ArrayList<>();

        //for every pair in the net get the name of place and name of transition
        for (Pair p : net.getNet()) {
            String place = p.getPlace().getName();
            String trans = p.getTrans().getName();
            String tokenPlace = Integer.toString(p.getPlace().getNumberOfToken());
            int direction = p.getTrans().getInputOutput(p.getPlace().getName());
            String weightPair = Integer.toString(p.getWeight());
            //add place to arraylist of places
            places.add(place);
            //add transition to arraylist of transitions
            transitions.add(trans);
            directions.add(direction);
            tokens.add(tokenPlace);
            weights.add(weightPair);
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
                        if (dir == 1) {
                            if (!JsonManager.existAlready(index, i, j)) {
                                index.put(i, j);
                                order.add(0);
                            }
                        } else {
                            if (!JsonManager.existAlready(index, i, j)) {
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
        for (Map.Entry<Integer, Integer> entry : index.entrySet()) {
            if (order.get(i) == 0) {
                couple = places.get(entry.getKey()) + " <" + tokens.get(i) + "> ----------<" + weights.get(i) + ">----------▶ " + transitions.get(entry.getValue());
            } else {
                couple = places.get(entry.getKey()) + " <" + tokens.get(i) + "> ◀︎----------<" + weights.get(i) + ">---------- " + transitions.get(entry.getValue());
            }
            //add the string to the arraylist
            couples.add(couple);
            i++;
        }

        //print the name and id and print all the pairs with their transition
        IO.print("\nName net: " + nameNet );
        IO.print("List pairs:");
        for (String s : couples) {
            IO.print("\t" + s);
        }
        IO.print("");

    }

    /**
     * method to view the net
     *
     * @param net
     */
    public static void showNet(Net net) {
        //get name and if of the net
        String nameNet = net.getName();
        //initialize the places and transitions arraylist
        ArrayList<String> places = new ArrayList<String>();
        ArrayList<String> transitions = new ArrayList<String>();
        ArrayList<Integer> directions = new ArrayList<>();

        //for every pair in the net get the name of place and name of transition
        for (Pair p : net.getNet()) {
            String place = p.getPlace().getName();
            String trans = p.getTrans().getName();
            int direction = p.getTrans().getInputOutput(p.getPlace().getName());
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
                        if (dir == 1) {
                            if (!JsonManager.existAlready(index, i, j)) {
                                index.put(i, j);
                                order.add(0);
                            }
                        } else {
                            if (!JsonManager.existAlready(index, i, j)) {
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
        for (Map.Entry<Integer, Integer> entry : index.entrySet()) {
            if (order.get(i) == 0) {
                couple = places.get(entry.getKey()) + "----->" + transitions.get(entry.getValue());
            } else {
                couple = places.get(entry.getKey()) + "<-----" + transitions.get(entry.getValue());
            }
            //add the string to the arraylist
            couples.add(couple);
            i++;
        }

        //print the name and id and print all the pairs with their transition
        IO.print("\nName net: " + nameNet );
        IO.print("List pairs:");
        for (String s : couples) {
            IO.print("\t" + s);
        }
        System.out.println();
    }

}
