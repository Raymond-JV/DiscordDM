package Data;

public enum ItemFields {
        ItemList("items"),
        Commons("commons"),
        Weapons("weapons"),
        Name("name"),
        Price("price"),
        MaxHit("maxHit"),
        Accuracy("accuracy"),
        Special("spec"),
        QuickCodes("quickCodes");

        public final String field;

        ItemFields(String field) {
            this.field = field;
        }
    }

