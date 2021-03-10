package Project_v2;

public class Pair {
    private Place place = new Place();
    private Transition trans = new Transition();

    public Place getPlace() {

        return place;
    }

    public Transition getTrans() {

        return trans;
    }

    /**
     * constructor of the pair
     * @param place_name
     * @param trans_name
     */
    public Pair(String place_name, String trans_name){
        this.place.setId(place_name);
        this.trans.setId(trans_name);
    }


    /**
     * this method check if the current pair is equal to the other one
     * @param toCompare the pair which is compared
     * @return true if the pairs are equal
     */

    public boolean compare(Pair toCompare) {
        //check if the place's ID is equal to the toCompare's ID, and then check if the trans'S ID is equal to the toCOmpare'S ID
        if( (place.getId().compareTo(toCompare.getPlace().getId()) == 0) &&
                (trans.getId().compareTo(toCompare.getTrans().getId()) == 0)){
            return true;
        }
        return false;
    }
}