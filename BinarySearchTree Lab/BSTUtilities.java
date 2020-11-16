/**
 * A collection of static methods for operating on binary search trees
 * @author Utkarsh Priyam
 * @version 11/27/18
 */
public abstract class BSTUtilities
{
    /**
     * Returns whether or not the tree with root t contains x.
     * @param t         the root of the tree
     * @param x         the value to be searched for
     * @param display   the display of the tree
     * @precondition    the display is not null
     * @precondition    the tree with root t is a binary search tree in ascending order
     * @postcondition   the tree with root t remains unchanged
     * @return          true, if the tree with root t contains the value x; otherwise,
     *                  false
     */
    public static boolean contains(TreeNode t, Comparable x, TreeDisplay display)
    {
        if ( t == null )
            return false;
        display.visit(t);
        if ( x.compareTo( t.getValue() ) == 0 )
            return true;
        if ( x.compareTo( t.getValue() ) > 0 )
            return contains( t.getRight(), x, display );
        return contains( t.getLeft(), x, display );
    }
    
    /**
     * Returns the root to the tree t with x added to it
     * @param t         the root of the tree
     * @param x         the value to be added into the tree
     * @param display   the display of the tree
     * @precondition    the display is not null
     * @precondition    the tree with root t is a binary search tree in ascending order
     * @postcondition   only one new TreeNode is created in this method (for the new value)
     * @return          a TreeNode with the new value if the tree was previously null,
     *                  the tree with the value added if it wasn't inside previously, or
     *                  the original tree if the value was already in the tree
     */
    public static TreeNode insert(TreeNode t, Comparable x, TreeDisplay display)
    {
        if ( t == null )
            return new TreeNode(x);
        display.visit(t);
        if ( x.compareTo( t.getValue() ) > 0 )
            t.setRight( insert( t.getRight(), x, display ) );
        if ( x.compareTo( t.getValue() ) < 0 )
            t.setLeft( insert( t.getLeft(), x, display ) );
        return t;
    }
    /**
     * Deletes the root of the given tree
     * @param t         the root of the tree
     * @param display   the display of the tree
     * @precondition    the display is not null
     * @precondition    t is not null
     * @precondition    the tree with root t is a binary search tree in ascending order
     * @postcondition   no new TreeNodes are created
     * @return          the root of a tree which is the original tree with the root deleted
     */
    private static TreeNode deleteNode(TreeNode t, TreeDisplay display)
    {
        if ( t.getRight() == null )
        {
            display.visit( t.getLeft() );
            return t.getLeft();
        }
        TreeNode temp = t.getRight();
        display.visit( temp );
        if ( temp.getLeft() == null )
        {
            temp.setLeft( t.getLeft() );
            return temp;
        }
        while ( temp.getLeft().getLeft() != null ) // Gets second left-most tree node
        {                                          // of the tree with root temp
            temp = temp.getLeft();
            display.visit( temp );
        }
        TreeNode newRoot = temp.getLeft();
        display.visit( newRoot );
        temp.setLeft( deleteNode(newRoot, display) );
        newRoot.setLeft( t.getLeft() );
        newRoot.setRight( t.getRight() );
        return newRoot;
    }

    /**
     * Deletes the TreeNode of the given tree which has value x
     * @param t         the root of the tree
     * @param x         the value to be deleted
     * @param display   the display of the tree
     * @precondition    the display is not null
     * @precondition    t is not null
     * @precondition    the tree with root t is a binary search tree in ascending order
     * @postcondition   no new TreeNodes are created
     * @return          the root of a tree which is the original tree
     *                  with the TreeNode with value x deleted (if that node exists in the tree)
     */
    public static TreeNode delete(TreeNode t, Comparable x, TreeDisplay display)
    {
        display.resetVisited();
        display.visit(t);
        if ( ! contains( t, x, display ) )
            return t;
        if ( t.getValue().equals(x) )
            return deleteNode( t, display );
        TreeNode temp = t, prev = t;
        boolean left = true;
        while ( ! temp.getValue().equals(x) )
        {
            prev = temp;
            if ( x.compareTo( temp.getValue() ) > 0 )
            {
                temp = temp.getRight();
                left = false;
            }
            else
            {
                temp = temp.getLeft();
                left = true;
            }
            display.visit(temp);
        }
        temp = deleteNode( temp, display );
        if ( left )
            prev.setLeft( temp );
        else
            prev.setRight( temp );
        return t;
    }
}