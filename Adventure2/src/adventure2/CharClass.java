package adventure2;

/**
 * Player class, holds inventory and current location. Can move on a map through
 * terrain.
 * @author HaydnSlade;
 * @version 1.05 2014-10-12
 */
public class CharClass 
{

    /* Public Features */
    
    /**
     * Initialize Char with defaults, doesn't currently support initialization
     * with custom values.
     */
    public CharClass()
    {
        charXYCoords = new XYCoordinates();
        setVisionPower(DEFAULT_VISION);
        characterInventory = new String[]
        {
            "flashlight", "rope", "cell phone", "food", "walking stick",
            "canteen"
        };
    }
    
    /**
     * Method for the Go adventure command that moves the player inside the grid.
     * @param direction argument of the direction to move player
     * @param adventureMap Map to attempt to move on
     */
    public void moveCharacter(String direction, Map adventureMap)
    {
        switch (direction) 
        {
            case "north": moveNorth(adventureMap);
                break;
            case "south": moveSouth(adventureMap);
                break;
            case "east": moveEast(adventureMap);
                break;
            case "west": moveWest(adventureMap);
                break;
            default: System.out.println("You can't go that way.");
                break;
        }
    }
    
    /**
     * Displays the character's current inventory.
     */
    public void displayInventory()
    {
        System.out.println("You are carrying:");
        for (String item : characterInventory)
        {
            System.out.println(item);
        }
    }
    
    /**
     * Access the Char's vision power (how far they can see)
     * @return int of current Vision strength
     */
    public int getVisionPower()
    {
        
        return visionPower;
    }
    
    /**
     * Change the Char's vision power (how far they can see)
     * @param range what to see the vision strength to
     */
    public void setVisionPower(int range)
    {
        visionPower = range;
    }
    
    /**
     * Access the current X coordinate of the Char in their XYCoordinate 
     * location
     * @return int of current X position
     */
    public int getCharX()
    {
        return charXYCoords.getX();
    }
    
    /**
     * Access the current Y coordinate of the Char in their XYCoordinate 
     * location
     * @return int of current Y position
     */
    public int getCharY()
    {
        return charXYCoords.getY();
    }
    
    /* Private features */
    private String[] characterInventory;
    
    private XYCoordinates charXYCoords;
    // Default vision is current 2, next assignment will allow changes
    private static final int DEFAULT_VISION = 2;
    private int visionPower;
    
    /**
     * Attempts to move the character North on the supplied map.
     * @param adventureMap map to try to move on
     */
    private void moveNorth(Map adventureMap)
    {
        int currentX = charXYCoords.getX();
        int currentY = charXYCoords.getY();
        
        /* If you try to move off the map, catch and output that you can't move
         * in that direction
         */
        try
        {
            charXYCoords.setXY(currentX, currentY - 1, adventureMap);
            System.out.println("Moving North...");
        }
        catch(TraverseMapException e)
        {
            System.out.println("You can't go that far North");
        }
    }
    
    /**
     * Attempts to move the character East on the supplied map.
     * @param adventureMap map to try to move on
     */
    private void moveEast(Map adventureMap)
    {
        int currentX = charXYCoords.getX();
        int currentY = charXYCoords.getY();
        
        /* If you try to move off the map, catch and output that you can't move
         * in that direction
         */
        try
        {
            charXYCoords.setXY(currentX + 1, currentY, adventureMap);
            System.out.println("Moving East...");
        }
        catch(TraverseMapException e)
        {
            System.out.println("You can't go that far East");
        }
    }
    
    /**
     * Attempts to move the character South on the supplied map.
     * @param adventureMap map to try to move on
     */
    private void moveSouth(Map adventureMap)
    {
        int currentX = charXYCoords.getX();
        int currentY = charXYCoords.getY();
        
        /* If you try to move off the map, catch and output that you can't move
         * in that direction
         */
        try 
        {
            charXYCoords.setXY(currentX, currentY + 1, adventureMap);
            System.out.println("Moving South...");
        }
        catch(TraverseMapException e)
        {
            System.out.println("You can't go that far South");
        }
    }
    
    /**
     * Attempts to move the character West on the supplied map.
     * @param adventureMap map to try to move on
     */
    private void moveWest(Map adventureMap)
    {
        int currentX = charXYCoords.getX();
        int currentY = charXYCoords.getY();
        
        /* If you try to move off the map, catch and output that you can't move
         * in that direction
         */
        try 
        {
            charXYCoords.setXY(currentX - 1, currentY, adventureMap);
            System.out.println("Moving West...");
        }
        catch(TraverseMapException e)
        {
            System.out.println("You can't go that far West");
        }
    }
    
}
