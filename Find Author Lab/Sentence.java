import java.util.*;
/**
 * The Sentence class holds a single sentence,
 * which is defined as an ordered collection
 * of all the phrases in that sentence.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Sentence
{
    private List<Phrase> phrases;
    
    /**
     * A constructor for the Sentence class.
     */
    public Sentence()
    {
        phrases = new ArrayList<Phrase>();
    }
    
    /**
     * Adds the given phrase to the sentence
     * @param phrase    The phrase to add to the sentence
     */
    public void addPhrase( Phrase phrase )
    {
        phrases.add( phrase );
    }
    
    /**
     * Duplicates the sentence by returning a deep copy of it.
     * @return  a deep copy of the sentence
     */
    public List<Phrase> duplicateSentence()
    {
        List<Phrase> sentence = new ArrayList<Phrase>();
        List<Token> list;
        Phrase phr;
        for ( Phrase phrase : phrases )
        {
            list = phrase.duplicatePhrase();
            phr = new Phrase();
            for ( Token token : list )
                phr.addToken( token );
            sentence.add( phr );
        }
        return sentence;
    }
    
    /**
     * Returns the string form of the sentence
     * @return  the string form of the sentence
     */
    public String toString()
    {
        if ( phrases.size() == 0 )
            return "";
        String stringForm = "";
        for ( Phrase phrase : phrases )
            stringForm += "\n" + phrase;
        return stringForm;
    }
}
