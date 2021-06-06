import server.*;

public class Server {

    public static void main(String[] args) {
        ServerView the_view = new ServerView();
        ServerModel the_model = new ServerModel();

        new ServerController(the_view, the_model);
    }
    
}