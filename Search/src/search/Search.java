package search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Search class to run the word search 'game'.
 * Handles the creation of the grid, words to search, and searching algorithm
 * @author HaydnSlade;
 * @version 1.06 2014-11-02
 */
public class Search implements searchLimits
{
    /* Public Features */

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // Check that a file was provided, and if not exit the program
        if(args.length == 0)
        {
            System.out.println("Proper usage is: Map file pathname.");
            System.exit(0);
        }
        
        try
        {
            wordSearchPuzzle = new wordGrid(args[0]);
            wordsToSearch = new ArrayList<wordToSearch>();
            copyWordsToSearch(args[0]);
            searchGrid();
            printWordSearchResults();
        }
        catch (IOException e)
        {
            System.out.println("Unable to open the Word Search file provided "
                    + e.getMessage());
        }
    }

    /**
     * Read the file provided, skip over the first portion (contains grid)
     * then load all words into wordsToSearch for state information later
     * @param fileWithWords file to look up words to search
     * @throws java.io.IOException if file cannot be read
     */
    public static void copyWordsToSearch(String fileWithWords)
            throws IOException
    {
        Scanner wordSearchFile = new Scanner(Paths.get(fileWithWords));
        
        // Advance through the file to get to the search words
        for (int i = 0; i <= wordSearchPuzzle.getGridSize(); i++)
        {
            wordSearchFile.nextLine();
        }
        
        // Read in all words below grid into List
        while (wordSearchFile.hasNextLine())
        {
            String currentLine = wordSearchFile.nextLine();
            wordToSearch currentWord = new wordToSearch(currentLine);
            wordsToSearch.add(currentWord);
        }
    }
    
    /**
     * Search the grid with all of the words to find in wordsToSearch
     * Takes no params and returns nothing, changes state of wordsToSearch
     */
    public static void searchGrid()
    {
        /* Loop through all of the characters in the grid and compare against
         * each word loaded into wordsToSearch.
         */
        for (int row = 0; row < wordSearchPuzzle.getGridSize(); row++)
        {
            for (int column = 0; column < wordSearchPuzzle.getGridSize();
                    column++)
            {
                for (wordToSearch currentWord : wordsToSearch)
                {
                    if (currentWord.getIfWordFound() == false)
                    {
                        /* Go through all eight cases (horizontal, vertical) in
                         * both directions, and diagonal in all four directions
                         */
                        compareWordOnGrid(currentWord, row, column, 0, 1);
                        compareWordOnGrid(currentWord, row, column, 0, -1);
                        compareWordOnGrid(currentWord, row, column, 1, 0);
                        compareWordOnGrid(currentWord, row, column, -1, 0);
                        compareWordOnGrid(currentWord, row, column, 1, 1);
                        compareWordOnGrid(currentWord, row, column, 1, -1);
                        compareWordOnGrid(currentWord, row, column, -1, 1);
                        compareWordOnGrid(currentWord, row, column, -1, -1);
                    }
                }
            }
        }
    }
    
    /**
     * Prints out the word search gridn and the results of the search of all
     * words in the provided file
     */
    public static void printWordSearchResults()
    {
        System.out.println(wordSearchPuzzle.toString());
        for (wordToSearch currentWord : wordsToSearch)
        {
            System.out.print(currentWord.toString());
        }
    }
    
    /* Private Features */
    
    private static List<wordToSearch> wordsToSearch;
    private static wordGrid wordSearchPuzzle;
    
    /**
     * Search for the given word at the starting location by using the 
     * increments given (-1 to 1)
     * @param word what to search for
     * @param startRow where on the grid to start searching
     * @param startColumn where on the grid to start searcing
     * @param rowIncrement how to move the row
     * @param how to move the column
     */
    private static void compareWordOnGrid (wordToSearch word, int startRow,
            int startColumn, int rowIncrement, int columnIncrement)
    {
        boolean search = true;
        int currentChar = 0;
        int endRow = startRow;
        int endColumn = startColumn;
        
        while (search == true)
        {
            /* If the currentChar counter equals the length of the
             * word we are searching then we found a match
             */
            if (currentChar == word.getSearchWord().length())
            {
                word.setIfWordFound(search);
                /* Set coords equal to the end position found minus the last
                 * iteration (quick fix, ought to revisit)
                 */
                word.setWordCoord(new int[][] {{startRow, startColumn},
                    {(endRow - rowIncrement), (endColumn - columnIncrement)}});
                search = false;
            }
            /* If the increments cause the end row/column to end outside the
             * or the characters do not match, then stop searching
             */
            else if (endRow < 0 || endColumn < 0
                    || endRow >= wordSearchPuzzle.getGridSize()
                    || endColumn >= wordSearchPuzzle.getGridSize()
                    || wordSearchPuzzle.getCharAtCoord(endRow, endColumn)
                        != word.getSearchWord().charAt(currentChar))
            {
                search = false;
            }
            // Otherwise increment the end row and column to the next position
            else
            {
                endRow += rowIncrement;
                endColumn += columnIncrement;
                currentChar++;
            }
        }
    }
}
