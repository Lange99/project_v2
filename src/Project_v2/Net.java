package Project_v2;

import Utility.Reader;

import java.util.ArrayList;

public class Net {
    public static final String INSERT_PLACE_S_ID = "Insert place's Id ";
    public static final String INSERT_TRANSITION_S_ID = "Insert transition's Id ";
    public static final String YOU_CAN_T_ADD_THIS_PAIR_BECAUSE_ALREADY_EXISTS = "You can't Add this pair because it already exists";
    public static final String YOU_WANT_ADD_ANOTHER_PAIR = "You want add another Pair?";

    private ArrayList<Pair> net = new ArrayList<Pair>();
    private String ID;
    private String name;
    private static int i;

    public ArrayList<Pair> getNet() {
        return net;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * constructor for the net
     * @param name the name that the user what to give to the net
     */
    public Net(String name) {
        this.name = name;
        this.ID = "name" + (++i); //DA FARE CONTROLLO SULL'ID
        addPair();
    }

    public Net(String name, String id) {
        this.name = name;
        this.ID = id;
    }

    public Net(Net _net){
        net.addAll(_net.getNet());
    }



    /**
     * this method allow to insert a new node, it is composed by a place and a transition
     */
    public void addPair() {
        String placeId;
        String transId;
        do {
            // ask to user the place's ID and the transition's ID
            placeId = Reader.readNotEmpityString(INSERT_PLACE_S_ID);
            transId = Reader.readNotEmpityString(INSERT_TRANSITION_S_ID);
            //this If check if the new node is equal to another one which is already in the net
            if (net.size() == 0) {
                net.add(new Pair(placeId, transId));
            } else {
                if (checkPair(new Pair(placeId, transId)) == true) {
                    net.add(new Pair(placeId, transId));
                } else {
                    System.out.println(YOU_CAN_T_ADD_THIS_PAIR_BECAUSE_ALREADY_EXISTS);
                }
            }
        } while (Reader.yesOrNo(YOU_WANT_ADD_ANOTHER_PAIR));
    }

    public void addPairFromJson(Pair pair) {
        net.add(pair);
    }

    /**
     * This metod check if two Pair are equal
     * @param pairToCheck
     * @return false if Pair are equal
     */
    public boolean checkPair(Pair pairToCheck) {
        for (Pair element : net) {
            if (element.compare(pairToCheck) == true) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPendantNode() {
        for (int i = 0; i < net.size(); i++) {
            boolean check = false;
            String toCheck = net.get(i).getTrans().getId();
            for (int j = 0; j < net.size(); j++) {
                if (i != j && toCheck.compareTo(net.get(j).getTrans().getId()) == 0) {
                    check = true;
                }
            }
            if (check == false) {
                return false;
            }
        }
        return true;
    }


}
