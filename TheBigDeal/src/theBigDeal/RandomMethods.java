package theBigDeal;

/**
 * Class that contains two methods to handle random selection for TheBigDeal
 * game.
 * @author HaydnSlade;
 * @version 1.01 2014-09-23
 */
public class RandomMethods implements TheBigDeal
{
    /* Public features */
    
    /**
     * Returns ONE or ZERO with equal probability by creating a random number,
     * multiplying by 101 to get a number from 1-100 and using modulus two to
     * get the remainder of 1 or zero.
     * @return integer of ONE or ZERO
     */
    public static int oneZero()
    {
        return (int)((Math.random() * RANDOM_FIX) % 2);
    }
    
    /**
     * Returns 1-3 for the Door to be randomly chosen. 
     * The output correlates to TheBigDeal interface's final int variables.
     * @return integer between 1-3
     */
    public static int doorChoice()
    {
        return (int)((Math.random() * RANDOM_FIX) % DOOR_MODULUS);
    }
    
    /* Private features */
    
    private final static int RANDOM_FIX = 101;
    private final static int DOOR_MODULUS = 3;
}
