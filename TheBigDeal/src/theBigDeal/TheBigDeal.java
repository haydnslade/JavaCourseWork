package theBigDeal;

/**
 * Interface to handle the communication from the RandomMethods to BestStrategy.
 * Only holds final variables to prevent magic numbers for the Door choice and
 * ONE and ZERO to be used in decision making.
 * @author HaydnSlade;
 * @version 1.02 2014-09-28
 */
public interface TheBigDeal
{
    public final static int DOOR_ONE = 0;
    public final static int DOOR_TWO = 1;
    public final static int DOOR_THREE = 2;
    public final static int DOORS_AVAILABLE = 3;
    
    public final static int LOSER = 0;
    public final static int WINNER = 1;
    
    public final static int ONE = 1;
    public final static int ZERO = 0;
    
    public static enum bigDealChoices {RANDOM, FEARFUL, UNDECIDED};
}
