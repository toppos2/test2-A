public class Item {
    private String name;
    private String description;
    private double weight;
    private boolean drinkable;

    public Item(String name, String description, double weight /*boolean drinkable*/) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.drinkable = drinkable;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isItemDrinkable() {
        return drinkable;
    }

    public String toString() {
        String itemString = name + " (" + description + ") weighs " + weight + " kg";
        if (drinkable) {
            itemString += " is DRINKABLE";
        }
        return itemString;
    }
}
