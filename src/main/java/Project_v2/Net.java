package Project_v2;

import Utility.IO;


import java.util.*;


public class Net {
    public static final String INSERT_PLACE_S_ID = "Insert place's Name ";
    public static final String INSERT_TRANSITION_S_ID = "Insert transition's Name ";
    public static final String YOU_CAN_T_ADD_THIS_PAIR_BECAUSE_ALREADY_EXISTS = "You can't Add this pair because it already exists";
    public static final String YOU_WANT_ADD_ANOTHER_PAIR = "You want add another Pair?";

    private HashSet<Place> setOfPlace = new HashSet<Place>();
    private HashSet<Transition> setOfTrans = new HashSet<Transition>();
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


    /**
     * constructor for the net
     *
     * @param name the name that the user what to give to the net
     */
    public Net(String name) {
        this.name = name;
        this.ID = name + (++i); //DA FARE CONTROLLO SULL'ID
        addPair();
        // addPrePost();
    }

    public Net(String name, String id) {
        this.name = name;
        this.ID = id;
    }

    /**
     * this method allow to insert a new node, it is composed by a place and a transition
     */
    public void addPair() {
        boolean checkPlace = false, checkTrans = false;
        String placeName;
        String transName;
        int inOrOut;

        do {
            // ask to user the place's ID and the transition's ID
            placeName = IO.readNotEmpityString(INSERT_PLACE_S_ID);
            transName = IO.readNotEmpityString(INSERT_TRANSITION_S_ID);
            inOrOut = IO.readInteger("Which type of connection there is between the place " + placeName + "and the transition " + transName + "? \n 1)" + placeName + " is an INPUT of " + transName + "\n 2)" + placeName + " is an OUTPUT of " + transName + "\n", 1, 2);
            //this If check if the new node is equal to another one which is already in the net

            //I create a temporary transition and a temporary place because it makes easy to check them
            Transition t = new Transition(transName);
            Place p = new Place(placeName);


            //if the net is empty I don't recall the check method because this is useless
            if (net.size() == 0) {
                //with this if I check if the node is a in or an output
                if (inOrOut == 1) {
                    //if this is an in I add the place to the pre
                    t.addPre(placeName);
                } else {
                    //if this is an in I add the place to the p0st
                    t.addPost(placeName);
                }
                //we add the pair at the net because it is correct
                net.add(new Pair(p, t));

                setOfPlace.add(p);
                setOfTrans.add(t);

            } else {
                //I check if the pair is equal than an other one which already exists
                if (checkPair(new Pair(p, t)) == true) {
                    //with this if I check if the node is a in or an output
                    if (inOrOut == 1) {
                        //if this is an in I add the place to the pre
                        t.addPre(placeName);
                    } else {
                        //if this is an in I add the place to the post
                        t.addPost(placeName);
                    }
                    net.add(new Pair(p, t));
                    //this for looks for if the place already exist
                    for (Place pl : setOfPlace) {
                        if (placeName.compareTo(pl.getName()) == 0) {
                            checkPlace = true;
                        }
                    }
                    //this for looks for if the place already exist
                    for (Transition tr : setOfTrans) {
                        if (transName.compareTo(tr.getName()) == 0) {
                            if (inOrOut == 1) {
                                tr.addPre(p.getName());
                            } else {
                                tr.addPost(p.getName());
                            }
                            checkTrans = true;
                        }
                    }
                    // 1) if checkPlace and checkTrans are false it means that the places and transitions I want to add have not been found in the sets,
                    // 2) if checkPlace = false and checkTrans = true then it means that I have to add only the transitions in the set
                    // 3) if checkPlace = true and checkTrans = false then it means that I have to add only the place in the set
                    if (!checkPlace && !checkTrans) {
                        setOfTrans.add(t);
                        setOfPlace.add(p);
                    } else if (!checkPlace && checkTrans) {
                        setOfPlace.add(p);
                    } else if (checkPlace && !checkTrans) {
                        setOfTrans.add(t);
                    }
                    checkTrans = false;
                    checkPlace = false;

                } else {
                    //I say to the user that the pair already exists
                    System.out.println(YOU_CAN_T_ADD_THIS_PAIR_BECAUSE_ALREADY_EXISTS);
                }
            }
        } while (IO.yesOrNo(YOU_WANT_ADD_ANOTHER_PAIR));
    }


    public void addPairFromJson(Pair pair) {
        assert pair!=null;
        net.add(pair);
    }

    //metodo per tornare un posto dal set dato il nome
    public Place getPlace(String name) {
        for (Place p : setOfPlace) {
            if (name.compareTo(p.getName()) == 0) {
                return p;
            }
        }
        return null;
    }

    //metodo per tornare una transizione dal set dato il nome
    public Transition getTrans(String name) {
        for (Transition t : setOfTrans) {
            if (name.compareTo(t.getName()) == 0) {
                return t;
            }
        }
        return null;
    }


    /**
     * This metod check if two Pair are equal
     *
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

    //controllo dei nodi pendenti, da rivedere e commentare
    public boolean checkPendantNode() {
        for (int i = 0; i < net.size(); i++) {
            boolean check = false;
            String toCheck = net.get(i).getTransName();
            for (int j = 0; j < net.size(); j++) {
                if (i != j && toCheck.compareTo(net.get(j).getTransName()) == 0) {
                    check = true;
                }
            }
            if (check == false) {
                return false;
            }
        }
        return true;
    }




    /**
     * this algorithm checks if the graph is connect
     *
     * @return true if the net is connect
     */
    public boolean checkConnect() {

        String firstPlace = null;
        //HashMap di bool
        HashMap<String, Boolean> check = new HashMap<>();

        //this map to point out the graph's direction
        //Map per indicare le direzioni del grafo
        HashMap<String, ArrayList<String>> map = new HashMap<>();


        ArrayList<String> temp = new ArrayList<>();
        //this for fills both the boolean and the transitions maps,
        for (Place place : setOfPlace) {//this for checks all the place
            firstPlace = place.getName();
            check.put(place.getName(), false);
            //this for checks all the transition to see if the place is in its pre or post
            if (!map.containsKey(place.getName())) {
                for (Transition trans : setOfTrans) {
                    addTempArray(temp, trans.getIdPre(), trans.getIdPost(), place.getName());
                    /** Se trovo il mio posto di riferimento all'interno dell'array di predecessori di una transizione, ciclo i successori e li aggiungo
                     * al mio array temporaneo
                     *
                     * questo mi serve per riempire la mappa dei collegamenti;
                     * Infatti poi userò come chiave il posto di riferimento, e come valore i suoi successori.
                     */
                }
                //we insert the place that we have found and have linked to the pre/post in the map
                map.put(place.getName(), new ArrayList<>(temp));
                //map.put(place.getName(), tempPost);
                temp.clear();
                //tempPost.clear();
            } else {
                //If the key already exist  we add the new place to its array
                for (Transition trans : setOfTrans) {
                    addTempArray(temp, trans.getIdPre(), trans.getIdPost(), place.getName());
                }
                map.get(place.getName()).addAll(temp);
                temp.clear();
            }
        }
        //this for checks the place in the opposite direction because we want to add the place that we haven't seen yet and to update the information
        for (Place place : setOfPlace) {
            //if the key isn't in the map we go on without problems
            if (!map.containsKey(place.getName())) {
                for (Transition trans : setOfTrans) {
                    addTempArray(temp, trans.getIdPost(), trans.getIdPre(), place.getName());
                }
                map.put(place.getName(), new ArrayList<>(temp));
                temp.clear();
            } else {
                //If the key already exist  we add the new place to its array
                for (Transition trans : setOfTrans) {
                    addTempArray(temp, trans.getIdPost(), trans.getIdPre(), place.getName());
                }
                map.get(place.getName()).addAll(temp);
                temp.clear();
            }

        }
        //we start the recursion
        recursion(map, check, firstPlace);
        //we check the result of the recursion, if there is a false in the map this means that the place hasn't been check, so that isn't linked
        for (Place id : setOfPlace) {
            if (check.get(id.getName()) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method that allows me to fill a temporary array by cycling the firstArray and comparing it to PlaceName
     * In case an element in firstArray is equal to PlaceName then I will have to clone secondArray in Temp
     *
     * @param temp               is the resulting array
     * @param firstArrayOfPlace  is the arraylist that I compare with PlaceName
     * @param secondArrayOfPlace
     * @param placeName
     */
    public void addTempArray(ArrayList<String> temp, ArrayList<String> firstArrayOfPlace, ArrayList<String> secondArrayOfPlace, String placeName) {
        for (String name : firstArrayOfPlace) { //this for checks if that place is in the post of the transition
            if (name.equals(placeName)) {
                temp.addAll(secondArrayOfPlace);
                break;
            }
        }
    }


    /**
     * Recursive method that allows, starting from a random node, to visit all the nodes of the graph if it is connected.
     * Starting from the first node, it is marked as already visited on the boolmap, then it cycles on the neighbors and invoking this method again if they have not been visited.
     *
     * @param map     it is the map on which I perform the recursion to move from one place to its neighbors, it does not have to be empty
     * @param boolmap is the map in which I sign if the node has been visited or not, there must be all the places present in the network
     * @param key     is the key to the place to recursion, it does not have to be empty
     */
    public void recursion(HashMap<String, ArrayList<String>> map, HashMap<String, Boolean> boolmap, String key) {
        boolmap.replace(key, true);
        for (String nextKey : map.get(key)) {
            if (!boolmap.get(nextKey)) {
                recursion(map, boolmap, nextKey);
            }
        }

    }

    public boolean checkTrans() {
        for (Transition t : setOfTrans) {
            if (t.sizePre() == 0 || t.sizePost() == 0) {
                return false;
            }
        }
        return true;
    }

    public Transition researchTrans(String nameTrans){
        for(Transition t: getSetOfTrans()){
            if(t.getName().equals(nameTrans)){
                return  t;
            }
        }
    return null;
    }


    public Place researchPlace(String namePlace){
        for(Place p: getSetOfPlace()){
            if(p.getName().equals(namePlace)){
                return  p;
            }
        }
        return null;
    }

    public Pair researchPair(Transition t, Place p){
        for(Pair pair: getNet()){
            if(pair.getPlace().equals(p) && pair.getTrans().equals(t)){
                return  pair;
            }
        }
        return null;
    }


    /**
     * this method allows to fill the sets of places and transitions starting from an already existing net
     */
    public void fillSet() {
        int i;
        boolean check = false;
        for (i = 0; i < net.size(); i++) {
            for (Transition t : setOfTrans) {
                if (net.get(i).getTransName().compareTo(t.getName()) == 0) {
                    check = true;
                }
            }
            if(!check) setOfTrans.add(net.get(i).getTrans());
            check=false;
            for (Place p : setOfPlace) {
                if (net.get(i).getPlaceName().compareTo(p.getName()) == 0) {
                    check=true;
                }
            }
            if(!check)setOfPlace.add(net.get(i).getPlace());
            check=false;
        }
    }
    public HashSet<Place> getSetOfPlace() {
        return setOfPlace;
    }

    public HashSet<Transition> getSetOfTrans() {
        return setOfTrans;
    }

    public Net(Net _net) {
        net.addAll(_net.getNet());
        this.setOfPlace.addAll(_net.setOfPlace);
        this.setOfTrans.addAll(_net.setOfTrans);
        this.name = _net.getName();
        this.ID = _net.getID();
    }

}