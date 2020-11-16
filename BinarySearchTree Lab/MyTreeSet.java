import java.util.*;
/**
 * The MyTreeSet<E> class is a tree set that stores object of type E.
 * The tree set does not store duplicate objects.
 * The size() method returns the size of the set.
 * The contains() method returns whether or not
 * the specified object is contained in the tree set.
 * The add() method adds the specified object to the set
 * if it is not contained. Otherwise, it does nothing.
 * It also returns whether or not it modified the set.
 * The remove() method removes the specified object to the set
 * if it is contained. Otherwise, it does nothing.
 * It also returns whether or not it modified the set.
 * The toString() method returns the string form of the tree set.
 * The iterator() method returns an iterator for the tree set.
 * 
 * @author Utkarsh Priyam
 * @version v1
 * 
 * @param <E> the tree set stores only objects of type E
 */
public class MyTreeSet<E>
{
    private TreeNode root;
    private int size;
    private TreeDisplay display;
    private int modCount;
    
    /**
     * A constructor for the MyTreeSet class
     * @postcondition   creates a new, empty tree set
     */
    public MyTreeSet()
    {
        root = null;
        size = 0;
        display = new TreeDisplay();

        //wait 1 millisecond when visiting a node
        display.setDelay(5); // originally 1
        
        modCount = 0;
    }
    
    /**
     * Returns the size of the tree set
     * @return  the number of elements in the tree set
     */
    public int size()
    {
        return size;
    }
    
    /**
     * Returns whether or not the tree set contains obj
     * @param obj       the object to be searched for
     * @precondition    obj implements Comparable
     * @return          true if the tree set contains obj; otherwise,
     *                  false
     */
    public boolean contains(Object obj)
    {
        return BSTUtilities.contains(root, (Comparable)obj, display);
    }
    
    /**
     * Adds the specified object to the tree set if it is not already contained.
     * Returns whether or not the tree set was modified by this method.
     * @param obj       the object to be added
     * @precondition    obj implements Comparable
     * @postcondition   the tree set contains obj
     * @return          true if the tree set was modified; otherwise,
     *                  false
     */
    public boolean add(E obj)
    {
        Comparable x = (Comparable)(obj);
        if(contains(obj))
            return false;
        root  = BSTUtilities.insert(root, x, display);
        size++;
        display.displayTree(root);
        modCount++;
        return true;
    }
    
    /**
     * Removes the specified object to the tree set if it is contained.
     * Returns whether or not the tree set was modified by this method.
     * @param obj       the object to be removed
     * @precondition    obj implements Comparable
     * @postcondition   the tree set does not contain obj
     * @return          true if the tree set was modified; otherwise,
     *                  false
     */
    public boolean remove(Object obj)
    {
        Comparable x = (Comparable)(obj);
        if(!contains(obj))
            return false;
        root = BSTUtilities.delete(root, x, display);
        size--;
        display.displayTree(root);
        modCount++;
        return true;
    }
    
    /**
     * Returns the string form of the tree set (the elements sorted in order)
     * @return  returns the elements of the tree set sorted in order as a string
     */
    public String toString()
    {
        String s = toString(root);
        if ( s.length() == 2 )
            return "";
        return s.substring( 2, s.length() - 2 );
    }
    
    /**
     * Returns the string form of a tree with the specified root
     * The elements sorted in order
     * @param t the root of the tree
     * @return  returns the elements of the tree sorted in order as a string
     */
    private String toString(TreeNode t)
    {
        if (t == null)
            return ", ";
        return toString(t.getLeft()) +  t.getValue() + toString(t.getRight());
    }
    
    /**
     * Returns an iterator for the tree set
     * The iterator will do an in order traversal over the tree set
     * @return  a new iterator for the tree set
     */
    public Iterator iterator()
    {
        return new Iterator();
    }
    
    /**
     * An iterator for the tree set.
     * This iterator is equipped with a variety of exceptions ranging from
     * ConcurrentModificationException to NoSuchElementException.
     * The iterator can check if the tree set has any elements left
     * and can return the next element if one exists.
     */
    private class Iterator
    {
        private Stack<TreeNode> s;
        private int modCountIt;
        
        /**
         * A constructor for the tree set's iterators
         */
        public Iterator()
        {
            s = new Stack<TreeNode>();
            TreeNode temp = root;
            while ( temp != null )
            {
                s.push(temp);
                temp = temp.getLeft();
            }
            modCountIt = MyTreeSet.this.modCount;
        }
        
        /**
         * Returns whether or not the tree set contains any more elements
         * @return  true if the tree set has more elements; otherwise,
         *          false
         */
        public boolean hasNext()
        {
            return ! s.isEmpty();
        }
        
        /**
         * Returns the next element in the tree set if one exists
         * If no such element exists, the iterator throws a NoSuchElementException
         * If the tree set was modified after the creation of the iterator,
         * the iterator throws a ConcurrentModificationException
         * @return  the next element in the tree set if one exists and tree set
         *          was not modified after the creation of the iterator; otherwise,
         *          null
         */
        public Object next()
        {
            try
            {
                if ( modCountIt != MyTreeSet.this.modCount )
                    throw new ConcurrentModificationException();
                if ( ! hasNext() )
                    throw new NoSuchElementException();
                TreeNode t = s.pop();
                TreeNode temp = t.getRight();
                while ( temp != null )
                {
                    s.push(temp);
                    temp = temp.getLeft();
                }
                return t.getValue();
            }
            catch ( ConcurrentModificationException e )
            {
                System.out.println("This tree set has been edited " +
                                   "since this iterator was created. " +
                                   "Please create another iterator. \n" +
                                   "This is a ConcurrentModificationException.");
            }
            catch ( NoSuchElementException e )
            {
                System.out.println("No such element exists. " +
                                   "The iterator has reached " +
                                   "the end of this tree set.\n" +
                                   "This is a NoSuchElementException.");
            }
            finally
            {
                return null;
            }
        }
    }
}