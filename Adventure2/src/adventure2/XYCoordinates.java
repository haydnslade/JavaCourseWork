package adventure2;

/**
 * XYCoordinates contains the an int[] with x, y coordinates.
 * Used with the CharClass to handle current location of the CharClass
 * @author HaydnSlade;
 * @version 1.02 2014-10-11
 */
public class XYCoordinates implements AdventureWorld
{

    /* Public Features */
    
    /**
     * Create default XYCoordinates @ 0,0
     */
    public XYCoordinates()
    {
        xyCoord = new int[] {0,0};
    }
    
    /**
     * Create XYCoordinates with a specific start rather than 0,0
     */
    public XYCoordinates(int x, int y, Map adventureMap)
    {
        xyCoord = new int[] {x, y};
    }
    
    /**
     * Set the X & Y components of position, checks the provided map to ensure
     * that you are not stepping off the world (out of bounds of map)
     * @param x coordinate to set position to
     * @param y coordinate to set position to
     * @param adventureMap map to compare the X & Y coordinates to, to verify
     * you aren't out of bounds
     */
    public void setXY(int x, int y, Map adventureMap) 
            throws TraverseMapException
    {
        if (adventureMap.getTerrain(x, y) == EDGE_OF_WORLD)
        {
            throw new TraverseMapException();
        }
        else
        {
            xyCoord[0] = x;
            xyCoord[1] = y;
        }
    }
    
    /**
     * access X component of current Coordinates
     * @return int of current X position
     */
    public int getX()
    {
        
        return xyCoord[0];
    }
    
    /**
     * access Y component of current Coordinates
     * @return int of current Y position
     */
    public int getY()
    {
     
        return xyCoord[1];
    }
    
    /* Private Features */
    
    private int[] xyCoord;
    
}
