package Project_v2;


import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        NetManager manager = new NetManager();
        manager.menageOption();

        //JACK TI LASCIO QUESTO CODICE PERCHè NON SO COSA TI SERVE E COSA NO
        /*
        String name= Reader.readNotEmpityString("Insert the name of the net ");
        Net net=new Net(name);
        boolean risp;
        do{
            //net.addNodi();
            risp=Reader.yesOrNo("Do you want to insert something else?");
        }while(risp);
        JsonWriter.writeJsonFile(net);
        //System.out.println("print "+ net.ID);
        */

    }
}

