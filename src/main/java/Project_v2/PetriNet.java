package Project_v2;

import Utility.IO;


import java.util.*;

public class PetriNet extends Net {
    public static final String HOW_MANY_TOKEN = "How many tokens do you want this place to have?\n(if you don't want tokens enter 0)";

    private static HashMap<Pair, Integer> initialMarking = new HashMap<>();

    public PetriNet(Net genericNet) {
        super(genericNet);
        addWeight();
        addToken();
        saveInitialMark();
    }

    public void addWeight() {
        ArrayList<Transition> transTemp = new ArrayList<>(super.getSetOfTrans());
        int i;
        while (IO.yesOrNo("You want change the weight of pair?")) {
            //stampo tutte le transizioni
            //TODO metodo che permette la scelta
            //TODO: CAMBIARE POSIZIONE
            IO.printTransition(transTemp);
            //salvo una transizione
            Transition transToChange = transTemp.get(IO.readInteger("What transition you want change?", 0, transTemp.size()));
            i = 0;

            //creo un array temporaneo di  posti e stampo tutti i posti associati a quella transizione
            System.out.println("the place connected to " + transToChange.getName() + ":");
            ArrayList<String> placeTemp = new ArrayList<>();
            //TODO: CAMBIARE POSiZIONE
            IO.printString(transToChange.getIdPre());

            //QUESTO FOR SERVE PERCHé C'è ADD
            for (String placeName : transToChange.getIdPre()) {
                placeTemp.add(placeName);
            }

            //TODO: CAMBIARE POSiZIONE
            IO.printString(transToChange.getIdPost());

            for (String placeName : transToChange.getIdPost()) {
                placeTemp.add(placeName);

            }
            String placeToChange = placeTemp.get(IO.readInteger("What place you want change?", 0, i));

            //ciclo le coppie finche non trovo quella desiderata
            //TODO: refattorizzare un botto
            for(i=0; i<super.getNet().size(); i++){
                if(placeToChange.compareTo(super.getNet().get(i).getPlaceName())==0 && transToChange.getName().compareTo(super.getNet().get(i).getTransName())==0){
                    int newWeight = IO.readIntegerWithMin("Insert the new Weight",1);
                    super.getNet().get(i).setWeight(newWeight);
                    IO.print("\n");
                    break;
                }
            }
            placeTemp.clear();
        }
    }

    //metodo per l'aggiunta dei token nella rete
    public void addToken() {
        ArrayList<Place> tempPlace = new ArrayList<>(super.getSetOfPlace());
        String placeId;
        int token;
        boolean check = false;
        boolean again = true;
        int i;

        while(IO.yesOrNo("You want to add tokens in the Petri's net?")) {
            do {
                i = 0;
                //Stampo tutte le alternative
                System.out.println("Place:");
                IO.printPlace(super.getSetOfPlace());



                int choise = IO.readInteger("where do you want to add the tokens?", 0, i-1);
                placeId = tempPlace.get(choise).getName();


                //inizio ricerca per vedere se esiste
                for (Place place : super.getSetOfPlace()) {
                    if (place.getName().compareTo(placeId) == 0) {
                        check = true;
                        break;
                    }
                }
                //Se check è falso non l'ha trovato, stampo l'error e ricomincio
                if (check == false) {
                    System.out.println("ERROR, WRONG ID");
                } else { //se check è true la ricerca è andata a buon fine, posso chiedere all'utente il peso della transizione e modificarla nella rete
                    token = IO.readIntegerWithMin("Insert the number of tokens: ", 0);
                    for (Place p : super.getSetOfPlace()) {
                        if (p.getName().compareTo(placeId) == 0) {
                            p.setToken(token);
                            break;
                        }
                    }
                }
            } while (!check);

        }
    }

    public void saveInitialMark(){
        for(Pair p: super.getNet()){
            if(p.getPlace().getNumberOfToken()!=0)
            initialMarking.put(p,p.getPlace().getNumberOfToken());
        }
       /* for (Place p: super.getSetOfPlace()) {
            initialMarking.put(p.getName(), p.getNumberOfToken());
        }*/
    }

    public void addWeightToPair(Pair pair, int weight) {
        pair.setWeight(weight);
    }
}

