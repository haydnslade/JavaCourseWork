package adventure2;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Map object to hold representation of the map that a player can traverse.
 * Contains various types of terrain defined in Interface AdventureWorld
 * @author HaydnSlade;
 * @version 1.02 2014-10-10
 */
public class Map implements AdventureWorld
{

    /* Public Features */
    
    /**
     * Initialize an empty map
     */
    public Map()
    {
        theMap = new char[][] {{EDGE_OF_WORLD}, {EDGE_OF_WORLD}};
    }
    
    /**
     * Initialize a map from a file, should include row columns and data
     * @param mapFileToRead string of the system path to a file
     * @throws java.io.IOException file cannot be read/opened/etc
     */
    public Map(String mapFileToRead) throws IOException
    {
        readMapFile(mapFileToRead);
    }
    
    /**
     * Get terrain value at the provided X & Y coordinates
     * @param x coordinate
     * @param y coordinate
     * @return char value of the terrain at specified location
     */
    public char getTerrain(int x, int y)
    {
        // Check if the X or Y are negative, or are larger than the array
        if (x < 0 || y < 0 || x > theMap.length || y > theMap[x].length) 
        {
            return EDGE_OF_WORLD;
        }
                    
        return theMap[x][y];
    }
    
    /**
     * Open a scanner of specified file, grab the first line to gather the rows
     * and columns for our map size, then pass on to read in the terrain values
     * @param mapFileToOpen string with system path to file to read
     * @throws java.io.IOException file cannot be read/opened/etc
     */
    public void readMapFile(String mapFileToOpen) throws IOException
    {
        Scanner mapFile = new Scanner(Paths.get(mapFileToOpen));
        /* Read the first line of the map file to get the rows & columns 
         * and then intialize the map array 
         */
        String currentLine = mapFile.nextLine();
        String[] mapSize = currentLine.split(" ");
        int mapRows = Integer.parseInt(mapSize[0]);
        int mapColumns = Integer.parseInt(mapSize[1]);
        theMap = new char[mapRows][mapColumns];
        
        /* The char array should be instantiated so we can create terrain
         * for the map from the rest of the file now.
         */
        createMapTerrain(mapFile);
        mapFile.close();
    }
    
    /* Private features */
    
    char[][] theMap;
    
    /**
     * Load into the map char array values of terrain from the file passed
     * @param mapFile string of system path to file to be read from, should have
     * already had the first line read and be set to first line with terrain
     * @exception IOException file cannot be read/opened/etc
     */
    private void createMapTerrain(Scanner mapFile) throws IOException
    {
        for (char[] theMap1 : theMap)
        {
            String currentLine = mapFile.nextLine();
            for (int column = 0; column < theMap.length; column++)
            {
                /* Set the current row and column equal to the char at that
                 * column in the current line.
                 */
                theMap1[column] = currentLine.charAt(column);
            }
        }
    }
}
