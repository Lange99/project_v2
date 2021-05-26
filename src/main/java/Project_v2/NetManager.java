package Project_v2;

import Utility.IO;
import Utility.JsonManager;
import Utility.JsonReader;
import Utility.JsonWriter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetManager {


    public static final String WHAT_PLACE_YOU_WANT_CHANGE = "What place you want change?";
    public static final String INSERT_THE_WEIGHT_THAT_YOU_WANT_TO_GIVE_TO_THE_PLACE = "Insert the weight that you want to give to the place";
    public static final String DO_YOU_WANT_TO_ADD_OTHER_WEIGHTS_TO_THIS_TRANSITION = "Do you want to add other weights to this transition?";
    public static final String WHERE_DO_YOU_WANT_TO_ADD_THE_TOKENS = "where do you want to add the tokens?";
    public static final String INSERT_THE_NUMBER_OF_TOKENS = "Insert the number of tokens: ";
    public static final String THE_WEIGHT_HAS_BEEN_ADDED = "The weight has been added";
    public static final String THE_PLACE_DOESN_T_EXIST = "The place doesn't exist";
    private ArrayList<Net> netList = new ArrayList<Net>();
    /**
     * this method handles the interface with the user
     *
     * @throws FileNotFoundException
     */
    public void menageOption() throws IOException {
        boolean check = true;
        int choise = 0;
        do {
            IO.print(IO.MENU);
            choise = IO.readNumber(IO.DIGIT_YOUR_CHOISE);
            while (choise < 0 || choise > 4) {
                IO.print(IO.DIGIT_VALID_CHOISE);
                choise = IO.readNumber(IO.DIGIT_YOUR_CHOISE);
            }
            //this switch handles the different situation and it recalls the method for satisfy the user

            switch (choise) {
                //the program stops running
                case 0:
                    check = false;
                    break;
                //this method allows to the user to create a new net
                case 1:

                    addNet();
                    check = IO.yesOrNo(IO.WANT_TO_DO_ANOTHER_OPERATION);
                    break;

                case 2:
                    int typeNet = IO.readInteger(IO.TYPE_OF_NET, 1, 2);
                    if (typeNet == 1) {
                        Net newNet = JsonManager.loadNet(IO.JSON_FILE);
                        if (newNet != null) {
                            netList.add(newNet);
                            IO.showNet(newNet);
                        }
                    }
                    else {
                        Net newNet = JsonManager.loadNet(IO.JSON_PETRI_FILE);
                        if (newNet != null) {
                            netList.add(newNet);
                            IO.showNet(newNet);
                        }
                    }
                    check = IO.yesOrNo(IO.WANT_TO_DO_ANOTHER_OPERATION);

                    break;

                case 3:
                    if (netList.size() == 0) {
                        IO.print(IO.NO_NORMAL_NET);
                    } else {
                        addPetriNet();
                    }
                    break;

            }
        } while (check == true);

    }

    /**
     * this method creates a new Petri's Net
     */
    public void addPetriNet() {
        PetriNet newPetriNet = new PetriNet(loadOneNet());
        //we add the token to the place
        while (IO.yesOrNo(IO.DO_YOU_WANT_TO_ADD_TOKEN_TO_PLACE)){
            addTokentoPetriNet(newPetriNet);
        }


        //we add the weight to the transition
        addWeightToPetriNet(newPetriNet);

        //we write the new net in a file Json
        JsonWriter.writeJsonPetri(newPetriNet);
        netList.add(newPetriNet);
    }

    /**
     * This method allows to add weight at the transition of the Petri's Net
     * @param newPetriNet the net that we want to modify
     */
    private void addWeightToPetriNet(PetriNet newPetriNet) {
        assert newPetriNet!=null;
        while (IO.yesOrNo(IO.ADD_WEIGHT)) {
            ArrayList<Transition> transTemp = new ArrayList<>(newPetriNet.getSetOfTrans());

            //we choose which transition we want to modify
            IO.printTransition(transTemp);
            int choose = IO.readInteger(IO.TRANSITION_CHOOSE, 0, transTemp.size())-1;

            //we show the pre and post place
            ArrayList<String> placeTemp = new ArrayList<>();
            placeTemp.addAll(transTemp.get(choose).getIdPre());
            placeTemp.addAll(transTemp.get(choose).getIdPost());

            do {


                IO.printString(placeTemp);

                //we ask which one place they want to change
                String placeToChange = placeTemp.get(IO.readInteger(WHAT_PLACE_YOU_WANT_CHANGE, 0, placeTemp.size())-1);
                //we ask the weight that they want to give to the place
                int weight = IO.readIntegerWithMin(INSERT_THE_WEIGHT_THAT_YOU_WANT_TO_GIVE_TO_THE_PLACE, 0);
                //we modify the weight in the correct place
                newPetriNet.addWeight(transTemp.get(choose).getName(), placeToChange, weight);
            }while(IO.yesOrNo(DO_YOU_WANT_TO_ADD_OTHER_WEIGHTS_TO_THIS_TRANSITION));



        }

    }

    /**
     * this method allows to insert the token in the place that the user has choosen
     * @param newPetriNet the net that should be modified
     */
    private void addTokentoPetriNet(PetriNet newPetriNet) {
    assert  newPetriNet!=null;

        ArrayList<Place> tempPlace = new ArrayList<>(newPetriNet.getSetOfPlace());

        //we print all the place in order to show them to the user, and then he can choose what he want to modify
        IO.printPlace(newPetriNet.getSetOfPlace());
        //we ask which place the user wants to modify and the tokens that he wants to add
        int choise = IO.readInteger(WHERE_DO_YOU_WANT_TO_ADD_THE_TOKENS, 0, tempPlace.size());
        int token = IO.readIntegerWithMin(INSERT_THE_NUMBER_OF_TOKENS, 0);


        String  placeId = tempPlace.get(choise-1).getName();

        if(newPetriNet.addToken(placeId, token)){
            IO.print(THE_WEIGHT_HAS_BEEN_ADDED);
        }else{
            IO.print(THE_PLACE_DOESN_T_EXIST);
        }



    }

    /**
     * this method allows to add a net
     */
    public void addNet() {

        String placeName;
        String transName;
        int inOrOut;

        do {
            Net n = new Net(IO.readNotEmptyString(IO.NAME_OF_NET));
            do {
                // ask to user the place's ID and the transition's ID
                placeName = IO.readNotEmptyString(IO.INSERT_PLACE_S_ID);
                transName = IO.readNotEmptyString(IO.INSERT_TRANSITION_S_ID);
                inOrOut = IO.readInteger(IO.WHICH_TYPE_OF_CONNECTION_THERE_IS_BETWEEN_THE_PLACE + placeName + "and the transition " + transName + "? \n 1)" + placeName + " is an INPUT of " + transName + "\n 2)" + placeName + " is an OUTPUT of " + transName + "\n", 1, 2);
                //this If check if the new node is equal to another one which is already in the net

                //I create a temporary transition and a temporary place because it makes easy to check them
                Transition t = new Transition(transName);
                Place p = new Place(placeName);
                if(!n.addPair(t, p, inOrOut)){
                    System.out.println(IO.YOU_CAN_T_ADD_THIS_PAIR_BECAUSE_ALREADY_EXISTS);
                }
            } while (IO.yesOrNo(IO.YOU_WANT_ADD_ANOTHER_PAIR));
            //if the new net is correct we show it to the user and ask if he wants to save it
            if (checkNet(n) && n.checkTrans() && n.checkConnect() && checkEqualNet(n)) {
                IO.showNet(n);
                IO.print(IO.THE_NET_IS_CORRECT_WE_ARE_GOING_TO_SAVE_IT);

                netList.add(n);

                if (IO.yesOrNo(IO.SAVE_NET)) {
                    JsonWriter.writeJsonFile(n);
                }
            } else {
                //if the net is incorrect we inform the user
                IO.printError(IO.THE_NET_IS_INCORRECT_IT_CAN_T_BE_SAVED);

            }
        } while (IO.yesOrNo(IO.ANOTHER_NET));
    }

    /**
     * the method check if there is only a place connect to a transition
     *
     * @param n the net we have to check
     * @return false if there are some problems and if there is one or more pendant connection
     */
    public boolean checkNet(Net n) {
        assert n != null;
//if there is a problem the method return false
        if (n.checkPendantNode() == false) {

            return false;
        } else {
            return true;
        }
    }

    //metodo che stampa tutte le net in netlist e ne restituisce una scelta dall'utente
    public Net loadOneNet() {

        for (int i = 0; i < netList.size(); i++) {
            System.out.println(i + ") " + netList.get(i).getName());
        }
        int choise = IO.readInteger("choose the network number ", 0, netList.size());
        return netList.get(choise);
    }

    /**
     * this method check if the net already exists and that can't be saved
     *
     * @param netToCheck the net that should be check
     * @return true if that net already exists and false if it doesn't
     * @throws FileNotFoundException PRECONDITION: NetToCheck!=null
     */
    private boolean checkEqualNet(Net netToCheck) throws FileNotFoundException {
        assert netToCheck != null;
        //initialize the File object directory
        File directory = new File("src/main/Json");
        //initialize the string that contains the list of name file
        String[] pathname = directory.list();
        int dim;
        if (pathname != null)
            dim = pathname.length;
        else {
            return true;
        }

        ArrayList<Pair> pairsNewNet = netToCheck.getNet();
        int ctrl = 0;
        //consider all files in directory
        for (int i = 0; i < dim; i++) {
            if (ctrl == pairsNewNet.size()) {
                return false;
            }
            ctrl = 0;
            //get pathname of the file
            String path = IO.JSON_FILE+"/" + pathname[i];
            //build a net by the file
            Net net = JsonReader.readJson(path);
            //get all pairs of the net
            ArrayList<Pair> pairsOldNet = net.getNet();

            //if the size is equal chek, else change file
            if (pairsOldNet.size() == pairsNewNet.size()) {
                int j = 0;
                //for every pair in the new net, take every pair of the pre existing net and check
                for (Pair newPair : pairsNewNet) {
                    if (ctrl < j)
                        break;
                    for (Pair oldPair : pairsOldNet) {
                        if (newPair.getPlaceName().equals(oldPair.getPlaceName())) {
                            if (newPair.getPlaceName().equals(oldPair.getPlaceName())) {
                                ctrl++;
                                break;
                            }
                        }
                    }
                    j++;
                }
            }
        }
        return true;
    }


}