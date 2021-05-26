package Project_v2;

import java.util.ArrayList;
import java.util.Objects;

public class Transition {
    private String name;
    private ArrayList<String> idPre= new ArrayList<>();
    private ArrayList<String> idPost= new ArrayList<>();

    Transition(String name){

        this.name=name;
    }

    public void addPreOrPost(String placeName, int inOut) {
        if (inOut == 1)
            idPre.add(placeName);
        else
            idPost.add(placeName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIdPre() {
        return idPre;
    }

    public ArrayList<String> getIdPost() {
        return idPost;
    }


    public void addPre(String a){
        idPre.add(a);
    }

    public void addPost(String a){
        idPost.add(a);
    }

    public int sizePre(){
        return idPre.size();
    }

    public int sizePost(){
        return idPost.size();
    }

    public int getInputOutput(String name) {
        int ret = -1;
        for (String item: idPre)
            if (item.equals(name))
                ret = 1;
        for (String item: idPost)
            if (item.equals(name))
                ret = 0;
        return ret;
    }

    @Override
    /**
     * this method return the hashcode of the transition
     */
    public int hashCode() {
        assert name!=null;
        return Objects.hash(name);
    }

    /**
     * Override Equals
     *
     * @param obj
     * @return true if two Transition have same name
     * @return false if two Transition have different name
     */
    @Override
    public boolean equals(Object obj) {
        assert obj instanceof Transition;
        Transition t = (Transition) obj;
        if (name.equals(t.getName())) {
            return true;
        }
        return false;
    }
}