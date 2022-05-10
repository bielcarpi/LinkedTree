package model.table;

public class Advertisment {
    private String name; //key of the hash
    private String date;
    private int price;

    public Advertisment(String name, String date, int price) {
        this.name = name;
        this.date = date;
        this.price = price;
    }


    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public int getPrice() {
        return price;
    }
}
