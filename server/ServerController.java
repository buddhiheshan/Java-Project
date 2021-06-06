package server;

import java.io.*;
import java.net.*;


public class ServerController extends Thread{
    protected ServerView the_view;
    protected ServerModel the_model;
    private int port = 2000;
    private ServerSocket server_socket;

    public ServerController(){}

    public ServerController(ServerView the_view, ServerModel the_model){
        this.the_view = the_view;
        this.the_model = the_model;
        initializeServer(); 
    }

    public void readStockFile(String csvFile){
        BufferedReader buffer = null;
        String line = "";
        String cvsSplitBy = "[,-]";

        try {
            buffer = new BufferedReader(new FileReader(csvFile));
            while ((line = buffer.readLine()) != null) {

                // use comma as separator
                String[] splitted_array = line.split(cvsSplitBy);
                Company company = new Company(splitted_array[1]);

                ServerModel.appendStock_db(splitted_array[0], company);

                // System.out.println("Symbol = " + splitted_array[0] + " Company =" + ServerModel.stock_db.get(splitted_array[0]).getName());
            }
            System.out.println(csvFile + " read complete.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateTable(){
        for (int i = 0; i < 8; i ++){
            Company com = the_model.getCompanyDetail(the_view.getRowSymbol(i));
            the_view.setRow(com, i);
        }
        the_view.frame.repaint();
    }

    public void initializeServer(){
        try{
            server_socket = new ServerSocket(port);
            readStockFile("./stocks.csv");
            updateTable();
            the_view.frame.setVisible(true);
            System.out.println("Server is running on port " + port + "...");
   
            System.out.println("Waiting for a clients...");
            while (true){
                Socket socket = server_socket.accept();
                System.out.println("New client connected.");
                //    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                try {
                    Thread client = new ClientHandler(socket,this.the_view, this.the_model);

                    System.out.println(Thread.activeCount());
        
                    client.start();
                } catch (Exception e) {
                    System.out.println("Thread creation failed");
                    e.printStackTrace();
                }    
                
            }
   
         } 
         catch (IOException e) {
            System.out.println("Server Initialization Failed.");
            the_view.frame.dispose();
         }
    }

}