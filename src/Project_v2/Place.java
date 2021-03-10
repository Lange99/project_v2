package Project_v2;

public class Place {
    private String id;
    private int numberOfToken = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(int n){ this.numberOfToken=n;}
}
