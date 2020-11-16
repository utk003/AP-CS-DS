import java.util.*;
/**
 * TreeUtil contains the following methods for manipulating binary trees:
 * leftmost(), rightmost(), maxDepth(), createRandom(), countNodes(), countLeaves(),
 * preOrder(), inOrder(), postOrder(), fillList(), saveTree(), buildTree(), loadTree(),
 * copy(), sameShape(), createDecodingTree(), decodeMorse(), eval(), and createExpressionTree().
 * @author Utkarsh Priyam
 * @version 11/29/18
 */
public class TreeUtil
{
    //used to prompt for command line input
    private static Scanner in = new Scanner(System.in);
    
    private static final boolean DEBUG = false;
    
    /**
     * Returns the value of leftmost node in the tree with root t.
     * @param t       the root of the tree
     * @return        the value of the leftmost node of the tree, or
     *                null if is the tree is null
     */
    public static Object leftmost(TreeNode t)
    {
        if ( t == null )
            return null;
        if ( t.getLeft() == null )
            return t.getValue();
        return leftmost( t.getLeft() );
    }

    /**
     * Returns the value of rightmost node in the tree with root t.
     * @param t       the root of the tree
     * @return        the value of the rightmost node of the tree, or
     *                null if is the tree is null
     */
    public static Object rightmost(TreeNode t)
    {
        if ( t == null )
            return null;
        if ( t.getRight() == null )
            return t.getValue();
        return rightmost( t.getRight() );
    }
    
    /**
     * Returns the max depth of a tree with root t.
     * @param t       the root of the tree
     * @return        the maximum depth of the tree
     */
    public static int maxDepth(TreeNode t)
    {
        if ( t == null )
            return 0;
        return Math.max( maxDepth(t.getLeft()), maxDepth(t.getRight()) ) + 1;
    }

    /**
     * Creates a random tree of the specified depth.
     * Does not attempt to balance the tree.
     * @param      depth of the tree
     * @return     the root of the randomly generated tree
     */
    public static TreeNode createRandom(int depth)
    {
        if (Math.random() * Math.pow(2, depth) < 1)
            return null;
        return new TreeNode(((int)(Math.random() * 10)),
            createRandom(depth - 1),
            createRandom(depth - 1));
    }
    
    /**
     * Counts the number of nodes in a tree with root t
     * @param t      the root of the tree
     * @return       the number of nodes in the tree
     */
    public static int countNodes(TreeNode t)
    {
        if ( t == null )
            return 0;
        return 1 + countNodes( t.getLeft() ) + countNodes( t.getRight() );
    }
    
    /**
     * Counts the number of leaves in a tree with root t
     * @param t      the root of the tree
     * @return       the number of leaves in the tree
     */
    public static int countLeaves(TreeNode t)
    {
        if ( t == null )
            return 0;
        if ( t.getLeft() == null && t.getRight() == null )
            return 1;
        return countLeaves( t.getLeft() ) + countLeaves( t.getRight() );
    }
    
    /**
     * Traverses the tree in preorder (root, left, right)
     * @param t         the root of the tree
     * @param display   the display on which the tree is shown
     */
    public static void preOrder(TreeNode t, TreeDisplay display)
    {
        if ( t == null )
            return;
        display.visit(t);
        preOrder(t.getLeft(),display);
        preOrder(t.getRight(),display);
    }
    
    /**
     * Traverses the tree in inorder (left, root, right)
     * @param t         the root of the tree
     * @param display   the display on which the tree is shown
     */
    public static void inOrder(TreeNode t, TreeDisplay display)
    {
        if ( t == null )
            return;
        inOrder(t.getLeft(),display);
        display.visit(t);
        inOrder(t.getRight(),display);
    }
    
    /**
     * Traverses the tree in postorder (left, right, root)
     * @param t         the root of the tree
     * @param display   the display on which the tree is shown
     */
    public static void postOrder(TreeNode t, TreeDisplay display)
    {
        if ( t == null )
            return;
        postOrder(t.getLeft(),display);
        postOrder(t.getRight(),display);
        display.visit(t);
    }
    
    /**
     * Fills a list with the contents of a tree (in preorder, null is $).
     * @param t      the root of the tree
     * @param list   the list to be filled with the contents of the tree
     */
    public static void fillList(TreeNode t, List<String> list)
    {
        if ( t == null )
        {
            list.add("$");
            return;
        }
        list.add(t.getValue().toString());
        fillList(t.getLeft(),list);
        fillList(t.getRight(),list);
    }
    
    /**
     * Uses the FileUtil utility class to save the tree rooted at t
     * as a file with the given file name
     * @param fileName    is the name of the file to create which will
     *                    hold the data values in the tree
     * @param t           the root of the tree to save
     */
    public static void saveTree(String fileName, TreeNode t)
    {
        List<String> list = new ArrayList<String>();
        fillList(t, list);
        try
        {
            FileUtil.saveFile(fileName, list.iterator());
        }
        catch ( RuntimeException e )
        {
            System.out.println( e.getMessage() );
        }
    }
    
    /**
     * Uses an iterator to iterate through a valid description of a binary tree with String values.
     * Null nodes are indicated by "$" markers
     * @param it   the iterator which will iterate over the tree description
     * @return     the root of the tree built by the iteration
     */
    public static TreeNode buildTree(Iterator<String> it)
    {
        if ( ! it.hasNext() )
            return null;
        String val = it.next();
        if ( val.equals("$") )
            return null;
        TreeNode t = new TreeNode(val);
        t.setLeft( buildTree(it) );
        t.setRight( buildTree(it) );
        return t;
    }
    
    /**
     * Reads a file description of a tree and then builds the tree
     * @param fileName   a valid file name for a file that describes a binary tree
     * @precondition     fileName is a valid file name for a file that describes a binary tree
     * @return           the root of the tree
     */
    public static TreeNode loadTree(String fileName)
    {
        Iterator<String> it;
        try
        {
            it = FileUtil.loadFile(fileName);
        }
        catch ( RuntimeException e )
        {
            throw e;
        }
        return buildTree(it);
    }
    
    //     /**
    //      * utility method that waits for a user to type text into Std Input and then press enter
    //      * @return the string entered by the user
    //      */
    //     private static String getUserInput()
    //     {
    //         return in.nextLine();
    //     }
    //     
    //     /**
    //      * plays a single round of 20 questions
    //      * postcondition:  plays a round of twenty questions, asking the user questions as it
    //      *                 walks down the given knowledge tree,
    //      *                 lighting up the display as it goes;
    //      *                 modifies the tree to include information learned.
    //      * @param t a pointer to the root of the game tree
    //      * @param display which will show the progress of the game
    //      */
    //     private static void twentyQuestionsRound(TreeNode t, TreeDisplay display)
    //     {   
    //         throw new RuntimeException("Write ME!");
    //     }
    //     
    //     /** 
    //      * plays a game of 20 questions
    //      * Begins by reading in a starting file and then plays multiple rounds
    //      * until the user enters "quit".  Then the final tree is saved
    //      */
    //     public static void twentyQuestions()
    //     {
    //         throw new RuntimeException("Write ME!");
    // }
    
    /**
     * Copies a binary tree
     * @param t  the root of the tree to copy
     * @return   a new tree, which is a complete copy
     *           of t with all new TreeNode objects
     *           pointing to the same values as t
     *           (in the same order, shape, etc)
     */
    public static TreeNode copy(TreeNode t)
    {
        if ( t == null )
            return null;
        return new TreeNode( t.getValue(), copy(t.getLeft()), copy(t.getRight()) );
    }
    
    /**
     * Tests to see if two trees have the same shape,
     * but not necessarily the same values.
     * Two trees have the same shape if they have TreeNode objects
     * in the same locations relative to the root
     * @param t1    pointer to the root of the first tree
     * @param t2    pointer to the root of the second tree
     * @return      true if t1 and t2 describe trees having the same shape,
     *              false otherwise
     */
    public static boolean sameShape(TreeNode t1, TreeNode t2)
    {
        if ( (t1 == null) != (t2 == null) )
            return false;
        if ( t1 == null )
            return true;
        return sameShape( t1.getLeft(), t2.getLeft() ) &&
               sameShape( t1.getRight(), t2.getRight() );
    }
    
    /**
     * Generates a tree for decoding Morse code
     * @param display    the display that will show the decoding tree
     * @return           the decoding tree
     */
    public static TreeNode createDecodingTree(TreeDisplay display)
    {
        TreeNode tree = new TreeNode("Morse Tree");
        display.displayTree(tree);
        insertMorse(tree, "e", ".", display);
        insertMorse(tree, "i", "..", display);
        insertMorse(tree, "s", "...", display);
        insertMorse(tree, "h", "....", display);
        insertMorse(tree, "v", "...-", display);
        insertMorse(tree, "u", "..-", display);
        insertMorse(tree, "f", "..-.", display);
        insertMorse(tree, "a", ".-", display);
        insertMorse(tree, "r", ".-.", display);
        insertMorse(tree, "l", ".-..", display);
        insertMorse(tree, "w", ".--", display);
        insertMorse(tree, "p", ".--.", display);
        insertMorse(tree, "j", ".---", display);
        insertMorse(tree, "t", "-", display);
        insertMorse(tree, "n", "-.", display);
        insertMorse(tree, "d", "-..", display);
        insertMorse(tree, "b", "-...", display);
        insertMorse(tree, "x", "-..-", display);
        insertMorse(tree, "k", "-.-", display);
        insertMorse(tree, "c", "-.-.", display);
        insertMorse(tree, "y", "-.--", display);
        insertMorse(tree, "m", "--", display);
        insertMorse(tree, "g", "--.", display);
        insertMorse(tree, "z", "--..", display);
        insertMorse(tree, "q", "--.-", display);
        insertMorse(tree, "o", "---", display);
        return tree;
    }
    
    /**
     * Helps build a Morse code decoding tree.
     * @param decodingTree    the partial decoding tree
     * @param letter          the letter to add
     * @param code            the Morse code for letter
     * @param display         the display that will show progress
     *                        as the method walks down the tree
     * @postcondition         inserts the given letter into the
     *                        decodingTree in the appropriate position,
     *                        as determined by the given Morse code sequence;
     *                        lights up the display as it walks down the tree
     */
    private static void insertMorse(TreeNode decodingTree, String letter,
                                    String code, TreeDisplay display)
    {
        display.visit( decodingTree );
        if ( code.equals(".") )
            decodingTree.setLeft( new TreeNode(letter) );
        else if ( code.equals("-") )
            decodingTree.setRight( new TreeNode(letter) );
        else if ( code.substring(0,1).equals(".") )
            insertMorse( decodingTree.getLeft(), letter, code.substring(1), display );
        else
            insertMorse( decodingTree.getRight(), letter, code.substring(1), display );
    }
    
    /**
     * Decodes Morse code by walking the decoding tree according to the input code
     * @param decodingTree   the Morse code decoding tree
     * @param cipherText     Morse code consisting of dots, dashes, and spaces
     * @param display        the display object that will show the decoding progress
     * @return               the string represented by cipherText
     */
    public static String decodeMorse(TreeNode decodingTree, String cipherText, TreeDisplay display)
    {
        int spindex = cipherText.indexOf(" ");
        if ( spindex == -1 )
            return decodeMorseChar( decodingTree, cipherText, display );
        return decodeMorseChar( decodingTree, cipherText.substring(0,spindex), display ) +
               decodeMorse( decodingTree, cipherText.substring(spindex + 1), display );
    }
    
    /**
     * Decodes a Morse code character by walking the decoding tree according to the input code
     * @param decodingTree   the Morse code decoding tree
     * @param morseChar      a Morse code character consisting of dots and dashes
     * @param d              the display object that will show the decoding progress
     * @return               the character/letter represented by morseChar
     */
    private static String decodeMorseChar(TreeNode decodingTree, String morseChar, TreeDisplay d)
    {
        d.visit( decodingTree );
        if ( morseChar.equals("") )
            return decodingTree.getValue().toString();
        if ( morseChar.substring(0,1).equals(".") )
            return decodeMorseChar( decodingTree.getLeft(), morseChar.substring(1), d);
        return decodeMorseChar( decodingTree.getRight(), morseChar.substring(1), d);
    }
    
    /**
     * Evaluates the inputted expression tree, which consists of just
     * + and * as strings and the numbers as ints inside the integer wrapper class
     * @param expTree    the expression tree to evaluate
     * @precondition     the expression only contains *, +, and integers
     * @return           the value the expression evaluates to
     */
    public static int eval(TreeNode expTree)
    {
        if ( expTree.getLeft() == null )
            return Integer.parseInt( expTree.getValue().toString() );
        if ( expTree.getValue().toString().equals("*") )
            return eval( expTree.getLeft() ) * eval( expTree.getRight() );
        return eval( expTree.getLeft() ) + eval( expTree.getRight() );
    }
    
    /**
     * Creates an expression tree for the given expression, which consists of
     * just +, *, (), and integers (all as one string).
     * @param exp        the expression for which the tree will be created
     * @return           the root of the tree that the expression becomes
     */
    public static TreeNode createExpressionTree(String exp)
    {
        int i;
        if ( exp.indexOf(" ") != -1 )
        {
            String str = "";
            for ( i = 0; i < exp.length(); i++ )
                if ( isValidChar(exp.substring(i,i+1)) )
                    str += exp.substring(i,i+1);
            exp = str;
        }
        i = exp.length();
        if ( exp.substring(0,1).equals("(") && exp.substring(i-1).equals(")") )
            if ( matchParenthesis(exp.substring(1,i-1)) )
                return createExpressionTree( exp.substring(1,i-1) );
        if ( exp.indexOf("+") != -1 )
            for ( i = 0; i < exp.length(); i++ )
                if ( exp.substring(i,i+1).equals("+") )
                {
                    if ( matchParenthesis(exp.substring(0,i)) )
                    {
                        TreeNode t = new TreeNode("+");
                        t.setLeft( createExpressionTree(exp.substring(0,i)) );
                        t.setRight( createExpressionTree(exp.substring(i+1)) );
                        return t;
                    }
                }
        if ( exp.indexOf("*") != -1 )
            for ( i = 0; i < exp.length(); i++ )
                if ( exp.substring(i,i+1).equals("*") )
                {
                    if ( matchParenthesis(exp.substring(0,i)) )
                    {
                        TreeNode t = new TreeNode("*");
                        t.setLeft( createExpressionTree(exp.substring(0,i)) );
                        t.setRight( createExpressionTree(exp.substring(i+1)) );
                        return t;
                    }
                }
        return new TreeNode( Integer.parseInt(exp) );
    }
    
    /**
     * Returns whether or not the character in the string is a
     * valid character (an integer, +, *, or the open/close parentheses)
     * @param str     the character to check
     * @return        true, if the character is valid for the expression; otherwise,
     *                false
     */
    private static boolean isValidChar(String str)
    {
        // Integers
        for ( int i = 0; i < 10; i++ )
            if ( str.equals(String.valueOf(i)) )
                return true;
        // Parentheses
        if ( str.equals("(") || str.equals(")"))
            return true;
        // Operators
        if ( str.equals("+") || str.equals("*"))
            return true;
        // Otherwise, extraneous "fluff"
        return false;
    }

    /**
     * This method checks if an expression has balanced parentheses.
     * An expression is said to be balanced if every opening parenthesis
     * has a corresponding closer, in the right order.
     * @param exp           the expression containing only numbers, +, *, and ()
     * @return              true if the parentheses are balanced; otherwise,
     *                      false
     */
    private static boolean matchParenthesis(String exp)
    {
        int c1 = 0, c2 = 0;
        String str;
        for ( int i = 0; i < exp.length(); i++ )
        {
            str = exp.substring(i,i+1);
            if ( str.equals("(") )
                c1++;
            if ( str.equals(")") )
                c2++;
            if ( c2 > c1 )
                return false;
        }
        return c1 == c2;
    }
    
    /**
     * debug printout
     * postcondition: out is printed to System.out
     * @param out the string to send to System.out
     */
    
    private static void debugPrint(String out)
    {
        if(DEBUG) System.out.println("debug: " + out);
    }
}