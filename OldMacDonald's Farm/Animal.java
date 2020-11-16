
/**
 * Abstract class Animal - An abstract class for animals
 * 
 * @author Utkarsh Priyam
 * @author Daniel Wu
 * @version 10/18/2018
 */
public abstract class Animal implements Comparable
{
    // instance variables - replace the example below with your own
    private String latinName;
    private String commonName;
    
    /**
     * A constructor that instantiates the instance variables
     * latinName and CommonName
     * 
     * @param latin the latin name
     * 
     * @param common the common name
     */
    public Animal(String latin, String common)
    {
        latinName = latin;
        commonName = common;
    }
    
    /**
     * A getter method for the latinName of the animal that returns
     * the String latinName
     * 
     * @return latinName the String containing latin name
     */
    public String getLatinName()
    {
        return latinName;
    }

    /**
     * A setter method for the latinName of the animal that resets
     * the String latinName
     * 
     * @param latin the new latin name
     */
    public void setLatinName(String latin)
    {
        latinName = latin;
    }

    /**
     * A getter method for the commonName of the animal that returns
     * the String commonName
     * 
     * @return commonName the String containing the common name
     */
    public String getCommonName()
    {
        return commonName;
    }

    /**
     * A setter method for the latinName of the animal that resets
     * the String latinName
     * 
     * @param common the new latin name
     */
    public void setCommonName(String common)
    {
        commonName = common;
    }
    
    /**
     * An abstract class that returns the subclass's animal sound
     * 
     * @return the sound that the specific animal makes
     */
    public abstract String speak();
    
    /**
     * Implements the compareTo method from the Comparable interface to compare the
     * common names of the animals
     * 
     * @param obj the animal that is being compare to the caller
     * @return 0 if the common names are equal, not 0 otherwise
     */
    @Override
    public int compareTo( Object obj )
    {
        try
        {
            if ( obj instanceof Animal )
                return ((Animal) obj).getCommonName().compareTo( getCommonName() );
            else
                throw new ClassCastException("Object must be an animal! Please correct this!");
        }
        catch ( ClassCastException e )
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
