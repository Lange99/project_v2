package Project_v2;

import java.util.*;

public class PetriNet extends Net {
    public static final String HOW_MANY_TOKEN = "How many tokens do you want this place to have?\n(if you don't want tokens enter 0)";

    private static final HashMap<Pair, Integer> initialMarking = new HashMap<>();

    public PetriNet(Net genericNet) {
        super(genericNet);;
        saveInitialMark();
    }

    public void addWeight(String nameTrans, String placeMod, int weight) {
        //we research the transition and the place that the user wants to change
        Transition transition = researchTrans(nameTrans);
        Place place = researchPlace(placeMod);

        //when we have the transition and the place we research the matching pair
        Pair pair = researchPair(transition, place);
        //we set its weight
        pair.setWeight(weight);
    }

    //metodo per l'aggiunta dei token nella rete
    public boolean addToken(String placeId, int token) {
        Place placeChoosen = researchPlace(placeId);
        if (placeChoosen == null) {
            return false;
        } else {
            placeChoosen.setToken(token);
            return true;
        }
    }

    public void saveInitialMark() {
        for (Pair p : super.getNet()) {
            if (p.getPlace().getNumberOfToken() != 0)
                initialMarking.put(p, p.getPlace().getNumberOfToken());
        }
    }

    public void addWeightToPair(Pair pair, int weight) {
        pair.setWeight(weight);
    }
/*
    @Override
    public boolean equals(Object obj) {
        assert obj instanceof PetriNet;
        PetriNet pt = (PetriNet) obj;
        if (super.getName().equals((pt.getName())){
            return true;
        }
        if ()
        return super.equals(obj);
    }
    */

}

