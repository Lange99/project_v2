package Project_v2;

import java.util.Scanner;

public class IO {

    public static final String INSERT_PLACE_S_ID = "Insert place's Name ";
    public static final String WHICH_TYPE_OF_CONNECTION_THERE_IS_BETWEEN_THE_PLACE = "Which type of connection there is between the place ";

    public static final String INSERT_TRANSITION_S_ID = "Insert transition's Name ";
    public static final String ANOTHER_NET = "You want add another Net?";
    public static final String NAME_OF_NET = "Add the name of Net:";
    public static final String MENU = "What do you want do?\n0)EXIT\n1)Add new Net\n2)Load net";
    public static final String WANT_TO_DO_ANOTHER_OPERATION = "you want to do another operation ";
    public static final String DIGIT_YOUR_CHOISE = "Digit your choise ";
    public static final String DIGIT_VALID_CHOISE = "Digit valid choise!";
    public static final String THE_NET_IS_INCORRECT_IT_CAN_T_BE_SAVED = "The net is incorrect, it can't be saved";
    public static final String THE_NET_IS_CORRECT_WE_ARE_GOING_TO_SAVE_IT = "The net is correct, we are going to save it";
    public static final String SAVE_NET = "Do you want to save the net that you have already made? ";
    public static final String GOOD_BYE = "GoodBye";
    public static final String INSERT_THE_ID_OF_THE_FILE_YOU_WANT_TO_LOAD = "Insert the id of the file you want to load ";
    public static final String FILE_IS_LOADED = "File is loaded";
    public static final String SHOW_THE_NET = "Show the net";
    public static final String THERE_AREN_T_ANY_FILES_TO_LOAD = "There aren't any files to load";
    public static final String YOU_WANT_ADD_ANOTHER_PAIR = "You want add another Pair?";
        public static final String NO_NORMAL_NET = "There aren't any nets! You have to insert or load a net before adding a Petri Net";
    public static final String JSON_FILE = "src/main/java/JsonFile";
    public static final String JSON_PETRI_FILE = "src/main/java/JsonPetri";
    public static final String DISPLAY_OF_THE_LIST = "Display of the list";
    public static final String LIST_PAIRS ="List pairs:" ;

    public static void print(String s){
        System.out.println(s);
    }
    public static void printError(String s){
        System.err.println(s);
    }


    private static Scanner creaScanner() {
        Scanner creato = new Scanner(System.in);
       // creato.useDelimiter(System.getProperty("line.separator"));
        creato.useDelimiter(System.lineSeparator() + "|\n");return creato;
    }
}
