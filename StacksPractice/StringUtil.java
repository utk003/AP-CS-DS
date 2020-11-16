import java.util.Stack;

/**
 * This class uses static methods to reverse strings and
 * check if a given string is a palindrome or not.
 * 
 * @author Utkarsh Priyam
 * @version 10/26/2018
 */
public class StringUtil
{
    /**
     * Reverse a string using stacks
     * @param str   the string to reverse
     * @return      the reversed string
     */
    public static String reverseString(String str)
    {
        Stack s = new Stack();
        String sol = "";
        for ( int i = 0; i < str.length(); i++ )
            s.push( str.substring(i,i+1) );
        while ( ! s.isEmpty() )
            sol += s.pop();
        return sol;
    }

    /**
     * Checks if a given string is a palindrome or not
     * @param s The string to be checked as palindromic or not
     * @return  true if the string s is palindromic; otherwise,
     *          false
     */
    public static boolean isPalindrome(String s)
    {
        return reverseString(s).equals(s);
    }

    /**
     * A tester to check that the above methods work as described.
     * Tests reverseString() and (indirectly) isPalindrome().
     * @param args  The usual string array that comes with any main
     */
    public static void main(String[] args)
    {
        String test =  "racecar";
        String test2 = "notapalindrome";

        if ( !("".equalsIgnoreCase(reverseString(""))) )
            System.out.println("** Oops Something went wrong. Check your reverse method **");

        if ( !("a".equalsIgnoreCase(reverseString("a"))) )
            System.out.println("** Oops Something went wrong. Check your reverse method **");

        if (!test.equalsIgnoreCase(reverseString(test)))
            System.out.println("** Oops Something went wrong. Check your reverse method **");
        else
            System.out.println("Success " + test + " matched " + reverseString(test));
            
        if (test2.equalsIgnoreCase(reverseString(test2)))
            System.out.println("** Oops Something went wrong. Check your reverse method **");

    }
}
