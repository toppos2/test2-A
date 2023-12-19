import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Player {
    private String name;
    private Room currentRoom;
    private double maxWeight;
    private ArrayList<Item> bag;
    private Stack<Room> previousRooms;

    public Player(String name, double maxWeight) {
        bag = new ArrayList<>();
        previousRooms = new Stack<>();
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

    public boolean go(String direction) {
        Room nextRoom = null;
        nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            return false;
        }
        else {
            previousRooms.push(currentRoom);
            setCurrentRoom(nextRoom);
            return true;
        }
    }

    public boolean back() {
        try {
            currentRoom = previousRooms.pop();
            return true;
        } catch(EmptyStackException e) {
            return false;
        }
    }

    public GrabStatus grab(String itemName) {
        Item foundItem = currentRoom.getItem(itemName);
        if (foundItem!=null && getBagWeight() + foundItem.getWeight() <= maxWeight) {
            bag.add(foundItem);
            return GrabStatus.OK;
        } else if(foundItem!=null) {
            currentRoom.addItem(foundItem);
            return GrabStatus.TOOHEAVY;
        }
        return GrabStatus.NOTFOUND;
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
