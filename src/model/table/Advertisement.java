package model.table;

public class Advertisement {
    private String name; //key of the hash
    private String date;
    private int price;

    public Advertisement(String name, String date, int price) {
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

    @Override
    public String toString() {
        return "Advertisement{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                '}';
    }

    public String toPrettyString() {
        return "Nom: " + name +
                "\nDia: " + date +
                "\nPreu: " + price + "€";
    }
}
