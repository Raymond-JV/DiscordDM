public enum WeaponItem {
    ABBYSAL_WHIP("Abbysal Whip", 0, 42, 0, 0.7, "whip"),
    ARMADYL_GODSWORD("Armadyl Godsword", 70_0000_000, 75, 50, 0.8, "ags"),
    BANDOS_GODSWORD("Bandos Godsword", 30_000_000, 70, 50, 0.8, "bgs"),
    DRAGON_CLAWS("Dragon Claws", 100_000_000, 35, 50, 0.6, "dclaw"),
    ZAMORAK_GODSWORD("Zamorak Godsword", 30_000_000, 54, 50, 0.75, "zgs");

    String name;
    int price;
    int maxHit;
    int spec;
    double accuracy;
    String quickCodes[];

    WeaponItem(String name, int price, int maxHit, int spec, double accuracy, String... quickCodes) {
        this.name = name;
        this.price = price;
        this.maxHit = maxHit;
        this.spec = spec;
        this.accuracy = accuracy;
        this.quickCodes = quickCodes;
    }
}
