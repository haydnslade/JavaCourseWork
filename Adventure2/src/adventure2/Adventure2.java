package adventure2;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author HaydnSlade;
 * @version 1.08 2014-10-12
 */
public class Adventure2 
{
    /* Public Features */

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // Check that a file was provided, and if not exit the program with
        if(args.length == 0)
        {
            System.out.println("Proper usage is: Map file pathname.");
            System.exit(0);
        }
        
        CharClass adventurer = new CharClass();
        Map adventureMap;
        
        try  
        {
            // Try to load the map, if invalid file will catch
            adventureMap = new Map(args[0]);
            String command;
            
            /* Start game loop */
            do
            {
                command = getInput();
                processInput(command, adventurer, adventureMap);
                displayCurrentLocation(adventurer, adventureMap);

            } while (currentState == adventureState.ACTIVE);
        }
        catch (IOException e)
        {
            System.out.println("Unable to open map file provided " 
                    + e.getMessage());
        }
    }
    
    /**
     * Gets input from player entered on command line.
     * @return String of the input collected from command line.
     */
    public static String getInput()
    {
        System.out.print("> ");
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
    public static void processInput(String inputToParse, CharClass adventurer, 
            Map adventureMap)
    {
        String[] commandParts = inputToParse.toLowerCase().split(" ");
        // Grab only the first character of the command and switch on it
        char command = commandParts[0].charAt(0);
        switch (command)
        {
            case 'g': adventurer.moveCharacter(commandParts[1], adventureMap);
                currentState = adventureState.ACTIVE;
                break;
            case 'i': adventurer.displayInventory();
                currentState = adventureState.ACTIVE;
                break;
            case 'q': quitApp();
                break;
            default : System.out.println("That is an invalid command.");
                break;
        }
    }
    
    /**
     * Displays the current location of the passed CharClass and checks the 
     * terrain they are on, then displays their position on the map based
     * on their vision.
     * @param adventurer CharClass object to use for location
     * @param adventureMap Map object to compare location for display (terrain)
     */
    public static void displayCurrentLocation(CharClass adventurer,
            Map adventureMap)
    {
        int adventurerVision = adventurer.getVisionPower();
        int[] adventurerXYPosition = new int[] 
        {
            adventurer.getCharX(), adventurer.getCharY()
        };
        
        // Print position and the terrain the Char is in
        System.out.println("You are at position " 
                + Integer.toString(adventurerXYPosition[1]) + "," 
                + Integer.toString(adventurerXYPosition[0]) + " in terrain " 
                + adventureMap.getTerrain(adventurerXYPosition[0], 
                        adventurerXYPosition[1]));
        
        /* This is a little hairy, we have to loop through the rows (Y) and then
         * the columns (X) for each of those rows which is backwards. 
         * So the entire loop is inverted, including the display.
         */
        int row = adventurerXYPosition[1] - adventurerVision;
        while (row <= adventurerXYPosition[1] + adventurerVision)
        {      
            int column = adventurerXYPosition[0] - adventurerVision;
            while (column <= adventurerXYPosition[0] + adventurerVision)
            {
                System.out.print(adventureMap.getTerrain(column, row));
                column++;
            }
            row++;
            // Print EOL for the next row
            System.out.println();
        }
    }
    
    /**
     * Displays the farewell message and sets status to INACTIVE
     */
    public static void quitApp()
    {
        System.out.println("Farewell");
        currentState = adventureState.INACTIVE;
    }
    
    /* Private Features */
    private static enum adventureState { ACTIVE, INACTIVE };
    private static adventureState currentState;
}
