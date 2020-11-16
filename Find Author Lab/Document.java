import java.util.*;
/**
 * The Document class represents an entire document,
 * and its parseDocument() method allows it to
 * parse any document and store the document as a list of sentences.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Document
{
    private Scanner sc;
    private Token currentToken;
    private List<Sentence> sentences;
    private boolean doneReading;
    
    /**
     * A constructor for the Document class
     * @param scanner   The scanner which will be parsing a file
     *                  for this Document one token at a time
     */
    public Document( Scanner scanner )
    {
        sc = scanner;
        getNextToken();
        sentences = new ArrayList<Sentence>();
        doneReading = false;
    } 
    
    /**
     * Retrieves and stores the next token in the file from the scanner.
     */
    private void getNextToken()
    {
        if ( sc.hasNextToken() )
            currentToken = sc.nextToken();
    }
    
    /**
     * Gets the next token in the file if t equals the current token
     * and throws an IllegalArgumentException if not.
     * @param t The token that should equal the current token
     */
    private void eat(Token t)
    {
        if ( currentToken.equals(t) )
            getNextToken();
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * Parses a phrase from the tokens it receives from the scanner.
     * This method continues to parse until it reaches a token which represents
     * the end of a phrase, the end of a sentence, or the end of the file.
     * (END_OF_PHRASE, END_OF_SENTENCE, and END_OF_FILE, respectively).
     * 
     * @return  the fully parsed phrase.
     */
    public Phrase parsePhrase()
    {
        Phrase currentPhrase = new Phrase();
        Scanner.TOKEN_TYPE type = currentToken.getType();
        while ( ! type.equals(Scanner.TOKEN_TYPE.END_OF_PHRASE) &&
                ! type.equals(Scanner.TOKEN_TYPE.END_OF_FILE) &&
                ! type.equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE) )
        {
            if ( type.equals(Scanner.TOKEN_TYPE.WORD) )
                currentPhrase.addToken(currentToken);
            eat(currentToken);
            type = currentToken.getType();
        }
        if ( type.equals(Scanner.TOKEN_TYPE.END_OF_PHRASE) )
            eat(currentToken);
        return currentPhrase;
    }
    
    /**
     * Parses a sentence using the phrases that are returned by the parsePhrase() method.
     * This method continues to parse until it reaches a token which represents
     * the end of a sentence or the end of the file.
     * (END_OF_SENTENCE and END_OF_FILE, respectively).
     * 
     * @return  the fully parsed sentence.
     */
    public Sentence parseSentence()
    {
        Sentence currentSentence = new Sentence();
        Scanner.TOKEN_TYPE type = currentToken.getType();
        while ( ! type.equals(Scanner.TOKEN_TYPE.END_OF_FILE) &&
                ! type.equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE) )
        {
            currentSentence.addPhrase( parsePhrase() );
            type = currentToken.getType();
        }
        if ( type.equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE) )
            eat(currentToken);
        return currentSentence;
    }
    
    /**
     * Parses the entire file using the sentences
     * that are returned by the parseSentence() method.
     * This method continues to parse until it reaches the end of the file.
     */
    public void parseDocument()
    {
        Scanner.TOKEN_TYPE type = currentToken.getType();
        while ( ! doneReading )
        {
            sentences.add( parseSentence() );
            type = currentToken.getType();
            doneReading = ! sc.hasNextToken();
        }
    }
    
    /**
     * Returns a very shallow copy of the document
     * (all the sentences are simply referenced to a new list).
     * 
     * @return  a shallow copy of the document
     */
    public List<Sentence> copyDocument()
    {
        List<Sentence> dupeSentences = new ArrayList<Sentence>();
        for ( Sentence s: sentences )
            dupeSentences.add(s);
        return dupeSentences;
    }
    
    /**
     * Returns a deep copy of the document.
     * @return  a deep copy of the document
     */
    public List<Sentence> duplicateDocument()
    {
        List<Sentence> dupeSentences = new ArrayList<Sentence>();
        Sentence sentence;
        for ( Sentence s: sentences )
        {
            List<Phrase> dupePhrases = s.duplicateSentence();
            sentence = new Sentence();
            for ( Phrase p: dupePhrases )
                sentence.addPhrase( p );
            dupeSentences.add( sentence );
        }
        return dupeSentences;
    }
    
    /**
     * Returns the string form of the document
     * @return  the string form of the document
     */
    public String toString()
    {
        if ( sentences.size() == 0 )
            return "";
        String stringForm = "";
        for ( Sentence sentence : sentences )
            stringForm += "\n" + sentence;
        return stringForm;
    }
}