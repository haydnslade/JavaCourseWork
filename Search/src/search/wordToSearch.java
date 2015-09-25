package search;

/**
 * Word object to hold word information for searching as well as status (found &
 * position).
 * Mainly a wrapper for the state of each word to be searched for in the grid
 * @author HaydnSlade;
 * @version 1.02 2014-11-02
 */
public class wordToSearch 
{
    /* Public Features */
    
    /**
     * Initialize word search object
     * @param word word to be used in search
     */
    public wordToSearch(String word)
    {
        searchWord = word;
        // set coords to default of 0
        wordCoord = new int[][] {{0, 0}, {0, 0}};
        wordFound = false;
    }
    
    /**
     * Return the value of the search word
     * @return word that object was initialized with the object
     */
    public String getSearchWord()
    {
        return searchWord;
    }
    
    /**
     * Returns the coordinates that the word are on (only correct if wordFound
     * is true.
     * First sub array position contains start, second contains end
     * @return int array that contains the x/y coords of the word from last
     * search.
     */
    public int[][] getWordCoord()
    {
        return wordCoord;
    }
    
    /**
     * Concatenates a string that is a textual representation of the coordinates
     * @return String that contains a text representation of the coordinates
     */
    public String displayWordCoord()
    {
        return ("start: " + wordCoord[0][0] + ", " + wordCoord[0][1] + " end: "
                + wordCoord[1][0] + ", " + wordCoord[1][1]);
    }
    
    /**
     * Returns a boolean on whether the string word was ever found (default is
     * false)
     * @return bool on if word was found at all in last search
     */
    public boolean getIfWordFound()
    {
        return wordFound;
    }
    
    /**
     * Modify the coordinates for where the word was found in the grid
     * @param coordinates integer array to copy to object for coordinates
     */
    public void setWordCoord(int[][] coordinates)
    {
        System.arraycopy(coordinates, 0, wordCoord, 0, wordCoord.length);
    }
    
    /**
     * Set the truth of whether the word in this object was found in the grid
     * @param found was the word found in the grid
     */
    public void setIfWordFound(boolean found)
    {
        wordFound = found;
    }
    
    /**
     * Overrides the toString of the word object, returns coord if found
     * or displays not found
     * @return String of the word with found and location
     */
    @Override public String toString()
    {
        String objectString;
        if (wordFound)
        {
            objectString = searchWord + " found at start: " + wordCoord[0][0]
                    + ", " + wordCoord[0][1] + " end: " + wordCoord[1][0] + ", "
                    + wordCoord[1][1] + "\n";
        }
        else
        {
            objectString = searchWord + " not found\n";
        }
        return objectString;
    }

    /* Private Features */
    
    private String searchWord;
    private int[][] wordCoord;
    private boolean wordFound;
}
