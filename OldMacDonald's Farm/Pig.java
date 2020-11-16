
/**
 * A pig class that extends the Animal abstract class
 * 
 * @author Daniel Wu
 * @author Utkarsh Priyam
 * @version 10/20/2018
 */
public class Pig extends Animal
{
    /**
     * Constructor for objects of class Pig. It calls the 
     * constructor for the Animal abstract class.
     */
    public Pig()
    {
        super("Sus Scrofa Domesticus", "pig");
    }
    
    /**
     * Overrides the speak() method of the superclass to make
     * an oink sound
     * 
     * @return String literal "oink"
     */
    public String speak()
    {
        return "oink";
    }
}
