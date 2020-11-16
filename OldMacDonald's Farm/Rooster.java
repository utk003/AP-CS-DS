
/**
 * A rooster class that extends the Chicken class
 * 
 * @author Utkarsh Priyam
 * @author Daniel Wu
 * @version 10/20/2018
 */
public class Rooster extends Chicken
{
    /**
     * Constructor for objects of class Rooster. It calls the super
     * constructor.
     */
    public Rooster()
    {
        super("rooster");
    }
    
    /**
     * Returns the sound that a rooster makes
     * 
     * @return the String literal "cock-a-doodle-do"
     */
    @Override
    public String speak()
    {
        return "cock-a-doodle-do";
    }
}
