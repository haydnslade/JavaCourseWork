/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventure2;

/**
 * Description of the World for the adventure including different types of 
 * terrain to traverse.
 * @author HaydnSlade
 * @version 1.00 2014-10-10
 */
public interface AdventureWorld
{
    /* Public features */
    
    /* FINAL variables for the different terrain types, only compare on
     * EDGE_OF_WORLD, but keep these for future comparisons of difficult terrain
     */
    public final static char MOUNTAINS = 'M';
    public final static char WATER = 'w';
    public final static char PLAINS = 'p';
    public final static char FOREST = 'f';
    public final static char EDGE_OF_WORLD = 'X';
}
