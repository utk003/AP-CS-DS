
import java.util.*;

/**
 * A tester class for the Animal abstract class and related subclasses
 * that sings the OldMacDonalds song.
 * 
 * @author Daniel Wu
 * @author Utkarsh Priyam
 * @version 10/20/2018
 */
public class OldMacDonaldsFarm
{
    private String farmerName;
    private ArrayList<Object> farmAnimals;

    /**
     * Constructor for the OldMacDonald's farm. It instantiates
     * the instance variables farmerName and farmAnimals.
     */
    public OldMacDonaldsFarm()
    {
        farmerName = "OldMacDonald";
        farmAnimals = new ArrayList<Object>();
    }

    /**
     * Main tester method for the Animal abstract class and associated subclasses
     * 
     * @param args arguments from the compiler
     */
    public static void main( String[] args )
    {
        OldMacDonaldsFarm singer = new OldMacDonaldsFarm();
        singer.farmAnimals.add( new Chicken() );
        singer.singVerse();
        singer.farmAnimals.add( "abc" );
        singer.singVerse();
        singer.farmAnimals.add( new Rooster() );
        singer.singVerse();
        singer.farmAnimals.add( new Pig() );
        singer.singVerse();
    }

    /**
     * A helper method that returns one repetition of an animal sound line
     * 
     * @param sound the sound that the animal makes
     * 
     * @return one line in the song tha thas the animal sounds
     */
    private String verseRep( String sound )
    {
        return "With a " + sound + "-" + sound + " here, and a " + sound + "-" + sound +
               " there,\n" + "Here a " + sound + ", there a " + sound + ", every where a " +
               sound + "-" + sound + ",\n";
    }

    /**
     * Sings (prints) a single verse of OldMacDonald's song
     */
    public void singVerse()
    {
        try
        {
            Object ob = farmAnimals.get(farmAnimals.size() - 1);
            if ( !(ob instanceof Animal) )
                throw new ClassCastException("Object must be an animal! Please correct this!");
            String p1 = farmerName + " had a farm,";
            String animal = ((Animal) ob).getCommonName();
            String p2 = "And on his farm he had some " + animal + "s,";
            String allSounds = "";
            for ( Object obj: farmAnimals )
                allSounds = verseRep( ((Animal) obj).speak() ) + allSounds;
            String ei = " E-I-E-I-O. ";
            System.out.print("\n\n\n" + p1 + ei + p2 + ei + "\n" + allSounds + p1 + ei);
        }
        catch ( ClassCastException e )
        {
            System.out.println("\n\n\n\n" + e.getMessage());
            farmAnimals.remove(farmAnimals.size() - 1);
        }
    }
}
