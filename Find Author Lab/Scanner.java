import java.io.IOException;
import java.io.Reader;

/**
 * A Scanner is responsible for reading an input stream, one character at a
 * time, and separating the input into tokens.  A token is defined as:
 *  1. A 'word' which is defined as a non-empty sequence of characters that 
 *     begins with an alpha character and then consists of alpha characters, 
 *     numbers, the single quote character "'", or the hyphen character "-". 
 *  2. An 'end-of-sentence' delimiter defined as any one of the characters 
 *     ".", "?", "!".
 *  3. An end-of-file token which is returned when the scanner is asked for a
 *     token and the input is at the end-of-file.
 *  4. A phrase separator which consists of one of the characters ",",":" or
 *     ";".
 *  5. A digit.
 *  6. Any other character not defined above.
 * 
 * @author Mr. Page
 * @author Utkarsh Priyam
 * @version 1 February 2019
 */
public class Scanner
{
    private Reader in;
    private String currentChar;
    private boolean endOfFile;
    /**
     * Defines symbolic constants for each type of token
     */
    public static enum TOKEN_TYPE
    {
        /**
         * This type of token represents a word.
         * It is composed of letters, digits, and special characters.
         */
        WORD,
        /**
         * This type of token represents the end of a sentence.
         */
        END_OF_SENTENCE,
        /**
         * This type of token represents the end of the file.
         */
        END_OF_FILE,
        /**
         * This type of token represents the end of a phrase.
         */
        END_OF_PHRASE,
        /**
         * This type of token represents a digit (0-9).
         */
        DIGIT,
        /**
         * This type of token represents anything else
         * which is not covered by the 5 token types above.
         */
        UNKNOWN
    }
    /**
     * Constructor for Scanner objects.  The Reader object should be one of
     *  1. A StringReader
     *  2. A BufferedReader wrapped around an InputStream
     *  3. A BufferedReader wrapped around a FileReader
     *  The instance field for the Reader is initialized to the input parameter,
     *  and the endOfFile indicator is set to false.  The currentChar field is
     *  initialized by the getNextChar method.
     * @param in is the reader object supplied by the program constructing
     *        this Scanner object.
     */
    public Scanner(Reader in)
    {
        this.in = in;
        endOfFile = false;
        getNextChar();
    }
    
    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java String object.
     * postcondition: The input stream is advanced one character if it is not at
     * end of file and the currentChar instance field is set to the String 
     * representation of the character read from the input stream.  The flag
     * endOfFile is set true if the input stream is exhausted.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1) 
                endOfFile = true;
            else 
                currentChar = "" + (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Gets the next character in the file if str equals the current char
     * and throws an IllegalArgumentException if not.
     * 
     * @param str   the string that should equal the current char
     */
    private void eat( String str )
    {
        if ( currentChar.equals(str) )
            getNextChar();
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * Returns true if str is an upper or lower case letter,
     * and return false otherwise.
     * 
     * @param str       the character to be checked
     * @precondition    str is a single character
     * @return          true if str is an upper or lower case letter; otherwise,
     *                  false
     */
    private boolean isLetter( String str )
    {
        return str.compareTo("a") >= 0 && str.compareTo("z") <= 0 ||
               str.compareTo("A") >= 0 && str.compareTo("Z") <= 0;
    }
    
    /**
     * Returns true if str is a digit (0-9),
     * and return false otherwise.
     * 
     * @param str       the character to be checked
     * @precondition    str is a single character
     * @return          true if str is a digit (0-9); otherwise,
     *                  false
     */
    private boolean isDigit( String str )
    {
        return str.compareTo("0") >= 0 && str.compareTo("9") <= 0;
    }
    
    /**
     * Returns true if str is a special character ("'" or "-"),
     * and return false otherwise.
     * 
     * @param str       the character to be checked
     * @precondition    str is a single character
     * @return          true if str is "'" or "-"; otherwise,
     *                  false
     */
    private boolean isSpecialChar( String str )
    {
        return str.equals("'") || str.equals("-");
    }
    
    /**
     * Returns true if str is a phrase terminator ("," or ":" or ";"),
     * and return false otherwise.
     * 
     * @param str       the character to be checked
     * @precondition    str is a single character
     * @return          true if str is "," or ":" or ";"; otherwise,
     *                  false
     */
    private boolean isPhraseTerminator( String str )
    {
        return str.equals(",") || str.equals(":") || str.equals(";");
    }
    
    /**
     * Returns true if str is a sentence terminator ("." or "?" or "!"),
     * and return false otherwise.
     * 
     * @param str       the character to be checked
     * @precondition    str is a single character
     * @return          true if str is "." or "?" or "!"; otherwise,
     *                  false
     */
    private boolean isSentenceTerminator( String str )
    {
        return str.equals(".") || str.equals("?") || str.equals("!");
    }
    
    /**
     * Returns true if str is a sentence terminator (" " or "\t" or "\n" or "\r"),
     * and return false otherwise.
     * 
     * @param str       the character to be checked
     * @precondition    str is a single character
     * @return          true if str is " " or "\t" or "\n" or "\r"; otherwise,
     *                  false
     */
    private boolean isWhiteSpace( String str )
    {
        return str.equals(" ") || str.equals("\t") || str.equals("\n") || str.equals("\r");
    }
    
    /**
     * Returns whether or not the file has another token
     * 
     * @return  true if the Reader has not reached the end of the file; otherwise,
     *          false
     */
    public boolean hasNextToken()
    {
        return ! endOfFile;
    }
    
    /**
     * Returns the next Token in the file.
     * A Token is defined as:
     * 1) A single digit        -   Token Type: DIGIT
     * 2) A sentence terminator -   Token Type: END_OF_SENTENCE
     * 3) A phrase terminator   -   Token Type: END_OF_PHRASE
     * 4) A file terminator     -   Token Type: END_OF_FILE
     * 5) A string of letters,
     *    digits, and special
     *    characters that
     *    starts with a letter  -   Token Type: WORD
     * 6) Anything else         -   Token Type: UNKNOWN
     * 
     * @return  a new Token which falls under one of the above classifications
     *          (based off the current character) and is labelled with
     *          its corresponding token type
     */
    public Token nextToken()
    {
        if ( endOfFile )
            return new Token( TOKEN_TYPE.END_OF_FILE, "" );
        while ( isWhiteSpace(currentChar) )
        {
            eat(currentChar);
            if ( endOfFile )
                return new Token( TOKEN_TYPE.END_OF_FILE, "" );
        }
        Token obj;
        if ( ! isLetter(currentChar) )
        {
            if ( isDigit(currentChar) )
                obj = new Token( TOKEN_TYPE.DIGIT, currentChar );
            else if ( isPhraseTerminator(currentChar) )
                obj = new Token( TOKEN_TYPE.END_OF_PHRASE, currentChar );
            else if ( isSentenceTerminator(currentChar) )
                obj = new Token( TOKEN_TYPE.END_OF_SENTENCE, currentChar );
            else
                obj = new Token( TOKEN_TYPE.UNKNOWN, currentChar );
            eat(currentChar);
            return obj;
        }
        String token = "";
        while ( hasNextToken() && (isLetter(currentChar) ||
                isDigit(currentChar) || isSpecialChar(currentChar)) )
        {
            token += currentChar;
            eat(currentChar);
        }
        return new Token( TOKEN_TYPE.WORD, token );
    }
}
