package server;

public class Company {
    private String name;
    private float current_price;

    public Company(String name){
        this.name = name;
        this.current_price = randStartPrice();
        // System.out.println(this.current_price);
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public float getCurrentPrice(){
        return current_price;
    }
    public void setCurrentPrice(Float current_price){
        this.current_price = current_price;
    }
    public float randStartPrice(){
        float start_price = (int)(Math.random() * 100);
        // System.out.println(start_price);
        return start_price;
    }
}