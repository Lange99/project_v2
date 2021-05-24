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
}
