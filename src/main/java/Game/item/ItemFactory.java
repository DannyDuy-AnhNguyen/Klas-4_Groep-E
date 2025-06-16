package Game.item;

public class ItemFactory {
    public static Item maakItem(String naam) {
        if (naam == null) return null;
        return switch (naam.toLowerCase()) {
            case "scrum zwaard" -> new ItemWapen("Scrum Zwaard");
            case "splitter" -> new ItemSplitter("Splitter");
            case "rots" -> new ItemNutteloos("Rots");
            case "boek" -> new ItemBoek("boek");
            case "vragengum" -> new ItemVragenGum("VragenGum");

            default -> null;
        };
    }
}
