package adventure;

import java.util.*;

/**
 * Creates an Adventure game allow exploration of 5x5 grid, and displaying
 * the player's inventory.
 * Completes CS3250 Assignment #2
 * @author HaydnSlade;
 * @version 1.02 2014-09-14
 */
public class Adventure 
{
    /* Public features */
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("Project02 Adventure");
        Adventure.adventureGrid[0][0] = Adventure.PLAYER_POSITION;
        String command;
        
        /* Start game loop */
        do
        {
            command = getInput();
            processInput(command);
            displayCurrentLocation();
            
        } while (Adventure.currentState == Adventure.adventureState.ACTIVE);
        
        System.out.println("Project02 Adventure");
    }
    
    /**
     * Gets input from player entered on command line.
     * @return String of the input collected from command line.
     */
    public static String getInput()
    {
        String currentInput;
        Scanner input = new Scanner(System.in);
        currentInput = input.nextLine();
        return currentInput;
    }
    
    /**
     * Processes input from the getInput(), parses the command
     * and arguments entered and splits it into the correct parts.
     * @param inputToParse value to parse into the command and argument.
     */
    public static void processInput(String inputToParse)
    {
        String[] commandParts = inputToParse.toLowerCase().split(" ");
        char command = commandParts[0].charAt(0);
        switch (command)
        {
            case 'g': moveCharacter(commandParts[1]);
                break;
            case 'i': displayInventory();
                break;
            case 'q': quitApp();
                break;
            default : System.out.println("That is an invalid command.");
                break;
        }
    }
    
    /**
     * Displays the player's current inventory.
     */
    public static void displayInventory()
    {
        System.out.println("You are carrying:");
        for (String item : Adventure.adventureInventory)
        {
            System.out.println(item);
        }
        
        Adventure.currentState = Adventure.adventureState.ACTIVE;
    }

    /**
     * Method for the Go adventure command that moves the player inside the grid.
     * @param direction argument of the direction to move player
     */
    public static void moveCharacter(String direction)
    {
        switch (direction) 
        {
            case "north": moveNorth();
                break;
            case "south": moveSouth();
                break;
            case "east": moveEast();
                break;
            case "west": moveWest();
                break;
            default: System.out.println("You can't go that way.");
                break;
        }
        
        Adventure.currentState = Adventure.adventureState.ACTIVE;
    }
    
    /**
     * Attempts to move the player North in the grid (-1 in the second column)
     */
    public static void moveNorth()
    {
        int moveToRow;
        boolean found = false;
       
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int column = 0; column < GRID_SIZE; column++)
            {
                if (Adventure.adventureGrid[row][column] 
                        == Adventure.PLAYER_POSITION)
                {
                    moveToRow = row - 1;
                    if (moveToRow < 0) 
                    {
                        System.out.println("You can't go that far north");
                    }
                    else if (found == false)
                    {
                        Adventure.adventureGrid[moveToRow][column] 
                                = Adventure.PLAYER_POSITION;
                        Adventure.adventureGrid[row][column] = 0;
                        found = true;
                    }
                }
            }
        }
    }
    
    /**
     * Attempts to move the player South in the grid (+1 in the second column)
     */
    public static void moveSouth()
    {
        int moveToRow;
        boolean found = false;
       
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int column = 0; column < GRID_SIZE; column++)
            {
                if (Adventure.adventureGrid[row][column] 
                        == Adventure.PLAYER_POSITION)
                {
                    moveToRow = row + 1;
                    if (moveToRow > Adventure.GRID_SIZE - 1) 
                    {
                        System.out.println("You can't go that far south");
                    }
                    else if (found == false)
                    {
                        Adventure.adventureGrid[moveToRow][column] 
                                = Adventure.PLAYER_POSITION;
                        Adventure.adventureGrid[row][column] = 0;
                        found = true;
                    }
                }
            }
        }
    }
    
    /**
     * Attempts to move the player East in the grid (+1 in the first column)
     */
    public static void moveEast()
    {
        int moveToColumn;
        boolean found = false;
       
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int column = 0; column < GRID_SIZE; column++)
            {
                if (Adventure.adventureGrid[row][column] 
                        == Adventure.PLAYER_POSITION)
                {
                    moveToColumn = column + 1;
                    if (moveToColumn > Adventure.GRID_SIZE - 1) 
                    {
                        System.out.println("You can't go that far east");
                    }
                    else if (found == false)
                    {
                        Adventure.adventureGrid[row][moveToColumn] 
                                = Adventure.PLAYER_POSITION;
                        Adventure.adventureGrid[row][column] = 0;
                        found = true;
                    }
                }
            }
        }
    }
    
    /**
     * Attempts to move the player West in the grid (-1 in the first column)
     */
    public static void moveWest()
    {
        int moveToColumn;
        boolean found = false;
       
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int column = 0; column < GRID_SIZE; column++)
            {
                if (Adventure.adventureGrid[row][column] 
                        == Adventure.PLAYER_POSITION)
                {
                    moveToColumn = column - 1;
                    if (moveToColumn < 0) 
                    {
                        System.out.println("You can't go that far west");
                    }
                    else if (found == false)
                    {
                        Adventure.adventureGrid[row][moveToColumn] 
                                = Adventure.PLAYER_POSITION;
                        Adventure.adventureGrid[row][column] = 0;
                        found = true;
                    }
                }
            }
        }
    }
    
    /**
     * Displays the current location of the player in the grid.
     */
    public static void displayCurrentLocation()
    {
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int column = 0; column < GRID_SIZE; column++)
            {
                if (Adventure.adventureGrid[row][column] 
                        == Adventure.PLAYER_POSITION)
                {
                    System.out.format("You are at location %d,%d\n", 
                            row, column);
                }
            }
        }
    }
    
    /**
     * Displays the farewell message and sets status to INACTIVE
     */
    public static void quitApp()
    {
        System.out.println("Farewell");
        Adventure.currentState = Adventure.adventureState.INACTIVE;
    }
    
    /* Private features */
    private static final int GRID_SIZE = 5;
    private static final int PLAYER_POSITION = 1;
    private static enum adventureState { ACTIVE, INACTIVE };
    
    private static int adventureGrid[][] = new int[GRID_SIZE][GRID_SIZE];
    private static adventureState currentState;
    private static String[] adventureInventory =
    {
        "flashlight", "rope", "cell phone", "food", "walking stick", "canteen"
    };
    
}
