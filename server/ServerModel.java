package server;

import java.net.Socket;
import java.util.*;

public class ServerModel {
    private static HashMap<String, Company> stock_db = new HashMap<String, Company>();
    private static HashMap<String, ArrayList<Bid>> stock_updates = new HashMap<String, ArrayList<Bid>>();
    private static HashMap<String, ArrayList<Socket>> connected_clients = new HashMap<String, ArrayList<Socket>>();
    private static ArrayList<String> user_list = new ArrayList<String>();


    public Company getCompanyDetail(String symbol){
        if (stock_db.containsKey(symbol)) return stock_db.get(symbol);
        else return null;
    }
    public static void appendStock_db(String symbol, Company company){
        stock_db.put(symbol, company);
    }
    public static void appendStockUpdates(String symbol, Bid bid){
        if (stock_updates.containsKey(symbol)){
            stock_updates.get(symbol).add(bid);
        }
        else {
            ArrayList<Bid> new_array_list = new ArrayList<Bid>();
            new_array_list.add(bid);
            stock_updates.put(symbol,new_array_list);
        }
    }
    public static void updateCurrentPrice(String symbol, Float new_price){
        stock_db.get(symbol).setCurrentPrice(new_price);
    }
    public ArrayList<Bid> getBidArray(String symbol){
        if (stock_updates.containsKey(symbol))
            return stock_updates.get(symbol);
        else
            return null;
    }   
    public void appendSocketArray(String symbol, Socket socket){
        if (connected_clients.containsKey(symbol)){
            connected_clients.get(symbol).add(socket);
        }
        else {
            ArrayList<Socket> new_array_list = new ArrayList<Socket>();
            new_array_list.add(socket);
            connected_clients.put(symbol, new_array_list);
        }
    }
    public ArrayList<Socket> getSocketArray(String symbol){
        if (connected_clients.containsKey(symbol))
            return connected_clients.get(symbol);
        else
            return null;
    }
    public void removeSocket(String symbol, Socket socket){
        if(connected_clients.containsKey(symbol)){
            connected_clients.get(symbol).remove(socket);
        }
        
    }
    public int addUser(String user){
        if (user_list.contains(user)){
            return -1;
        }
        else{
            user_list.add(user);
            return 0;
        }
    }
    public void removeUser(String user){
        if (user_list.contains(user)){
            user_list.remove(user);
        }
    }
}