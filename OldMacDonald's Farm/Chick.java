
/**
 * A subclass of chicken that is a chick
 * 
 * @author Daniel Wu
 * @author Utkarsh Priyam
 * @version 10/18/2018
 */
public class Chick extends Chicken
{
    /**
     * Constructor for objects of class Chick
     */
    public Chick()
    {
        super("chick");
    }
    
    /**
     * Returns the Chick's animal sound
     * 
     * @return the sound that the chick makes
     */
    public String speak()
    {
        return "peep";
    }
}
