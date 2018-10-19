package Game;

public class Item {

    private final String name;
    private final int value;

    public Item(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Item()
    {
        this.name = null;
        this.value = 0;
    }

    public Item(Item otherItem)
    {
        this.name = otherItem.getName();
        this.value = otherItem.getValue();
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return String.format("Name: %s%nPrice: %d%n", name, value);
    }
}
