import java.util.*;
/**
 * This Token class holds a string value and
 * has a token type, which is one of the
 * 6 types defined in the Scanner class.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public final class Token implements Comparable<Token>
{
    private final Scanner.TOKEN_TYPE type;
    private final String value;
    /**
     * Constructor for objects of class Token
     * @param obj   the type of Token this token represents
     * @param val   the string value of this token
     */
    public Token( Scanner.TOKEN_TYPE obj, String val )
    {
        type = obj;
        value = val.toLowerCase();
    }
    
    /**
     * Returns the type of this token
     * @return  This token's token type
     */
    public Scanner.TOKEN_TYPE getType()
    {
        return type;
    }
    
    /**
     * Returns the string value of the token
     * @return  The string value of the token
     */
    public String getValue()
    {
        return value;
    }
    
    /**
     * Returns the string form of the token
     * @return  the string form of the token
     */
    @Override
    public String toString()
    {
        return "Value Stored: " + value + "\tType Stored: " + type;
    }
    
    /**
     * Returns whether or not this and the given token are equal
     * @param obj       The object to compare to
     * @precondition    obj instanceof Token returns true
     * @return          true if this and obj have the same
     *                  token type and value; otherwise,
     *                  false
     */
    @Override
    public boolean equals( Object obj )
    {
        Token token = (Token) obj;
        return type.equals( token.type ) && value.equals( token.value );
    }
    
    /**
     * Returns the relationship between this
     * and the given token as an integer.
     * A negative number means that this < t,
     * a positive number means that this > t, and
     * 0 means that this = t, or this.equals(t) returns true.
     * 
     * @param t         The object to compare to
     * @precondition    obj instanceof Token returns true
     * @return          A negative number if this < t,
     *                  a positive number if this > t, and
     *                  0 otherwise (ie this = t).
     */
    @Override
    public int compareTo( Token t )
    {
        return value.compareTo( t.getValue() );
    }
    
    /**
     * Returns the hash of this token using
     * the excellent java string hash
     * @return  the hash of this token
     */
    @Override
    public int hashCode()
    {
        return value.hashCode();
    }
}
