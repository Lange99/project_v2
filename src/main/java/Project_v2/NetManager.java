package Project_v2;

import Utility.IO;
import Utility.JsonManager;
import Utility.JsonReader;
import Utility.JsonWriter;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NetManager {

    private final ArrayList<Net> netList = new ArrayList<Net>();
    private final ArrayList<PetriNet> petriNetList = new ArrayList<PetriNet>();

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
                    } else {
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
     * Method that compares a petri net with those saved in the PetriNetList
     *
     * @param net is the net to check;
     * @return false if two net are equal
     */
    public boolean checkPetriNet(PetriNet net)  {
        try {
            if(existsAlreadyPetriNet(net)){
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (PetriNet n : petriNetList) {
            if (net.equals(n)) {
                return false;
            }
        }
        return true;
    }

    //Metodo per la creazione di petri net;
    public void addPetriNet() {
        PetriNet newPetriNet = new PetriNet(loadOneNet());
        newPetriNet.setName(IO.NAME_OF_NET);
        while (!checkPetriNetName(newPetriNet.getName())){
            IO.print(IO.SET_NEW_NAME);
            newPetriNet.setName(IO.readNotEmptyString(IO.NAME_OF_NET));
        }
        //we add the token to the place
        while (IO.yesOrNo(IO.DO_YOU_WANT_TO_ADD_TOKEN_TO_PLACE)) {
            addTokentoPetriNet(newPetriNet);
        }

        //we add the weight to the transition
        addWeightToPetriNet(newPetriNet);


        if (checkPetriNet(newPetriNet)) {
            if (IO.yesOrNo(IO.DO_YOU_WANT_TO_SAVE_THAT_PETRI_S_NET)) {
                JsonWriter.writeJsonPetri(newPetriNet);
            }
            petriNetList.add(newPetriNet);
        }
    }

    private void addWeightToPetriNet(PetriNet newPetriNet) {

        while (IO.yesOrNo(IO.ADD_WEIGHT)) {
            ArrayList<Transition> transTemp = new ArrayList<>(newPetriNet.getSetOfTrans());

            //qua scelgo la transizione da modificare
            IO.printTransition(transTemp);
            int choose = IO.readInteger(IO.TRANSITION_CHOOSE, 0, transTemp.size()) - 1;

            //qui mostro i pre e i post della transizione collegata
            ArrayList<String> placeTemp = new ArrayList<>();
            placeTemp.addAll(transTemp.get(choose).getIdPre());
            placeTemp.addAll(transTemp.get(choose).getIdPost());

            do {
                IO.printString(placeTemp);
                //qui chiedo quale transazione-posto vuole modificare
                String placeToChange = placeTemp.get(IO.readInteger("What place you want change?", 0, placeTemp.size()) - 1);
                int weight = IO.readIntegerWithMin("Insert the weight that you want to give to the place", 0);
                newPetriNet.addWeight(transTemp.get(choose).getName(), placeToChange, weight);
            } while (IO.yesOrNo("Do you want to add other weights to this transition?"));


        }

    }

    private void addTokentoPetriNet(PetriNet newPetriNet) {

        ArrayList<Place> tempPlace = new ArrayList<>(newPetriNet.getSetOfPlace());


        IO.printPlace(newPetriNet.getSetOfPlace());
        int choise = IO.readInteger("where do you want to add the tokens?", 0, tempPlace.size());
        int token = IO.readIntegerWithMin("Insert the number of tokens: ", 0);

        String placeId = tempPlace.get(choise - 1).getName();

        if (newPetriNet.addToken(placeId, token)) {
            IO.print("The weight has been added");
        } else {
            IO.print("The place doesn't exist");
        }
    }

    /**
     * this method allows to add a net
     */
    public void addNet() throws FileNotFoundException {
        String placeName;
        String transName;
        int inOrOut;

        do {
            Net n = new Net(IO.readNotEmptyString(IO.NAME_OF_NET));
            while (!checkNetName(n.getName())){
                IO.print(IO.SET_NEW_NAME);
                n.setName(IO.readNotEmptyString(IO.NAME_OF_NET));
            }
            do {
                // ask to user the place's ID and the transition's ID
                placeName = IO.readNotEmptyString(IO.INSERT_PLACE_S_ID);
                transName = IO.readNotEmptyString(IO.INSERT_TRANSITION_S_ID);
                inOrOut = IO.readInteger(IO.WHICH_TYPE_OF_CONNECTION_THERE_IS_BETWEEN_THE_PLACE + placeName + "and the transition " + transName + "? \n 1)" + placeName + " is an INPUT of " + transName + "\n 2)" + placeName + " is an OUTPUT of " + transName + "\n", 1, 2);
                //this If check if the new node is equal to another one which is already in the net

                //I create a temporary transition and a temporary place because it makes easy to check them
                Transition t = new Transition(transName);
                Place p = new Place(placeName);
                if (!n.addPair(t, p, inOrOut)) {
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
        //if there is a problem the method return false
        return n.checkPendantNode();
    }

    //metodo che stampa tutte le net in netlist e ne restituisce una scelta dall'utente
    public Net loadOneNet() {

        for (int i = 0; i < netList.size(); i++) {
            IO.print(i + ") " + netList.get(i).getName());

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
            String path = IO.JSON_FILE + "/" + pathname[i];
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

    /**
     * Method to check if the petri net insert exist already or is new and it is possible add it
     * @param newPetriNetToCheck
     * @return true if there is already the same net, false if there isn't
     * @throws FileNotFoundException
     */
    public boolean existsAlreadyPetriNet(PetriNet newPetriNetToCheck) throws FileNotFoundException {
        assert newPetriNetToCheck != null;
        // bulld array String of the list of all file in JsonPetri directory
        String[] pathname = JsonManager.getPathname(IO.JSON_PETRI_FILE);
        //initialize StringBuilder object
        String stringOfNewPetriNetToCheck = JsonWriter.stringPetriNet(newPetriNetToCheck);

        for (String pathnameOfFileToCheck: pathname) {
            //initialize Scanner object
            Scanner sc = new Scanner(new File(pathnameOfFileToCheck));
            //initialize StringBuilder object
            StringBuilder sbExitingPetriNet = new StringBuilder();
            //while Scanner detect new line append to StringBuilder object the line of json file
            while (sc.hasNextLine()) {
                sbExitingPetriNet.append(sc.nextLine()).append("\n");
            }
            String stringExistingPetriNet = sbExitingPetriNet.toString();
            if (stringExistingPetriNet.equals(stringOfNewPetriNetToCheck)) {
                return true;
            }
        }
        return false;
    }
    /* * Method that allows you to check that the name of a network is not the same as the existing networks
     * @param netName is the name of the Net
     * @return true if there are no networks with this name
     */
    public boolean checkNetName(String netName) {
        for (Net n : netList) {
            if (n.getName().equals(netName)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Method that allows you to check that the name of a Petri's network is not the same as the existing Petri's networks
     * @param petriNetName is the name of the Petri's Net
     * @return true if there are no Petri's net with this name
     */
    public boolean checkPetriNetName(String petriNetName) {
        for (PetriNet n : petriNetList) {
            if (n.getName().equals(petriNetName)) {
                return false;
            }
        }
        return true;
    }

}
