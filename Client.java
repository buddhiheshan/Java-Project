import client.*;

public class Client {

    public static void main(String[] args) {
        ClientView the_view = new ClientView();
        ClientModel the_model = new ClientModel();

        new ClientController(the_view, the_model);
    }
    
}