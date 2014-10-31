package adventure2;

/**
 * Exception class to easily alert caller that they are walking off the world,
 * outside the bounds of the provided map.
 * @author HaydnSlade;
 * @version 1.00 2014-10-12
 */
public class TraverseMapException extends Exception
{
    /* Public Features */
    
    /**
     * Create an exception with no extra data
     */
    public TraverseMapException()
    {
        super();
    }
    
    /**
     * Create an exception with an additional message that can be called by the
     * catcher.
     * @param message additional message.
     */
    public TraverseMapException(String message)
    {
        super(message);
    }

    /* Private Features */
    
}
