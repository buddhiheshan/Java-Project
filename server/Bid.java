package server;

public class Bid {
    private String user;
    private Float bid_value;
    private String date_time;

    public Bid(String user, Float bid_value, String date_time){
        this.user = user;
        this.bid_value = bid_value;
        this.date_time = date_time;
    }

    public void printBid(String user, String bid_value, String date_time){
        System.out.println(this.date_time + "\t" + this.user + "\t" + this.bid_value);
    }
    public String getUser(){
        return user;
    }
    public float getBidValue(){
        return bid_value;
    }
    public String getDateTime(){
        return date_time;
    }
}