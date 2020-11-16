
/**
 * Creates a card class that has a rank and a suit. The card can be face up or down.
 * The card can be flipped up or down. The card can return its rank, suit, and color.
 * 
 * @author Utkarsh Priyam
 * @version 10-26-2018
 */
public class Card
{
    // instance variables - replace the example below with your own
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Constructor for objects of class Card
     * @param rank  the rank of the new card  
     * @param suit  the suit of the new card
     */
    public Card( int rank, String suit )
    {
        this.rank = rank;
        this.suit = suit;
        isFaceUp = false;
    }
    
    /**
     * Returns the rank of the card as an integer between 1 and 13,
     * where 1 represents an ace and 13 is a king.
     * @return  the rank of the card
     */
    public int getRank()
    {
        return rank;
    }
    
    /**
     * Returns the suit of the card as a single character.
     * C is clubs, H is hearts, D is diamonds, and S is spades.
     * @return  the suit of the card
     */
    public String getSuit()
    {
        return suit;
    }
    
    /**
     * Returns if the card is red (hearts and diamonds) or not (clubs and spades).
     * @return  true if the card is red; otherwise,
     *          false;
     */
    public boolean isRed()
    {
        return suit.equals("h") || suit.equals("d");
    }
    
    /**
     * Returns if the card is face up or not.
     * @return  true if the card is facing up; otherwise,
     *          false
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }
    
    /**
     * Turns the card face up.
     * @postcondition   the card is facing up
     */
    public void turnUp()
    {
        isFaceUp = true;
    }
    
    /**
     * Turns the card face down.
     * @postcondition   the card is facing down
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
    
    /**
     * Returns the location of the card image file as a string
     * @return  the location of the card image file as a string
     */
    public String getFileName()
    {
        if ( ! isFaceUp )
            return "cards/backsolitaire.gif";
        String[] ranks = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k"};
        return "cards/" + ranks[rank-1] + suit + ".gif";
    }
}
