package Project_v2;

import java.util.*;

public class PetriNet extends Net {

    private static final HashMap<Pair, Integer> initialMarking = new HashMap<>();

    public static HashMap<Pair, Integer> getInitialMarking() {
        return initialMarking;
    }

    public PetriNet(Net genericNet) {
        super(genericNet);
        saveInitialMark();
    }

    /**
     * this method allows to add the weight to the pair
     * @param nameTrans name of the trans
     * @param placeMod name of the place
     * @param weight the quantity of weight
     */
    public void addWeight(String nameTrans, String placeMod, int weight) {
        //we research the transition and the place that the user wants to change
        Transition transition = researchTrans(nameTrans);
        Place place = researchPlace(placeMod);

        //when we have the transition and the place we research the matching pair
        Pair pair = researchPair(transition, place);
        //we set its weight
        pair.setWeight(weight);
    }

    /**
     * method for adding tokens in the network
     * @param placeId is the place where i want add token
     * @param token is the number of token
     * @return false if the place doesn't exist, true if I add it correctly
     */
    public boolean addToken(String placeId, int token) {
        Place placeChoosen = researchPlace(placeId);
        if (placeChoosen == null) {
            return false;
        } else {
            placeChoosen.setToken(token);
            return true;
        }
    }

    /**
     * Method that allows you to save the initial marking
     */
    public void saveInitialMark() {
        for (Pair p : super.getNet()) {
            if (p.getPlace().getNumberOfToken() != 0)
                initialMarking.put(p, p.getPlace().getNumberOfToken());
        }
    }

    /**
     * Override of the equals method which allows me to check if two petri nets are equal
     * @param obj is element to check
     * @return true if two Petri's Net are equals
     */
    @Override
    public boolean equals(Object obj) {

        assert obj instanceof PetriNet;
        assert obj != null;
        int tokenNumber;
        PetriNet pt = (PetriNet) obj;
        int numberOfPlace= pt.getSetOfPlace().size() ;
        int numberOfTrans = pt.getSetOfTrans().size();

        //If they have a different number of places and transitions I know they are two different networks
        if (numberOfPlace != super.getSetOfPlace().size() || numberOfTrans != super.getSetOfTrans().size()){
            return false;
        }

        //Check if the sets of transitions and places are the same, if they are different the two networks are different
        if(!super.getSetOfPlace().containsAll(pt.getSetOfPlace())){
            return false;
        }
        if(!super.getSetOfTrans().containsAll(pt.getSetOfTrans())){
            return false;
        }

        //At this point I check the initial marking,
        // if two places have a different number of tokens it means that the initial marking of the two networks is different
        for (Pair p: super.getNet()) {
           tokenNumber = (pt.getInitialMarking().get(p));
           if (tokenNumber!=initialMarking.get(p)) {
               return false;
           }
        }
        return true;
    }


}

