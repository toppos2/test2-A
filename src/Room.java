import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Room
{
    public static final String NORTH = "north";
    public static final String EAST = "east";
    public static final String SOUTH = "south";
    public static final String WEST = "west";
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String WHATEVER = "whatever";
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;


    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        Random rand1 = new Random();
    }

    public void setExit(String direction, Room room) {
        if (!direction.equals(WHATEVER)) {
            exits.put(direction, room);
        }
    }




    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direction) {
        if (direction.equals(WHATEVER)) {
            ArrayList<String> exitList = new ArrayList<>(exits.keySet());
            if (!exitList.isEmpty()) {
                Random random = new Random();
                int randomIndex = random.nextInt(exitList.size());
                return exits.get(exitList.get(randomIndex));
            }
        }
        return exits.get(direction);
    }

    public String getExitString() {
        String exitString = "Exits: ";
        for(String direction : exits.keySet()) {
            exitString += direction + " ";
        }
        return exitString;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String getItemsString() {
        String returnString = "";
        for(Item item : items) {
            returnString += System.lineSeparator() + "  " + item;
        }
        return returnString;
    }

    public String getAllInfo() {
        String info = getDescription();
        if(!items.isEmpty()) {
            info += " which contains" + getItemsString();
        }
        info += System.lineSeparator() + getExitString();
        return info;
    }

    public Item getItem(String itemName) {
        Iterator<Item> it = items.iterator();
        while(it.hasNext()) {
            Item i = it.next();
            if(itemName.equals(i.getName())) {
                it.remove();
                return i;
            }
        }
        return null;
    }








}
