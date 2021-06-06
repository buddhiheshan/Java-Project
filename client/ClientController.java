package client;

import java.awt.event.*;
import java.io.BufferedReader;
import java.net.Socket;
import java.io.*;

public class ClientController {
    ClientView the_view;
    ClientModel the_model;
    int port = 2000;
    String servername = "localhost";
    Socket socket;
    BufferedReader in;
    BufferedWriter out;

    String server_request;

    String username;
    String symbol;
    String company;
    String current_value;
    String new_value;

    public ClientController(ClientView the_view, ClientModel the_model){
        this.the_view = the_view;
        this.the_model = the_model;
        this.the_view.name_panel.addButtonListener(new ButtonListener(), the_view.name_panel.next_button);
        this.the_view.name_panel.addButtonListener(new ButtonListener(), the_view.name_panel.quit_button);
        this.the_view.symbol_panel.addButtonListener(new ButtonListener(), the_view.symbol_panel.next_button);
        this.the_view.symbol_panel.addButtonListener(new ButtonListener(), the_view.symbol_panel.quit_button);
        this.the_view.bid_panel.addButtonListener(new ButtonListener(), the_view.bid_panel.bid_button);
        this.the_view.bid_panel.addButtonListener(new ButtonListener(), the_view.bid_panel.quit_button);
        initializeClient();
    }

    class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == the_view.name_panel.next_button){
                username = the_view.name_panel.username_textbox.getText();
                System.out.println(username);
                replyToServer(username);
                the_view.bid_panel.user_value.setText(username);
                the_view.card_layout.show(the_view.panel_container, "symbol");
            }
            if(e.getSource() == the_view.symbol_panel.next_button){
                symbol = the_view.symbol_panel.symbol_textbox.getText();
                System.out.println(symbol);
                replyToServer(symbol);
                the_view.card_layout.show(the_view.panel_container, "bid");
            }
            if(e.getSource() == the_view.bid_panel.bid_button){
                new_value = the_view.bid_panel.newbid_textbox.getText();
                replyToServer(new_value);
                the_view.bid_panel.newbid_textbox.setText("");
            }
            if((e.getSource() == the_view.name_panel.quit_button) | (e.getSource() == the_view.symbol_panel.quit_button)| (e.getSource() == the_view.bid_panel.quit_button) ){
                the_view.frame.dispose();
                replyToServer("quit");
                
            }
        }
    }

    public void initializeClient(){
        
        try {
            System.out.println("Waiting for a clients...");
            socket = new Socket(servername, port);
            System.out.println("Connected to the server.");
            the_view.frame.setVisible(true);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(true){
                server_request = in.readLine();
                System.out.println("Server> " + server_request);
                if(server_request.startsWith("Hello")){
                    the_view.symbol_panel.title.setText(server_request);
                }
                if(server_request.startsWith("Symbol:")){
                    symbol = getData(server_request, 1);
                    the_view.bid_panel.symbol_value.setText(symbol);
                }
                if(server_request.startsWith("Company:")){
                    company = getData(server_request, 1);
                    the_view.bid_panel.company_value.setText(company);
                }
                if(server_request.startsWith("Current")){
                    current_value = getData(server_request, 1);
                    the_view.bid_panel.currentbid_value.setText(current_value);
                }
                if(server_request.contains("greater")){
                    the_view.bid_panel.newbid_textbox.setText("");
                    new ErrorMsg(server_request);
                }
                if(server_request.contains("numeric")){
                    the_view.bid_panel.newbid_textbox.setText("");
                    new ErrorMsg(server_request);
                }
                if(server_request.contains("Invalid symbol")){
                    the_view.symbol_panel.symbol_textbox.setText("");
                    the_view.card_layout.show(the_view.panel_container, "symbol");
                    new ErrorMsg(server_request);
                }
                if(server_request.contains("Username already exist")){
                    the_view.name_panel.username_textbox.setText("");
                    the_view.card_layout.show(the_view.panel_container, "username");
                    new ErrorMsg(server_request);
                }
                if(server_request.contains("updated")){
                    the_view.bid_panel.bid_logger.append(server_request + "\n");
                }
                if(server_request.contains("Thank")){
                    try{
                        socket.close();
                        break;
                    }
                    catch (Exception err){
                        System.out.println("cannot close socket");
                    }
                }
                

            }
        } catch (Exception e) {
            System.out.println("Host unkown.");
        }
        
    }

    public void replyToServer(String reply){
        try {
            out.write(reply + "\n");
            out.flush();
        } catch (Exception e) {
            System.out.println("Write fail." + reply);
        }
        
    } 
    public String getData(String text, int index){
        String split_from = ": ";
        String[] splitted_array = text.split(split_from);
        return splitted_array[index];
    }

}