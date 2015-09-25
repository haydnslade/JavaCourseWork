package search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Grid object to hold the word search grid.
 * Initialized with a file that creates a grid of the specified size (size being
 * specified by the number of characters on the first line (N x N).
 * @author HaydnSlade;
 * @version 1.01 2014-11-01
 */
public class wordGrid implements searchLimits
{
    /* Public Features */
    
    /**
     * Initialize a word grid from the supplied file.
     * @param wordSearchGridFile string of system path to the file to read
     * @throws java.io.IOException
     */
    public wordGrid(String wordSearchGridFile) throws IOException
    {
        readGridFile(wordSearchGridFile);
    }
    
    /**
     * Return the grid size (N) as the grid is N x N
     * @return integer of size (grid is always square)
     */
    public int getGridSize()
    {
        return wordGrid.length;
    }
    
    /**
     * Return the character at the specified coordinates
     * @param x row in grid
     * @param y column in grid
     * @return char found at the specified row & column
     */
    public char getCharAtCoord(int x, int y)
    {
        return wordGrid[x][y];
    }
    
    /**
     * Override of toString, prints the Grid with all chars contained within
     * @return String of grid
     */
    @Override public String toString()
    {
        StringBuilder gridOut = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        
        for (char[] wordGridR : wordGrid)
        {
            gridOut.append(wordGridR).append(newLine);
        }
        return gridOut.toString();
    }

    /* Private Features */
    
    char[][] wordGrid;
    
    /**
     * Read in the specified file and gets the size of the grid
     * @param wordSearchGridFile file path to load
     * @throws java.io.IOException if file cannot be read, or grid exceeds max
     * size
     */
    private void readGridFile(String wordSearchGridFile) throws IOException
    {
        Scanner gridFile = new Scanner(Paths.get(wordSearchGridFile));
        /* Read the first line of the grid file to get the rows & columns based
         * on the number of characters and then intialize the word grid array 
         */
        String firstLine = gridFile.nextLine();
        int gridSize = firstLine.length();
        if (gridSize >= MAX_GRID_SIZE)
        {
            throw new IOException("Grid in file '" + wordSearchGridFile
                    + "' exceeds the total available grid size of "
                    + MAX_GRID_SIZE);
        }
        wordGrid = new char[gridSize][gridSize];
        
        // Since we had to read the first line to get size, close & reopen file
        gridFile.close();
        gridFile = new Scanner(Paths.get(wordSearchGridFile));
        /* The char array should be instantiated so we can create terrain
         * for the map from the rest of the file now.
         */
        createWordGrid(gridFile);
        gridFile.close();
    }
    
    /**
     * Populates the grid with characters from the file
     * @param gridFile already opened file that contains grid
     * @throws java.io.IOException issue while reading lines for grid
     */
    private void createWordGrid(Scanner gridFile) throws IOException
    {
        for (char[] wordGrid1 : wordGrid)
        {
            String currentLine = gridFile.nextLine();
            for (int col = 0; col < wordGrid.length; col++)
            {
                /* Set the current row and column in the grid equal to the
                 * char at the column on the current line.
                 */
                wordGrid1[col] = currentLine.charAt(col);
            }
        }
    }
    
}
