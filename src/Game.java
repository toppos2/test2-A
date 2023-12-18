/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game
{
    private Parser parser;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player("lennert", 25);
        createGame();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createGame()
    {
        Room outside, theater, pub, lab, office, cellar;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("in the pub cellar");
        
        // initialise room exits
        outside.setExit(Room.EAST, theater);
        outside.setExit(Room.SOUTH, lab);
        outside.setExit(Room.WEST, pub);
        theater.setExit(Room.WEST, outside);
        pub.setExit(Room.EAST, outside);
        pub.setExit(Room.DOWN, cellar);
        lab.setExit(Room.NORTH, outside);
        lab.setExit(Room.EAST, office);
        office.setExit(Room.WEST,lab);
        cellar.setExit(Room.UP, pub);

        player.setCurrentRoom(outside);

        Item fireaxe = new Item("fireaxe", "fireman's axe to break through doors", 1.7);
        Item laptop = new Item("laptop", "marc's laptop", 3.7);
        office.addItem(laptop);
        office.addItem(fireaxe);
        outside.addItem(new Item("shovel", "you can dig or hit", 2.1));
        outside.addItem(new Item("pubboard", "thomas more is the best", 4.7));
        outside.addItem(new Item("keg", "a keg filled with beer just delivered", 72.8));
        cellar.addItem(new Item("barrel", "barrel filled with suspicious liquid", 40.7));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printPlayerInfo();
        System.out.println();
    }

    private void printPlayerInfo() {
        System.out.println(player.getAllInfo());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        CommandWord commandWord = command.getCommandWord();
        switch(commandWord) {
            case HELP:
                printHelp();
                break;
            case GRAB:
                grabItem(command);
                break;
            case LOOK:
                printPlayerInfo();
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            default:
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   " + parser.getCommandInfo());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setCurrentRoom(nextRoom);
            printPlayerInfo();
            System.out.println();
        }
    }

    private void grabItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to grab...
            System.out.println("Grab what?");
            return;
        }

        String itemName = command.getSecondWord();
        //if (player.grab(itemName)) {
        GrabStatus status = player.grab(itemName);
        if (status==GrabStatus.OK) {
            printPlayerInfo();
        } else if (status==GrabStatus.TOOHEAVY) {
            System.out.println("The item with the name " + itemName + " is too heavy");
        } else {
            System.out.println("There is no item with the name " + itemName);
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
