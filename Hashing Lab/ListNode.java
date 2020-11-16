/**
 * This ListNode class can be used to create nodes for LinkedLists.
 * The nodes know their own value and a "link" to the next node.
 * 
 * @author  Schoology
 * @version 9/24/18
 * 
 * @param <E> the listnode stores only objects of type E
 */
public class ListNode<E>
{
    private E value;
    private ListNode next;
    
    /**
     *Constructor for class ListNode
     * @param initValue the initial value of the node
     * @param initNext  the initial next node in the list
     */
    public ListNode( E initValue, ListNode initNext )
    {
        value = initValue;
        next = initNext; 
    }
    
    /**
     * Returns the value of the node
     * @return  the value of the node
     */
    public E getValue() 
    { 
        return value; 
    }
    
    /**
     * Returns the next node in the list
     * @return  next, the next node in the list
     */
    public ListNode getNext() 
    { 
        return next; 
    }
    
    /**
     * Sets the value of the node to a new given value
     * @param theNewValue   the new value of the node
     * @postcondition       value = theNewValue
     */
    public void setValue( E theNewValue ) 
    { 
        value = theNewValue; 
    }
    
    /**
     * Sets the value of the node to a new given value
     * @param theNewNext    the new next node in the list
     * @postcondition       next = theNewValue
     */
    public void setNext( ListNode theNewNext ) 
    { 
        next = theNewNext; 
    }
}