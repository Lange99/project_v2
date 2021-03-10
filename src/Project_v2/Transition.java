package Project_v2;

import java.util.ArrayList;

public class Transition {
    private String id;
    private int weight = 0;
    private ArrayList<String> idPre= new ArrayList<>();
    private ArrayList<String> idPost= new ArrayList<>();

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getIdPost() {
        return idPost;
    }

    public ArrayList<String> getIdPre() {
        return idPre;
    }
}
