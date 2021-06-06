package server;

import java.net.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class ClientHandler extends ServerController{
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String client_response = new String();
    private String user;
    private String symbol;
    private float new_price;
    private String date_time;
    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    Date dateobj = new Date();
    Company com;



    public ClientHandler(Socket socket, ServerView the_view, ServerModel the_model) throws Exception{
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.the_view = the_view;
        this.the_model = the_model;
    }
    public void run(){
        try{               
            // Request for the usename from the client
            askClient("Welcome to the Auction Server.(Enter 'quit' to disconnect from the server)\nEnter username:\n");

            // Read the username from the response
            getAndVlidateUser();
            // getClientReponse();
            // user = client_response;

            // if (client_response.equals("quit")) {
            //     askClient("Thank you for using auction server.\n");
            //     socket.close();
            //     System.out.println(user + " left.");
            // }
            // else{
                askClient("Hello " + user + "\n");

                // Request a symbol from the client
                askClient("Enter  a symbol of the company:\n");
                
                // Get the symbol symbol from the client and validate the symbol
                getAndValidateSymbol();
                
                // Send symbol details and ask for bid
                askClient("Symbol: " + symbol +"\nCompany: " + com.getName() + "\nCurrent Price: $" + com.getCurrentPrice() + "\n");
                askClient("Enter new bid in $ to start bidding:\n");
                while (true){
                    // Read the bid
                    getAndValidateBid();
                    updateTextArea();
                    broadcastBid();
                }
            // }
           
        }
        catch (Exception e) {
            the_model.removeUser(user);
            the_model.removeSocket(symbol, socket);
            System.out.println("Client Disconnected.");
            // e.printStackTrace();
        }
    }
    public void getClientReponse() throws Exception{
        // try {
            while (true) {
                client_response = in.readLine();
                if (!client_response.equals("")){
                    if(client_response.equals("quit")){
                        askClient("Thank you for using auction server.\n");
                        socket.close();
                    }
                    break;
                }
            }
        // }
        // catch (Exception e){
        //     System.out.println("Client Response read fail");
        //     // e.printStackTrace();
        // }
    }

    public void askClient(String msg) throws Exception{
        out.write(msg);
        out.flush();
    }

    public void getAndVlidateUser() throws Exception{
        while(true){
            getClientReponse();
            if(the_model.addUser(client_response) == 0){
                user = client_response;
                break;
            }
            askClient("Username already exist. Enter another username: \n");
        }
    }

    public void getAndValidateSymbol() throws Exception{
        // try{
            while(true){
                getClientReponse();
                com = the_model.getCompanyDetail(client_response);
                if (com != null){
                    symbol = client_response;
                    System.out.println(user + " needs " + symbol);
                    the_model.appendSocketArray(symbol, socket);
                    break;
                }            
                askClient("Invalid symbol. Add a valid symbol.\n");
            }
        // }
        // catch (Exception e){
        //     System.out.println("getsymbol failed");
        //     // e.printStackTrace();
        // }
    }

    public void getAndValidateBid() throws Exception{
        // try {
            while(true){
                getClientReponse();
                // if (client_response.equals("quit")) {
                //     out.write("Thank you for using auction server.\n");
                //     out.flush();
                //     socket.close();
                //     System.out.println(user + " left.");
                //     break;
                // }
                try{
                    new_price = Float.parseFloat(client_response);
                }
                catch (NumberFormatException e){
                    askClient("Invalid bid. Enter a numeric value: \n");
                    continue;
                }
                
                if (new_price > com.getCurrentPrice()){
                    System.out.println(user + "\t" + symbol + "\t$" + new_price);
                    date_time = df.format(dateobj);
                    Bid bid = new Bid(user, new_price, date_time);
                    ServerModel.appendStockUpdates(symbol, bid);
                    ServerModel.updateCurrentPrice(symbol, new_price);
                    updateTable();
                    break;
                }
                askClient("Enter a value greater than current value:\n");
            }
        // }
        // catch (Exception e) {
        //     System.out.println("getbit failed");
        //     // e.printStackTrace();
        // }
    }
    public void displayOldBids(){
        ArrayList<Bid> bid_array = the_model.getBidArray(symbol);
        try{
            askClient("Previous Bids\n");
            if (bid_array == null) askClient("No previous bids.\n");
            else{
                for (Bid bid : bid_array) {
                    askClient(bid.getDateTime() + "\t" + bid.getUser() + "\t$" + bid.getBidValue() + "\n");
                }
            }
        }
        catch (Exception e){
            System.out.println("Display bid failed.");
            e.printStackTrace();
        }
    }
    public void updateTextArea(){
        String bid_detail = date_time + "\t" + symbol + "\t" + com.getName() + "\t" + user + "\t$" + new_price + "\n";
        the_view.addNewEntry(bid_detail);
    }
    public void broadcastBid(){
        ArrayList<Socket> socket_array = the_model.getSocketArray(symbol);

        for (Socket s : socket_array) {
            try{
                BufferedWriter broadcast = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                broadcast.write("Current price updated: $" + new_price + "\n");
                broadcast.flush();
                // System.out.println(com.getCurrentPrice());
            }
            catch (Exception e){
                System.out.println("Broadcast to " + socket + "failed\n");
            }
        }
    }
}