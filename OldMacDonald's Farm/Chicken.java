
/**
 * A generic chicken class that extends the abstract Animal class
 * 
 * @author Utkarsh Priyam
 * @author Daniel Wu
 * @version 10/20/2018
 */
public class Chicken extends Animal
{   
    /**
     * Constructor for objects of class Chicken
     */
    public Chicken()
    {
        this("chicken");
    }
    
    /**
     * Constructor for objects of class Chicken
     * 
     * @param chickenType the type of chicken with a specific commonName
     */
    public Chicken(String chickenType)
    {
        super("Gallus Gallus Domesticus", chickenType);
    }
    
    /**
     * Returns the sound that a chicken makes
     * 
     * @return the String literal "bawk"
     */
    public String speak()
    {
        return "bawk";
    }
}
