package Game;

public class Item {



    private final String name;
    private final int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    //#TODO clean item design in relation to weaponcomponent
    public Item()
    {
        this.name = null;
        this.price = 0;
    }

    public Item(Item otherItem)
    {
        this.name = otherItem.getName();
        this.price = otherItem.getPrice();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String info() {
        return String.format("Name: %s%nPrice: %d%n", name, price);
    }
}
