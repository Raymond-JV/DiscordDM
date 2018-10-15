package Game;

public class Item {

    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String info() {
        return String.format("Name: %s%nPrice: %d%n", name, price);
    }
}
