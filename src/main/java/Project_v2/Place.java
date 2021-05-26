package  Project_v2;

import java.util.Objects;

public class Place {
    private String name;
    private int numberOfToken = 0;

    public Place(String _name){
        this.name=_name;
    }

    public Place(String name_, int token) {
        this.name = name_;
        this.numberOfToken = token;
    }

    public int getNumberOfToken() {
        return numberOfToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(int n){
        this.numberOfToken=n;
    }
    @Override
    /**
     * this method return the hashcode of the transition
     */
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Override Equals
     *
     * @param obj
     * @return true if two Place have same name
     * @return false if two Place have different name
     */
    @Override
    public boolean equals(Object obj) {
        assert obj instanceof Place;
        Place p = (Place) obj;
        if (name.equals(p.getName())) {
            return true;
        }
        return false;
    }
}
