import java.util.*;
/**
 * The Phrase class holds a single phrase,
 * which is defined as an ordered collection
 * of all the word-type tokens in that phrase.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Phrase
{
    private List<Token> tokens;
    
    /**
     * A constructor for the Phrase class.
     */
    public Phrase()
    {
        tokens = new ArrayList<Token>();
    }
    
    /**
     * Adds the given token to the phrase
     * @param token     The token to add to the phrase
     * @precondition    The token is a word-type token
     */
    public void addToken( Token token )
    {
        tokens.add( token );
    }
    
    /**
     * Duplicates the phrase by returning a deep copy of the phrase.
     * @return  a deep copy of the phrase
     */
    public List<Token> duplicatePhrase()
    {
        List<Token> phrase = new ArrayList<Token>();
        for ( Token token : tokens )
            phrase.add( new Token(token.getType(), token.getValue()) );
        return phrase;
    }
    
    /**
     * Returns the string form of the phrase
     * @return  the string form of the phrase
     */
    public String toString()
    {
        if ( tokens.size() == 0 )
            return "";
        String stringForm = "";
        for ( Token token : tokens )
            stringForm += "\n" + token;
        return stringForm;
    }
}
