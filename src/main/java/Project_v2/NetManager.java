package main.java.Project_v2;

import main.java.Utility.*;

import java.io.IOException;
import java.util.ArrayList;


public class NetManager {



    private ArrayList<Net> netList = new ArrayList<Net>();

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

            switch (choise) {
                case 0:
                    check = false;
                    break;
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


    //Metodo per la creazione di petri net;
    public void addPetriNet() {
        PetriNet newPetriNet = new PetriNet(loadOneNet());
        //we add the token to the place
        while (IO.yesOrNo(IO.DO_YOU_WANT_TO_ADD_TOKEN_TO_PLACE)){
            addTokentoPetriNet(newPetriNet);
        }


        //we add the weight to the transition
        addWeightToPetriNet(newPetriNet);


        JsonWriter.writeJsonPetri(newPetriNet);
        netList.add(newPetriNet);
    }

    private void addWeightToPetriNet(PetriNet newPetriNet) {

        while (IO.yesOrNo(IO.ADD_WEIGHT)) {
            ArrayList<Transition> transTemp = new ArrayList<>(newPetriNet.getSetOfTrans());

            //qua scelgo la transizione da modificare
            IO.printTransition(transTemp);
            int choose = IO.readInteger(IO.TRANSITION_CHOOSE, 0, transTemp.size())-1;

            //qui mostro i pre e i post della transizione collegata
            ArrayList<String> placeTemp = new ArrayList<>();
            placeTemp.addAll(transTemp.get(choose).getIdPre());
            placeTemp.addAll(transTemp.get(choose).getIdPost());

            do {


                IO.printString(placeTemp);

                //qui chiedo quale transazione-posto vuole modificare
                String placeToChange = placeTemp.get(IO.readInteger(IO.WHAT_PLACE_YOU_WANT_CHANGE, 0, placeTemp.size())-1);
                int weight = IO.readIntegerWithMin(IO.INSERT_THE_WEIGHT_THAT_YOU_WANT_TO_GIVE_TO_THE_PLACE, 0);
                newPetriNet.addWeight(transTemp.get(choose).getName(), placeToChange, weight);
            }while(IO.yesOrNo(IO.DO_YOU_WANT_TO_ADD_OTHER_WEIGHTS_TO_THIS_TRANSITION));



        }

    }

    private void addTokentoPetriNet(PetriNet newPetriNet) {

        ArrayList<Place> tempPlace = new ArrayList<>(newPetriNet.getSetOfPlace());


        IO.printPlace(newPetriNet.getSetOfPlace());
        int choise = IO.readInteger(IO.WHERE_DO_YOU_WANT_TO_ADD_THE_TOKENS, 0, tempPlace.size());
        int token = IO.readIntegerWithMin(IO.INSERT_THE_NUMBER_OF_TOKENS, 0);

        String  placeId = tempPlace.get(choise).getName();

        if(newPetriNet.addToken(placeId, token)){
            IO.print(IO.THE_WEIGHT_HAS_BEEN_ADDED);
        }else{
            IO.print(IO.THE_PLACE_DOESN_T_EXIST);
        }


    }

    /**
     * this method allows to add a net
     */
    public void addNet() {

        do {
            Net n = new Net(IO.readNotEmpityString(IO.NAME_OF_NET));
            //if the new net is correct we show it to the user and ask if he wants to save it
            if (checkNet(n) && n.checkTrans() && n.checkConnect()) {
                IO.showNet(n);
                IO.print(IO.THE_NET_IS_CORRECT_WE_ARE_GOING_TO_SAVE_IT);
                netList.add(n);

                if (IO.yesOrNo(IO.SAVE_NET)) {
                    JsonWriter.writeJsonFile(n);
                }
            } else {
                //if the net is incorrect we inform the user
                IO.print(IO.THE_NET_IS_INCORRECT_IT_CAN_T_BE_SAVED);
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
        if (n.checkPendantNode() == false) {

            return false;
        } else {
            return true;
        }
    }

    //metodo che stampa tutte le net in netlist e ne restituisce una scelta dall'utente
    public Net loadOneNet() {
        IO.printNet(netList);
        /*for (int i = 0; i < netList.size(); i++) {
            System.out.println(i + ") " + netList.get(i).getName());
        }*/
        int choise = IO.readInteger(IO.CHOOSE_THE_NETWORK_NUMBER_, 0, netList.size());
        return netList.get(choise);
    }

}