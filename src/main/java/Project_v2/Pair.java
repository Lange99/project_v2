package Project_v2;

public class Pair {
    private Place place;
    private Transition trans;
    private int weight = 1;

    public  Pair(Place place, Transition trans){
        assert !place.equals(null);
        this.place=place;
        this.trans=trans;
    }

    public Transition getTrans() {
        assert !place.equals(null);
        assert !trans.equals(null);
        return trans;
    }


    public Pair(String place_name, String trans_name, int direction){
        this.place = new Place(place_name);
        this.trans = new Transition(trans_name);
        this.trans.addPreOrPost(place_name, direction);
    }

    public Pair(String place_name, Transition trans, int direction){
        this.place = new Place(place_name);
        this.trans = trans;
        this.trans.addPreOrPost(place_name, direction);
    }


    public Pair(String place_name, int token, String trans_name, int direction, int weight) {
        this.place = new Place(place_name, token);
        this.trans = new Transition(trans_name);
        this.trans.addPreOrPost(place_name, direction);
        this.weight = weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Place getPlace() {
        return place;
    }
    /**
     * this method check if the current pair is equal to the other one
     * @param toCompare the pair which is compared
     * @return true if the pairs are equal
     */

    public boolean compare(Pair toCompare) {
        assert !toCompare.compare(null);
        //check if the place's ID is equal to the toCompare's ID, and then check if the trans'S ID is equal to the toCOmpare'S ID
        if( (place.getName().compareTo(toCompare.getPlaceName()) == 0) &&
                (trans.getName().compareTo(toCompare.getTransName()) == 0)){
            return true;
        }
        return false;
    }

    public String getTransName(){
        assert !trans.getName().equals(null);
        return  trans.getName();
    }
    public String getPlaceName(){
        assert !place.getName().equals(null);
        return  place.getName();
    }
}