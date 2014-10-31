package theBigDeal;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.EnumSet;

/**
 *
 * @author HaydnSlade;
 * @version 1.05 2014-09-28
 */
public class BestStrategy implements TheBigDeal
{
    /* Public features */

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        EnumSet<bigDealChoices> testCases = EnumSet.allOf(bigDealChoices.class);
        
        // Begin test case, loop through each strategy
        for (bigDealChoices choice : testCases)
        {
            for (int run = 1; run <= testCaseIterations; run++)
            {
                
                createWinningDoor();
                int currentDoorChoice = RandomMethods.doorChoice();
                
                int eliminatedDoor = eliminateDoor(currentDoorChoice);
                
                switch (choice)
                {
                    case RANDOM: chooseDoorRandom(currentDoorChoice, 
                            eliminatedDoor, false);
                        break;
                    case FEARFUL: chooseDoorFearful(currentDoorChoice, 
                            eliminatedDoor, false);
                        break;
                    case UNDECIDED: chooseDoorUndecided(currentDoorChoice, eliminatedDoor);
                        break;
                }
            }
        }
        
        //Ran all tests, show the results.
        displaySimulationResults();
    }
    
    /* Private features */
    
    private static int testCaseIterations = 4000000;
    private static int randomChoiceWinsTotal;
    private static int fearfulChoiceWinsTotal;
    private static int undecidedChoiceWinsTotal;
    
    private static int[] gameDoors = new int[DOORS_AVAILABLE];
    
    //Int to fix return status, maybe there is a better way??
    private static final int BAD_DATA = 9;
    
    
    /**
     * Updates the availableDoors array to have a new winning door. Wipes out 
     * last winning door.
     */
    private static void createWinningDoor()
    {
        for (int i = 0; i < gameDoors.length; i++) 
        { 
            gameDoors[i] = LOSER;
        }
        
        int winningDoor = RandomMethods.doorChoice();
        gameDoors[winningDoor] = WINNER;
    }
    
    /**
     * 'Player' is confident in their choice, check to see if they win.
     * @param currentDoorChoice the current choice of door from the 'player'
     * @param eliminatedDoor the door that was eliminated after 'player' chose
     * @param undecided flag of whether this originally came from undecided
     */
    private static void chooseDoorRandom(int currentDoorChoice, 
            int eliminatedDoor, boolean undecided)
    {
        if (gameDoors[currentDoorChoice] == WINNER && undecided == false)
        { 
            randomChoiceWinsTotal++; 
        }
        else if (gameDoors[currentDoorChoice] == WINNER && undecided == true)
        {
            undecidedChoiceWinsTotal++;
        }
    }
    
    /**
     * 'Player' is scared, so switch the choice to the other remaining door and
     * then check to see if they win.
     * @param currentDoorChoice the current choice of door from the 'player'
     * @param eliminatedDoor the door that was eliminated after 'player' chose
     * @param undecided flag of whether this originally came from undecided
     */
    private static void chooseDoorFearful(int currentDoorChoice, 
            int eliminatedDoor, boolean undecided)
    {
        currentDoorChoice = switchDoorChoice(currentDoorChoice, eliminatedDoor);
        
        if (gameDoors[currentDoorChoice] == WINNER && undecided == false) 
        { 
            fearfulChoiceWinsTotal++;
        }
        if (gameDoors[currentDoorChoice] == WINNER && undecided == true) 
        { 
            undecidedChoiceWinsTotal++;
        }
    }
    
    /**
     * 'Player' is not sure what to do, they flip a coin and stick with their
     * choice, or choose the other door.
     * @param currentDoorChoice the current choice of door from the 'player'
     * @param eliminatedDoor the door that was eliminated after 'player' chose
     */
    private static void chooseDoorUndecided(int currentDoorChoice, 
            int eliminatedDoor)
    {
        int doorStrategy = RandomMethods.oneZero();
        
        switch (doorStrategy)
        {
            case ZERO: chooseDoorRandom(currentDoorChoice, eliminatedDoor, 
                    true);
                break;
            case ONE: chooseDoorFearful(currentDoorChoice, eliminatedDoor, 
                    true);
                break;
        }
    }
    
    /**
     * Change the current choice of door to the other remaining door.
     * @return int of the other door (one not chosen or eliminated)
     */
    private static int switchDoorChoice(int oldDoorChoice, int eliminatedDoor)
    {
        for (int i = 0; i < gameDoors.length; i++)
        {
            if (i != oldDoorChoice && i != eliminatedDoor) { return i; }
        }
        
        //Invalid door was switched to
        return BAD_DATA;
    }
    
    /**
     * 'Player' has chosen a door, a losing door must be eliminated. If player
     * chose a loser then eliminate the other loser, otherwise randomly pick a
     * losing door to eliminate.
     * @return int of door that is out of play.
     */
    private static int eliminateDoor(int currentDoorChoice)
    {
        //'Player' has chosen a losing door, find the other loser and eliminate
        if (gameDoors[currentDoorChoice] == LOSER)
        {
            for (int i = 0; i < gameDoors.length; i++)
            {
                if (i != currentDoorChoice && gameDoors[i] != WINNER)
                {
                    return i;
                }
            }
        }
        
        /* 'Player' has chose the winning door, randomly eliminate one of the
         * other two
        */

        int doorToEliminate = RandomMethods.oneZero();
        int eliminationCounter = ZERO;

        for (int i = 0; i < gameDoors.length; i++)
        {
            if (i != WINNER && doorToEliminate == eliminationCounter)
            {
                return i;
            }
            else { eliminationCounter++; }
        }
        
        //Somehow a valid door was not eliminated
        return BAD_DATA;
    }
    
    /**
     * Display the final results of the simulation.
     */
    private static void displaySimulationResults()
    {
        //Formatter to display the decimal correctly
        DecimalFormat df = new DecimalFormat("0.0000");
        
        System.out.println("Best Strategy for the Big Deal");
        
        String iterations = NumberFormat.getIntegerInstance().format(
                testCaseIterations);
        System.out.printf("( %s iterations per strategy )\n", 
                iterations);
        System.out.println("Strategy\tFraction Wins");
        System.out.println("-----------------------------------------");
        
        String randomWinOutput = df.format(( (double)randomChoiceWinsTotal 
                / testCaseIterations));
        System.out.printf("Random\t\t%s\n", randomWinOutput);
        
        String fearfulWinOutput = df.format( (double)fearfulChoiceWinsTotal 
                / testCaseIterations);
        System.out.printf("Fearful\t\t%s\n", fearfulWinOutput);
        
        String undecidedWinOutput = df.format( (double)undecidedChoiceWinsTotal 
                / testCaseIterations);
        System.out.printf("Undecided\t%s\n", undecidedWinOutput);
    }
}
