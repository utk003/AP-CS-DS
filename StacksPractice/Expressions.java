import java.util.Stack;

/**
 * This class uses static methods which can
 * check if expressions are balanced,
 * convert infix expressions to postfix form,
 * and calculate the value of expressions in postfix form.
 * 
 * @author Utkarsh Priyam
 * @version 10/26/2018
 */

public class Expressions
{
    /**
     * This method checks if an expression has balanced parentheses, brackets, and braces.
     * An expression is said to be balanced if every opening parenthesis, bracket, and brace
     * has a corresponding closer, in the right order.
     * @param expression    the expression containing operands, operators
     *                      and any of the 3 supported parentheses, brackets, and braces
     * @return              true if the parentheses, brackets, and braces are balanced; otherwise,
     *                      false
     */
    public static boolean matchParenthesis(String expression)
    {
        Stack s = new Stack();
        for ( int i = 0; i < expression.length(); i++ )
        {
            String str = expression.substring(i,i+1);
            if ( str.equals("(") || str.equals("[") || str.equals("{") )
                s.push(str);
            else if ( str.equals(")") || str.equals("]") || str.equals("}") )
            {
                if ( s.isEmpty() )
                    return false;
                if ( ! matches( (String) s.pop(), str ) )
                    return false;
            }
        }
        return s.isEmpty();
    }
    
    /**
     * Checks if the two input parameters are a pair (), [], or {}
     * @param s1    the opening parenthesis, bracket, or brace
     * @param s2    the closing parenthesis, bracket, or brace
     * @return      true if the two inputs match; otherwise,
     *              false
     */
    private static boolean matches( String s1, String s2 )
    {
        if ( s1.equals("(") && s2.equals(")") )
            return true;
        if ( s1.equals("[") && s2.equals("]") )
            return true;
        if ( s1.equals("{") && s2.equals("}") )
            return true;
        return false;
    }
    
    /**
     * Returns the postfix form of the given infix form of an expression
     * @param expr  a valid expression in infix form
     * @return      an equivalent expression in postfix form
     */
    public static String infixToPostfix(String expr)
    {
        Stack<String> postFix = new Stack<String>();
        String strPostfix = "";
        int spindex1 = -1, spindex2 = expr.indexOf(" ", spindex1 + 1);
        while ( spindex2 != -1 )
        {
            String s = expr.substring(spindex1 + 1, spindex2);
            if ( ! isOperator(s) )
                strPostfix += s + " ";
            else
            {
                while ( ! postFix.isEmpty() && isLesserOperator( s, postFix.peek() ) )
                    strPostfix += postFix.pop() + " ";
                postFix.push(s);
            }
            spindex1 = spindex2;
            spindex2 = expr.indexOf(" ", spindex2  + 1);
        }
        strPostfix += expr.substring(spindex1 + 1) + " ";
        while ( ! postFix.isEmpty() )
            strPostfix += postFix.pop() + " ";
        return strPostfix.substring(0,strPostfix.length()-1);
    }
    
    /**
     * Returns if the input is one of the 5 supported operators (+, -, *, /, or %).
     * @param s The (potential) operator
     * @return  true if the given string is a supported operator; otherwise,
     *          false
     */
    private static boolean isOperator( String s )
    {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%");
    }
    
    /**
     * Checks if the old operator from the stack is of
     * higher or lower precedence than the given one.
     * @param o     the new operator
     * @param o1    the operator from the stack
     * @return      true if the new operator o is of lower precedence than the old one; otherwise,
     *              false
     */
    private static boolean isLesserOperator( String o, String o1 )
    {
        return o.equals("+") || o.equals("-") || o1.equals("*") || o1.equals("/") || o1.equals("%");
    }

    /**
     * Returns the value of an expression in postfix form
     * @param expr      a valid expression in postfix form
     * @precondition    the postfix expression contains only numbers and the supported operators
     *                  (+, -, *, /, and %).
     * @return          the value of the expression
     */
    public static double evalPostfix(String expr)
    {
        Stack<Double> postfixOperands = new Stack<Double>();
        int spindex1 = -1, spindex2 = expr.indexOf(" ", spindex1 + 1);
        double i1, i2;
        while ( spindex2 != -1 )
        {
            String s = expr.substring(spindex1 + 1, spindex2);
            if ( isOperator(s) )
            {
                i2 = postfixOperands.pop().doubleValue();
                i1 = postfixOperands.pop().doubleValue();
                postfixOperands.push(new Double(calculatePostfix( i1, i2, s )));
            }
            else
                postfixOperands.push(Double.parseDouble(s));
            spindex1 = spindex2;
            spindex2 = expr.indexOf(" ", spindex2  + 1);
        }
        String s = expr.substring(expr.length() - 1);
        i2 = postfixOperands.pop().doubleValue();
        i1 = postfixOperands.pop().doubleValue();
        return calculatePostfix( i1, i2, s );
    }
    
    /**
     * Performs the binary operation op on i1 and i2 (returns i1 op i2)
     * @param i1        the first input of the binary operator
     * @param i2        the second input of the binary operator
     * @param op        the binary operator (+, -, *, /, or %)
     * @precondition    the operator is either +, -, *, /, or %
     * @return          the result of the binary operation op on i1 and i2
     */
    private static double calculatePostfix( double i1, double i2, String op )
    {
        if ( op.equals("+") )
            return i1 + i2;
        if ( op.equals("-") )
            return i1 - i2;
        if ( op.equals("*") )
            return i1 * i2;
        if ( op.equals("/") )
            return i1 / i2;
        return i1 % i2;
    }
    
    /**
     * A tester to check that the 3 above methods work as described.
     * Tests infixToPostfix(), evalPostfix(), and matchParenthesis().
     * @param args  The usual string array that comes with any main
     */
    public static void main(String[] args)
    {
        String exp = "2 + 3 * 4";
        test(exp, 14);
        
        exp = "8 * 12 / 2";
        test(exp, 48);

        exp = "5 % 2 + 3 * 2 - 4 / 2";
        test(exp, 5);   
        
        // test balanced expressions
        testBalanced("{ 2 + 3 } * ( 4 + 3 )", true);
        testBalanced("} 4 + 4 { * ( 4 + 3 )", false);
        testBalanced("[ [ [ ] ]", false);
        testBalanced("{ ( } )", false);
        testBalanced("( ( ( ) ) )", true);
    }
    
    /**
     * A specialized tester that tests infixToPostfix() and evalPostfix().
     * @param expr      the expression in infix form
     * @param expect    the expected value of the equation
     * @postcondition   prints out the intermediate steps and says if the methods work or not
     */
    public static void test(String expr, double expect)
    {
        String post = infixToPostfix(expr);
        double val = evalPostfix(post);

        System.out.println("Infix: " + expr);
        System.out.println("Postfix: " + post);
        System.out.println("Value: " + val);
        if (val == expect)
        {
            System.out.println("** Success! Great Job **");
        }
        else
        {
            System.out.print("** Oops! Something went wrong. ");
            System.out.println("Check your postfix and eval methods **");
        }
    }
    
    /**
     * A specialized tester that tests matchParenthesis().
     * @param ex        the expression that must be checked as balanced or not
     * @param expected  the expected state of the expression (balanced or not)
     * @postcondition   prints whether the method works or not
     */
    public static void testBalanced(String ex, boolean expected)
    {
        boolean act = matchParenthesis(ex);
        if (act == expected)
            System.out.println("** Success!: matchParenthesis(" + ex + ") returned " + act);
        else
        {
            System.out.print("** Oops! Something went wrong check : matchParen(" + ex + ")");
            System.out.println(" returned " + act + " but should have returned " + expected);
        }
    }
}
