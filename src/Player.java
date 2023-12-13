import java.util.ArrayList;

public class Player {
    private String name;
    private Room currentRoom;
    private double maxWeight;
    private ArrayList<Item> bag;

    public Player(String name, double mexWeight) {
        bag = new ArrayList<>();
        this.name = name;
        setMaxWeight(maxWeight);
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        if(maxWeight < 0) maxWeight = 0;
        this.maxWeight = maxWeight;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getName() {
        return name;
    }

    public boolean grab(String itemName) {
        Item foundItem = currentRoom.getItem(itemName);
        if (foundItem!=null && getBagWeight() + foundItem.getWeight() <= maxWeight) {
            bag.add(foundItem);
            return true;
        } else if(foundItem!=null) {
            currentRoom.addItem(foundItem);
        }
        return false;
    }

    private double getBagWeight() {
        double totalWeight = 0;
        for(Item i : bag) {
            totalWeight += i.getWeight();
        }
        return totalWeight;
    }

    private String getBagString() {
        String returnString = "";
        for(Item item : bag) {
            returnString += System.lineSeparator() + "  " + item;
        }
        return returnString;
    }

    public String getAllInfo() {
        String info = name;
        if(!bag.isEmpty()) {
            info += " has following items in his bag:" + getBagString()
                    + System.lineSeparator() + "and";
        }
        info += " is " + currentRoom.getAllInfo();
        return info;
    }
}
