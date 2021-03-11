package Project_v2;
import Utility.JsonReader;
import Utility.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NetManager {
    public static final String ANOTHER_NET = "You want add another Net?";
    public static final String NAME_OF_NET = "Add the name of Net:";
    public static final String MENU = "What do you want do?\n0)EXIT\n1)Add new Net\n" +/*"2)Check the net\n3)Save Net\n"+*/"2)Load net\n3) Visualize a net\n4)add a Petri's net";
    public static final String WANT_TO_DO_ANOTHER_OPERATION = "you want to do another operation ";
    public static final String DIGIT_YOUR_CHOISE = "Digit your choise ";
    public static final String DIGIT_VALID_CHOISE = "Digit valid choise!";
    public static final String THE_NET_IS_INCORRECT = "The net is incorrect, it can't be saved";
    public static final String THE_NET_IS_CORRECT = "The net is correct, we are going to save it";

    private ArrayList<Net> netList = new ArrayList<Net>();

    public void menageOption() throws FileNotFoundException {
        boolean check = true;
        int choise = 0;
        do {
            System.out.println(MENU);
            choise = Reader.readNumber(DIGIT_YOUR_CHOISE);
            while (choise < 0 || choise > 4) {
                System.out.println(DIGIT_VALID_CHOISE);
                choise = Reader.readNumber(DIGIT_YOUR_CHOISE);
            }

            switch (choise) {
                case 0:
                    check = false;
                    break;
                case 1:
                    addNet();
                    check = Reader.yesOrNo(WANT_TO_DO_ANOTHER_OPERATION);
                    break;
                case 2:
                    loadNet();
                    check = Reader.yesOrNo(WANT_TO_DO_ANOTHER_OPERATION);
                    break;
//               case 2:
//                    checkNet();
//                    check=Reader.yesOrNo(WANT_TO_DO_ANOTHER_OPERATION);
//                    break;
//
                case 3:
                    //TODO

                    printNet();
                    check = Reader.yesOrNo(WANT_TO_DO_ANOTHER_OPERATION);
                    break;

                case 4:
                    addPetriNet();
                    check = Reader.yesOrNo(WANT_TO_DO_ANOTHER_OPERATION);
                    break;


            }
        } while (check == true);

    }

    /**
     * this method allows to add a new net
     */
    public void addNet() {
        do {
            //the name is required to use the constructor where there is the request to insert the pairs
            Net n = new Net(Reader.ReadString(NAME_OF_NET));
            //We check if the net has pendant pair or not
            if (checkNet(n)) {
                netList.add(n);
            }
        } while (Reader.yesOrNo(ANOTHER_NET));
    }

    /**
     * this method allows to add a new net
     */
    public void addPetriNet() {
        //do {
            //todo: QUESTA COSA MI SERVE PER DEBUGGARE, E' DA FARE BENE
            //the name is required to use the constructor where there is the request to insert the pairs
            int num = 0;
            String nameNet;
            if (netList.size() == 0) {
                System.out.println("There aren't any nets");
            } else {
                for (int i = 0; i < netList.size(); i++) {
                    System.out.println((i + 1) + ") " + netList.get(i).getName());
                }
                nameNet=Reader.readNotEmpityString("write the name");
                //TODO: check se la rete esiste
                for (Net net: netList) {
                    if(net.getName().compareTo(nameNet)==0){
                        netList.add(new PetriNet(net, net.getName()));
                    }
                }



            }
        //} while (Reader.yesOrNo(ANOTHER_NET));
    }

    /**
     * @param n this is the net we have to check
     * @return true if the net is correct and it doesn't have pendant pair
     */
    public boolean checkNet(Net n) {
        //we call the method in the Net class which check if there are some pendant pairs
        if (n.checkPendantNode() == false) {

            System.out.println(THE_NET_IS_INCORRECT);
            return false;
        } else {
            //if the net is correct, we show it to the user
            showNet(n);
            System.out.println(THE_NET_IS_CORRECT);
            return true;
        }

    }

    private void printNet() {

        int num = 0;
        if (netList.size() == 0) {
            System.out.println("There aren't any nets");
        } else {

            for (int i = 0; i < netList.size(); i++) {
                System.out.println((i + 1) + ") " + netList.get(i).getName());
            }
            num = Reader.leggiIntero("Insert the number of which net do you want to visualize?", 1, netList.size() + 1);
            showNet(netList.remove(num - 1));
        }
    }

    public void loadNet() throws FileNotFoundException {
        File directory = new File("src/main/java/Json");
        String[] pathname = directory.list();
        int i = 0;
        for (String s : pathname) {
            i++;
            System.out.println(i + ") " + s);
        }
        if (i == 0) {
            System.out.println("There aren't any files to load");
        } else {
            int number = Reader.leggiIntero("Insert the id of the file you want to load ", 1, i);
            String path = "src/main/java/Json/" + pathname[number - 1];
            Net newNet = JsonReader.readJson(path);
            netList.add(newNet);
            System.out.println("File is loaded");
            System.out.println("Visualizzazione della lista");
            showNet(newNet);
        }
    }

    //JACK COMMENTA IL CODICE
    public void showNet(Net net) {
        String nameNet = net.getName();
        String idNet = net.getID();
        ArrayList<String> places = new ArrayList<>();
        ArrayList<String> transitions = new ArrayList<>();

        for (Pair p : net.getNet()) {
            String place = p.getPlace().getId();
            String trans = p.getTrans().getId();
            places.add(place);
            transitions.add(trans);
        }

        HashMap<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i < transitions.size(); i++) {
            for (int j = 0; j < transitions.size(); j++) {
                if (i != j) {
                    if (transitions.get(i).equals(transitions.get(j))) {
                        index.put(i, j);
                    }
                }
            }
        }
        HashMap<Integer, Integer> indexUpdate = checkDuplicate(index);
        ArrayList<String> couples = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : indexUpdate.entrySet()) {
            String couple = places.get(entry.getKey()) + "-----" + transitions.get(entry.getKey()) + "-----" + places.get(entry.getValue());
            couples.add(couple);
        }

        System.out.println("\nName net: " + nameNet + "\tID net: " + idNet);
        System.out.println("List pairs:");
        for (String s : couples) {
            System.out.println("\t" + s);
        }
        System.out.println();
    }

    private HashMap<Integer, Integer> checkDuplicate(HashMap<Integer, Integer> map) {
        HashMap<Integer, Integer> newMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> copy : map.entrySet()) {
            newMap.put(copy.getKey(), copy.getValue());
        }
        ArrayList<Integer> done = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            boolean ctrl = true;
            for (Integer d : done) {
                if (entry.getKey().equals(d)) {
                    ctrl = false;
                    break;
                }
            }
            if (ctrl) {
                for (Map.Entry<Integer, Integer> entry2 : map.entrySet()) {
                    if (entry.getKey().equals(entry2.getValue())) {
                        if (entry.getValue().equals(entry2.getKey())) {
                            newMap.remove(entry2.getKey());
                            done.add(entry2.getKey());
                        }
                    }
                }
            }
        }
        return newMap;
    }
}
