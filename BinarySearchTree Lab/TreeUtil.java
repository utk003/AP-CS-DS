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
}