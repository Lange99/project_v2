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



    public ArrayList<Pair> getNet() {
        assert net !=null;
        return net;
    }


    public String getName() {
        assert name != null;
        return name;
    }


    /**
     * constructor for the net
     *
     * @param name the name that the user what to give to the net
     */
    public Net(String name) {
        assert name!=null;
        this.name = name;

    }

    /**
     * this method allow to insert a new node, it is composed by a place and a transition
     */

    public boolean addPair(Transition t, Place p, int inOrOut) {
        assert t != null;
        assert p != null;
        assert inOrOut >= 0;
        assert inOrOut <= 1;

        //if the net is empty I don't recall the check method because this is useless
        if (net.size() == 0) {
            //with this if I check if the node is a in or an output
            if (inOrOut == 1) {
                //if this is an in I add the place to the pre
                t.addPre(p.getName());
            } else {
                //if this is an in I add the place to the p0st
                t.addPost(p.getName());
            }
            //we add the pair at the net because it is correct
            net.add(new Pair(p, t));

            setOfPlace.add(p);
            setOfTrans.add(t);

        } else {

            //I check if the pair is equal than an other one which already exists
            if (checkPair(new Pair(p, t))) {

                if (setOfTrans.add(t)) {
                    if (inOrOut == 1) {
                        //if this is an in I add the place to the pre
                        t.addPre(p.getName());
                    } else {
                        //if this is an in I add the place to the post
                        t.addPost(p.getName());
                    }
                } else {
                    for (Transition tr : setOfTrans) {
                        if (t.getName().equals(tr.getName())) {
                            if (inOrOut == 1) {
                                tr.addPre(p.getName());
                            } else {
                                tr.addPost(p.getName());
                            }
                        }
                    }
                }
                net.add(new Pair(p, t));
                //this for looks for if the place already exist
                setOfPlace.add(p);


            } else {
                //I say to the user that the pair already exists
                return false;
            }
        }
        return true;
    }


    public void addPairFromJson(Pair pair) {
        assert pair!=null;
        net.add(pair);
    }

    /**
     * @param name this method returns a place from the set knowing the name
     * @return the place if it finds it, null if the place doesn't exist
     */
    //metodo per tornare un posto dal set dato il nome
    public Place getPlace(String name) {
        assert name!=null && setOfPlace!=null;
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
        assert pairToCheck.compare(null);
        for (Pair element : net) {
            if (element.compare(pairToCheck) == true) {
                return false;
            }
        }
        return true;
    }
    /**
     * this method checks if there are some pendant place
     *
     * @return true if there aren't pendant places
     */
    //controllo dei nodi pendenti, da rivedere e commentare
    public boolean checkPendantNode() {
        assert net != null;
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
                    /* If we find the place in the array that contains the predecessors of a transition we will check all the successor and we add them to the
                     * temporary array
                     * This parth of the methods allows to fill the map of the link
                     *
                     */
                }
                //we insert the place that we have found and have linked to the pre/post in the map
                map.put(place.getName(), new ArrayList<>(temp));
                temp.clear();
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
        assert temp.size()!=0;
        assert firstArrayOfPlace.size()!=0;
        assert secondArrayOfPlace.size()!=0;
        assert !placeName.equals(null);

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
        assert !map.isEmpty();
        assert !boolmap.isEmpty();
        assert !key.equals(null);
       boolmap.replace(key, true);
        for (String nextKey : map.get(key)) {
            if (!boolmap.get(nextKey)) {
                recursion(map, boolmap, nextKey);
            }
        }

    }
    /**
     * this method check if there is only transition in input or only in outup
     *
     * @return true if it is correct, false it isn't
     *
     */
    public boolean checkTrans() {
        assert !setOfTrans.isEmpty();
        for (Transition t : setOfTrans) {
            if (t.sizePre() == 0 || t.sizePost() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * this method research a transition in the net's set of transitions
     * @param nameTrans the name of the transition that we are looking for
     * @return the transition if we find it, or null if the transition doesn't exist
     */
    public Transition researchTrans(String nameTrans){
        assert nameTrans!=null;
        assert getSetOfTrans().size()!=0;
        for(Transition t: getSetOfTrans()){
            if(t.getName().equals(nameTrans)){
                return  t;
            }
        }
    return null;
    }

    /**
     * this method research a place in the net's set of place
     * @param namePlace the name of the place that we are looking for
     * @return the transition if we find it, or null if the place doesn't exist
     */
    public Place researchPlace(String namePlace){
        assert namePlace!=null;
        assert getSetOfPlace().size()!=0;
        for(Place p: getSetOfPlace()){
            if(p.getName().equals(namePlace)){
                return  p;
            }
        }
        return null;
    }

    /**
     * this method research a pair in the net
     * @param t the transition of the pair
     * @param p the place of the pair
     * @return the pair that we are looking for if we find it, else null
     */
    public Pair researchPair(Transition t, Place p){
        assert t!=null;
        assert p!=null;
        assert getSetOfPlace().size()!=0;
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
        assert setOfPlace.size()!=0;
        return setOfPlace;
    }

    public HashSet<Transition> getSetOfTrans() {
        assert setOfTrans.size()!=0;
        return setOfTrans;
    }

    public Net(Net _net) {
        net.addAll(_net.getNet());
        this.setOfPlace.addAll(_net.setOfPlace);
        this.setOfTrans.addAll(_net.setOfTrans);
        this.name = _net.getName();

    }

}